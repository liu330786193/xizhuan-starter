package com.xizhuan.wx.cp.message;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xizhuan.wx.common.api.WxErrorExceptionHandler;
import com.xizhuan.wx.common.api.WxMessageDuplicateChecker;
import com.xizhuan.wx.common.api.WxMessageInMemoryDuplicateChecker;
import com.xizhuan.wx.common.session.InternalSession;
import com.xizhuan.wx.common.session.InternalSessionManager;
import com.xizhuan.wx.common.session.WxSessionManager;
import com.xizhuan.wx.common.util.LogExceptionHandler;
import com.xizhuan.wx.cp.api.WxCpService;
import com.xizhuan.wx.cp.bean.WxCpXmlMessage;
import com.xizhuan.wx.cp.bean.WxCpXmlOutMessage;

/**
 * <pre>
 * 微信消息路由器，通过代码化的配置，把来自微信的消息交给handler处理
 *
 * 说明：
 * 1. 配置路由规则时要按照从细到粗的原则，否则可能消息可能会被提前处理
 * 2. 默认情况下消息只会被处理一次，除非使用 {@link WxCpMessageRouterRule#next()}
 * 3. 规则的结束必须用{@link WxCpMessageRouterRule#end()}或者{@link WxCpMessageRouterRule#next()}，否则不会生效
 *
 * 使用方法：
 * WxCpMessageRouter router = new WxCpMessageRouter();
 * router
 *   .rule()
 *       .msgType("MSG_TYPE").event("EVENT").eventKey("EVENT_KEY").content("CONTENT")
 *       .interceptor(interceptor, ...).handler(handler, ...)
 *   .end()
 *   .rule()
 *       // 另外一个匹配规则
 *   .end()
 * ;
 *
 * // 将WxXmlMessage交给消息路由器
 * router.route(message);
 *
 * </pre>
 *
 * @author Daniel Qian
 */
public class WxCpMessageRouter {
  private static final int DEFAULT_THREAD_POOL_SIZE = 100;
  private final Logger log = LoggerFactory.getLogger(WxCpMessageRouter.class);
  private final List<WxCpMessageRouterRule> rules = new ArrayList<>();

  private final WxCpService wxCpService;

  private ExecutorService executorService;

  private WxMessageDuplicateChecker messageDuplicateChecker;

  private WxSessionManager sessionManager;

  private WxErrorExceptionHandler exceptionHandler;

