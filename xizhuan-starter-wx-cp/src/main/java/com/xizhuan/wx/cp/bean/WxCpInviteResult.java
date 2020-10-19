package com.xizhuan.wx.cp.bean;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import com.xizhuan.wx.cp.util.json.WxCpGsonBuilder;

/**
 * 邀请成员的结果对象类.
 * Created by Binary Wang on 2018-5-13.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
public class WxCpInviteResult implements Serializable {
  private static final long serialVersionUID = 1420065684270213578L;

  @Override
  public String toString() {
    return WxCpGsonBuilder.create().toJson(this);
  }

  public static WxCpInviteResult fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpInviteResult.class);
  }

  @SerializedName("errcode")
  private Integer errCode;

  @SerializedName("errmsg")
  private String errMsg;

  @SerializedName("invaliduser")
  private String[] invalidUsers;

  @SerializedName("invalidparty")
  private String[] invalidParties;

  @SerializedName("invalidtag")
  private String[] invalidTags;

}
