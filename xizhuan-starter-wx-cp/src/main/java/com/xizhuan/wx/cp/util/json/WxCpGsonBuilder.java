package com.xizhuan.wx.cp.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xizhuan.wx.common.bean.menu.WxMenu;
import com.xizhuan.wx.common.error.WxError;
import com.xizhuan.wx.common.util.json.WxErrorAdapter;
import com.xizhuan.wx.cp.bean.WxCpChat;
import com.xizhuan.wx.cp.bean.WxCpDepart;
import com.xizhuan.wx.cp.bean.WxCpTag;
import com.xizhuan.wx.cp.bean.WxCpUser;

/**
 * @author Daniel Qian
 */
public class WxCpGsonBuilder {

  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxCpChat.class, new WxCpChatGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpDepart.class, new WxCpDepartGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpUser.class, new WxCpUserGsonAdapter());
    INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
    INSTANCE.registerTypeAdapter(WxMenu.class, new WxCpMenuGsonAdapter());
    INSTANCE.registerTypeAdapter(WxCpTag.class, new WxCpTagGsonAdapter());
  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
