package com.xizhuan.wx.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import com.xizhuan.wx.cp.util.json.WxCpGsonBuilder;

/**
 * @author yqx
 * @date 2020/3/16
 */
@Getter
@Setter
public class WxCpBaseResp {
  @SerializedName("errcode")
  protected Long errcode;

  @SerializedName("errmsg")
  protected String errmsg;

  public boolean success() {
    return getErrcode() == 0;
  }

  public static WxCpBaseResp fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpBaseResp.class);
  }
}
