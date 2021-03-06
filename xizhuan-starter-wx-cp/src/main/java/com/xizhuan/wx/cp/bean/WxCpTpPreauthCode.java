package com.xizhuan.wx.cp.bean;

import com.google.gson.annotations.SerializedName;
import com.xizhuan.wx.cp.util.json.WxCpGsonBuilder;
import lombok.Getter;
import lombok.Setter;

/**
 * 预授权码返回
 * @author yqx
 * @date 2020/3/19
 */
@Getter
@Setter
public class WxCpTpPreauthCode extends WxCpBaseResp {

  @SerializedName("pre_auth_code")
  String preAuthCode;

  @SerializedName("expires_in")
  Long expiresIn;

  public static WxCpTpPreauthCode fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpTpPreauthCode.class);
  }
}
