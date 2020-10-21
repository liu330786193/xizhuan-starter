package com.xizhuan.wx.cp.api.impl;

import com.xizhuan.wx.common.api.WxConsts;
import com.xizhuan.wx.common.error.WxErrorException;
import com.xizhuan.wx.cp.api.WxCpGroupRobotService;
import com.xizhuan.wx.cp.api.WxCpService;
import com.xizhuan.wx.cp.bean.WxCpGroupRobotMessage;
import com.xizhuan.wx.cp.bean.article.NewArticle;
import com.xizhuan.wx.cp.config.WxCpConfigStorage;
import com.xizhuan.wx.cp.constant.WxCpApiPathConsts;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * 微信群机器人消息发送api 实现
 *
 * @author yr
 * @date 2020-08-20
 */
@RequiredArgsConstructor
public class WxCpGroupRobotServiceImpl implements WxCpGroupRobotService {
  private final WxCpService cpService;

  private String getApiUrl() {
    WxCpConfigStorage wxCpConfigStorage = cpService.getWxCpConfigStorage();
    return wxCpConfigStorage.getApiUrl(WxCpApiPathConsts.WEBHOOK_SEND) + wxCpConfigStorage.getWebhookKey();
  }

  @Override
  public void sendText(String content, List<String> mentionedList, List<String> mobileList) throws WxErrorException {
    WxCpGroupRobotMessage message = new WxCpGroupRobotMessage()
      .setMsgType(WxConsts.GroupRobotMsgType.TEXT)
      .setContent(content)
      .setMentionedList(mentionedList)
      .setMentionedMobileList(mobileList);
    cpService.postWithoutToken(this.getApiUrl(), message.toJson());
  }

  @Override
  public void sendMarkDown(String content) throws WxErrorException {
    WxCpGroupRobotMessage message = new WxCpGroupRobotMessage()
      .setMsgType(WxConsts.GroupRobotMsgType.MARKDOWN)
      .setContent(content);
    cpService.postWithoutToken(this.getApiUrl(), message.toJson());
  }

  @Override
  public void sendImage(String base64, String md5) throws WxErrorException {
    WxCpGroupRobotMessage message = new WxCpGroupRobotMessage()
      .setMsgType(WxConsts.GroupRobotMsgType.IMAGE)
      .setBase64(base64)
      .setMd5(md5);
    cpService.postWithoutToken(this.getApiUrl(), message.toJson());
  }

  @Override
  public void sendNews(List<NewArticle> articleList) throws WxErrorException {
    WxCpGroupRobotMessage message = new WxCpGroupRobotMessage()
      .setMsgType(WxConsts.GroupRobotMsgType.NEWS)
      .setArticles(articleList);
    cpService.postWithoutToken(this.getApiUrl(), message.toJson());
  }

}