  /**
   * 构造方法.
   */
  public WxCpMessageRouter(WxCpService wxCpService) {
    this.wxCpService = wxCpService;
    this.executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_POOL_SIZE);
    this.messageDuplicateChecker = new WxMessageInMemoryDuplicateChecker();
    this.sessionManager = wxCpService.getSessionManager();
    this.exceptionHandler = new LogExceptionHandler();
  }

  /**
   * <pre>
   * 设置自定义的 {@link ExecutorService}
   * 如果不调用该方法，默认使用 Executors.newFixedThreadPool(100)
   * </pre>
   */
  public void setExecutorService(ExecutorService executorService) {
    this.executorService = executorService;
  }

  /**
   * <pre>
   * 设置自定义的 {@link com.xizhuan.wx.common.api.WxMessageDuplicateChecker}
   * 如果不调用该方法，默认使用 {@link com.xizhuan.wx.common.api.WxMessageInMemoryDuplicateChecker}
   * </pre>
   */
  public void setMessageDuplicateChecker(WxMessageDuplicateChecker messageDuplicateChecker) {
    this.messageDuplicateChecker = messageDuplicateChecker;
  }

  /**
   * <pre>
   * 设置自定义的{@link com.xizhuan.wx.common.session.WxSessionManager}
   * 如果不调用该方法，默认使用 {@link com.xizhuan.wx.common.session.StandardSessionManager}
   * </pre>
   */
  public void setSessionManager(WxSessionManager sessionManager) {
    this.sessionManager = sessionManager;
  }

  /**
   * <pre>
   * 设置自定义的{@link com.xizhuan.wx.common.api.WxErrorExceptionHandler}
   * 如果不调用该方法，默认使用 {@link com.xizhuan.wx.common.util.LogExceptionHandler}
   * </pre>
   */
  public void setExceptionHandler(WxErrorExceptionHandler exceptionHandler) {
    this.exceptionHandler = exceptionHandler;
  }

  List<WxCpMessageRouterRule> getRules() {
    return this.rules;
  }

  /**
   * 开始一个新的Route规则.
   */
  public WxCpMessageRouterRule rule() {
    return new WxCpMessageRouterRule(this);
  }

  /**
   * 处理微信消息.
   */
  public WxCpXmlOutMessage route(final WxCpXmlMessage wxMessage, final Map<String, Object> context) {
    if (isMsgDuplicated(wxMessage)) {
      // 如果是重复消息，那么就不做处理
      return null;
    }

    final List<WxCpMessageRouterRule> matchRules = new ArrayList<>();
    // 收集匹配的规则
    for (final WxCpMessageRouterRule rule : this.rules) {
      if (rule.test(wxMessage)) {
        matchRules.add(rule);
        if (!rule.isReEnter()) {
          break;
        }
      }
    }

    if (matchRules.size() == 0) {
      return null;
    }

    WxCpXmlOutMessage res = null;
    final List<Future> futures = new ArrayList<>();
    for (final WxCpMessageRouterRule rule : matchRules) {
      // 返回最后一个非异步的rule的执行结果
      if (rule.isAsync()) {
        futures.add(
          this.executorService.submit(new Runnable() {
            @Override
            public void run() {
              rule.service(wxMessage, context, WxCpMessageRouter.this.wxCpService, WxCpMessageRouter.this.sessionManager, WxCpMessageRouter.this.exceptionHandler);
            }
          })
        );
      } else {
        res = rule.service(wxMessage, context, this.wxCpService, this.sessionManager, this.exceptionHandler);
        // 在同步操作结束，session访问结束
        this.log.debug("End session access: async=false, sessionId={}", wxMessage.getFromUserName());
        sessionEndAccess(wxMessage);
      }
    }

    if (futures.size() > 0) {
      this.executorService.submit(new Runnable() {
        @Override
        public void run() {
          for (Future future : futures) {
            try {
              future.get();
              WxCpMessageRouter.this.log.debug("End session access: async=true, sessionId={}", wxMessage.getFromUserName());
              // 异步操作结束，session访问结束
              sessionEndAccess(wxMessage);
            } catch (InterruptedException e) {
              WxCpMessageRouter.this.log.error("Error happened when wait task finish", e);
              Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
              WxCpMessageRouter.this.log.error("Error happened when wait task finish", e);
            }
          }
        }
      });
    }
    return res;
  }

  /**
   * 处理微信消息.
   */
  public WxCpXmlOutMessage route(final WxCpXmlMessage wxMessage) {
    return this.route(wxMessage, new HashMap<String, Object>(2));
  }

  private boolean isMsgDuplicated(WxCpXmlMessage wxMessage) {
    StringBuilder messageId = new StringBuilder();
    if (wxMessage.getMsgId() == null) {
      messageId.append(wxMessage.getCreateTime())
        .append("-").append(StringUtils.trimToEmpty(String.valueOf(wxMessage.getAgentId())))
        .append("-").append(wxMessage.getFromUserName())
        .append("-").append(StringUtils.trimToEmpty(wxMessage.getEventKey()))
        .append("-").append(StringUtils.trimToEmpty(wxMessage.getEvent()));
    } else {
      messageId.append(wxMessage.getMsgId())
        .append("-").append(wxMessage.getCreateTime())
        .append("-").append(wxMessage.getFromUserName());
    }

    if (StringUtils.isNotEmpty(wxMessage.getUserId())) {
      messageId.append("-").append(wxMessage.getUserId());
    }

    if (StringUtils.isNotEmpty(wxMessage.getChangeType())) {
      messageId.append("-").append(wxMessage.getChangeType());
    }

    return this.messageDuplicateChecker.isDuplicate(messageId.toString());
  }

  /**
   * 对session的访问结束.
   */
  private void sessionEndAccess(WxCpXmlMessage wxMessage) {
    InternalSession session = ((InternalSessionManager) this.sessionManager).findSession(wxMessage.getFromUserName());
    if (session != null) {
      session.endAccess();
    }

  }
}
