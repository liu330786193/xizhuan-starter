package com.xizhuan.wx.cp.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.xizhuan.wx.common.api.WxConsts;
import com.xizhuan.wx.common.util.xml.XStreamCDataConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = false)
public class WxCpXmlOutTextMessage extends WxCpXmlOutMessage {
  private static final long serialVersionUID = 2569239617185930232L;

  @XStreamAlias("Content")
  @XStreamConverter(value = XStreamCDataConverter.class)
  private String content;

  public WxCpXmlOutTextMessage() {
    this.msgType = WxConsts.XmlMsgType.TEXT;
  }

}
