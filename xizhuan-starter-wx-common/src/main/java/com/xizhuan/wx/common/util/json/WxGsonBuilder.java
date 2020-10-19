package com.xizhuan.wx.common.util.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xizhuan.wx.common.bean.WxAccessToken;
import com.xizhuan.wx.common.bean.WxNetCheckResult;
import com.xizhuan.wx.common.bean.menu.WxMenu;
import com.xizhuan.wx.common.error.WxError;
import com.xizhuan.wx.common.bean.result.WxMediaUploadResult;

/**
 * .
 * @author chanjarster
 */
public class WxGsonBuilder {
  private static final GsonBuilder INSTANCE = new GsonBuilder();

  static {
    INSTANCE.disableHtmlEscaping();
    INSTANCE.registerTypeAdapter(WxAccessToken.class, new WxAccessTokenAdapter());
    INSTANCE.registerTypeAdapter(WxError.class, new WxErrorAdapter());
    INSTANCE.registerTypeAdapter(WxMenu.class, new WxMenuGsonAdapter());
    INSTANCE.registerTypeAdapter(WxMediaUploadResult.class, new WxMediaUploadResultAdapter());
    INSTANCE.registerTypeAdapter(WxNetCheckResult.class, new WxNetCheckResultGsonAdapter());

  }

  public static Gson create() {
    return INSTANCE.create();
  }

}
