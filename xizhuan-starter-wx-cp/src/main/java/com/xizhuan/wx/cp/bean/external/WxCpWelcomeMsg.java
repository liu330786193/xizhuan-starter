package com.xizhuan.wx.cp.bean.external;

import com.google.gson.annotations.SerializedName;
import com.xizhuan.wx.cp.bean.external.msg.Image;
import com.xizhuan.wx.cp.bean.external.msg.Link;
import com.xizhuan.wx.cp.bean.external.msg.MiniProgram;
import com.xizhuan.wx.cp.bean.external.msg.Text;
import com.xizhuan.wx.cp.util.json.WxCpGsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 新客户欢迎语.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 * @date 2020-08-16
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxCpWelcomeMsg implements Serializable {
  private static final long serialVersionUID = 4170843890468921757L;

  @SerializedName("welcome_code")
  private String welcomeCode;

  private Text text;

  private Image image;

  private Link link;

  private MiniProgram miniprogram;

  public String toJson() {
    return WxCpGsonBuilder.create().toJson(this);
  }
}
