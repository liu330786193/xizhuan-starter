package com.xizhuan.wx.cp.api.impl;

import com.xizhuan.wx.common.error.WxErrorException;
import com.xizhuan.wx.common.util.json.GsonParser;
import com.xizhuan.wx.common.util.json.WxGsonBuilder;
import com.xizhuan.wx.cp.api.WxCpChatService;
import com.xizhuan.wx.cp.api.WxCpService;
import com.xizhuan.wx.cp.bean.WxCpAppChatMessage;
import com.xizhuan.wx.cp.bean.WxCpChat;
import com.xizhuan.wx.cp.util.json.WxCpGsonBuilder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xizhuan.wx.cp.constant.WxCpApiPathConsts.Chat.*;

/**
 * 群聊服务实现.
 *
 * @author gaigeshen
 */
@RequiredArgsConstructor
public class WxCpChatServiceImpl implements WxCpChatService {
  private final WxCpService cpService;

  @Override
  public String create(String name, String owner, List<String> users, String chatId) throws WxErrorException {
    Map<String, Object> data = new HashMap<>(4);
    if (StringUtils.isNotBlank(name)) {
      data.put("name", name);
    }
    if (StringUtils.isNotBlank(owner)) {
      data.put("owner", owner);
    }
    if (users != null) {
      data.put("userlist", users);
    }
    if (StringUtils.isNotBlank(chatId)) {
      data.put("chatid", chatId);
    }
    final String url = this.cpService.getWxCpConfigStorage().getApiUrl(APPCHAT_CREATE);
    String result = this.cpService.post(url, WxGsonBuilder.create().toJson(data));
    return GsonParser.parse(result).get("chatid").getAsString();
  }

  @Override
  public void update(String chatId, String name, String owner, List<String> usersToAdd, List<String> usersToDelete)
    throws WxErrorException {
    Map<String, Object> data = new HashMap<>(5);
    if (StringUtils.isNotBlank(chatId)) {
      data.put("chatid", chatId);
    }
    if (StringUtils.isNotBlank(name)) {
      data.put("name", name);
    }
    if (StringUtils.isNotBlank(owner)) {
      data.put("owner", owner);
    }
    if (usersToAdd != null && !usersToAdd.isEmpty()) {
      data.put("add_user_list", usersToAdd);
    }
    if (usersToDelete != null && !usersToDelete.isEmpty()) {
      data.put("del_user_list", usersToDelete);
    }

    final String url = this.cpService.getWxCpConfigStorage().getApiUrl(APPCHAT_UPDATE);
    this.cpService.post(url, WxGsonBuilder.create().toJson(data));
  }

  @Override
  public WxCpChat get(String chatId) throws WxErrorException {
    final String url = this.cpService.getWxCpConfigStorage().getApiUrl(APPCHAT_GET_CHATID + chatId);
    String result = this.cpService.get(url, null);
    final String chatInfo = GsonParser.parse(result).getAsJsonObject("chat_info").toString();
    return WxCpGsonBuilder.create().fromJson(chatInfo, WxCpChat.class);
  }

  @Override
  public void sendMsg(WxCpAppChatMessage message) throws WxErrorException {
    this.cpService.post(this.cpService.getWxCpConfigStorage().getApiUrl(APPCHAT_SEND), message.toJson());
  }

}
