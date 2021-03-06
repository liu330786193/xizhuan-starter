package com.xizhuan.wx.cp.bean;

import com.xizhuan.wx.cp.util.json.WxCpGsonBuilder;
import lombok.Data;

import java.io.Serializable;

/**
 * 企业微信的部门.
 *
 * @author Daniel Qian
 */
@Data
public class WxCpDepart implements Serializable {
  private static final long serialVersionUID = -5028321625140879571L;

  private Long id;
  private String name;
  private String enName;
  private Long parentId;
  private Long order;

  public static WxCpDepart fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpDepart.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
