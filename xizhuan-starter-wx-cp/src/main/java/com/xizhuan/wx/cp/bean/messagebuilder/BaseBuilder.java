package com.xizhuan.wx.cp.bean.messagebuilder;

import com.xizhuan.wx.common.api.WxConsts;
import com.xizhuan.wx.cp.bean.WxCpMessage;
import org.apache.commons.lang3.StringUtils;

public class BaseBuilder<T> {
  protected String msgType;
  protected Integer agentId;
  protected String toUser;
  protected String toParty;
  protected String toTag;
  protected String safe;

  public T agentId(Integer agentId) {
    this.agentId = agentId;
    return (T) this;
  }

  public T toUser(String toUser) {
    this.toUser = toUser;
    return (T) this;
  }

  public T toParty(String toParty) {
    this.toParty = toParty;
    return (T) this;
  }

  public T toTag(String toTag) {
    this.toTag = toTag;
    return (T) this;
  }

  public T safe(String safe) {
    this.safe = safe;
    return (T) this;
  }

  public WxCpMessage build() {
    WxCpMessage m = new WxCpMessage();
    m.setAgentId(this.agentId);
    m.setMsgType(this.msgType);
    m.setToUser(this.toUser);
    m.setToParty(this.toParty);
    m.setToTag(this.toTag);
    m.setSafe(StringUtils.defaultIfBlank(this.safe, WxConsts.KefuMsgSafe.NO));
    return m;
  }

}
