package com.xizhuan.wx.cp.bean;

import com.google.gson.annotations.SerializedName;
import com.xizhuan.wx.cp.util.json.WxCpGsonBuilder;
import lombok.Data;

/**
 * 服务商凭证.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 * @date 2019-11-02
 */
@Data
public class WxCpProviderToken {
  /**
   * 服务商的access_token，最长为512字节。
   */
  @SerializedName("provider_access_token")
  private String providerAccessToken;

  /**
   * provider_access_token有效期（秒）
   */
  @SerializedName("expires_in")
  private Integer expiresIn;

  public static WxCpProviderToken fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpProviderToken.class);
  }
}
