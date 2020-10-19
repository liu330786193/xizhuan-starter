package com.xizhuan.wx.cp.bean;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.xizhuan.wx.cp.util.json.WxCpGsonBuilder;

/**
 * Created by Daniel Qian.
 * @author Daniel Qian
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WxCpTag implements Serializable {
  private static final long serialVersionUID = -7243320279646928402L;

  private String id;

  private String name;


  public static WxCpTag fromJson(String json) {
    return WxCpGsonBuilder.create().fromJson(json, WxCpTag.class);
  }

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }

}
