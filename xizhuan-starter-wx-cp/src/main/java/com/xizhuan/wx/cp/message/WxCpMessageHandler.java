package com.xizhuan.wx.cp.message;

import com.xizhuan.wx.common.error.WxErrorException;
import com.xizhuan.wx.common.session.WxSessionManager;
import com.xizhuan.wx.cp.api.WxCpService;
import com.xizhuan.wx.cp.bean.WxCpXmlMessage;
import com.xizhuan.wx.cp.bean.WxCpXmlOutMessage;

import java.util.Map;

/**
 * 处理微信推送消息的处理器接口
 *
 * @author Daniel Qian
 */
public interface WxCpMessageHandler {

  /**
   * @param wxMessage
   * @param context        上下文，如果handler或interceptor之间有信息要传递，可以用这个
   * @param wxCpService
   * @param sessionManager
   * @return xml格式的消息，如果在异步规则里处理的话，可以返回null
   */
  WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage,
                           Map<String, Object> context,
                           WxCpService wxCpService,
                           WxSessionManager sessionManager) throws WxErrorException;

}
