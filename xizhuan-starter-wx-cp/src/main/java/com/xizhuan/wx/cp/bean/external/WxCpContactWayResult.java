package com.xizhuan.wx.cp.bean.external;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import com.xizhuan.wx.cp.bean.WxCpBaseResp;
import com.xizhuan.wx.cp.util.json.WxCpGsonBuilder;

/**
 * 「联系我」方式 处理结果
 */
@Data
public class WxCpContactWayResult extends WxCpBaseResp {
  @SerializedName("config_id")
  private String configId;

  @SerializedName("qr_code")
  private String qrCode;

  public static WxCpContactWayResult fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpContactWayResult.class);
  }
}
