package com.xizhuan.wx.cp.bean;

import com.google.gson.annotations.SerializedName;
import com.xizhuan.wx.cp.util.json.WxCpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * <pre>
 * 小程序登录凭证校验
 * 文档地址：https://work.weixin.qq.com/api/doc#90000/90136/90289/wx.qy.login
 * </pre>
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
public class WxCpMaJsCode2SessionResult implements Serializable {
  private static final long serialVersionUID = 6229609023682814765L;

  @SerializedName("session_key")
  private String sessionKey;

  @SerializedName("userid")
  private String userId;

  @SerializedName("corpid")
  private String corpId;

  public static WxCpMaJsCode2SessionResult fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpMaJsCode2SessionResult.class);
  }

}
