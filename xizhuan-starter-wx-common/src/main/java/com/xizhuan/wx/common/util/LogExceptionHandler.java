package com.xizhuan.wx.common.util;

import com.xizhuan.wx.common.api.WxErrorExceptionHandler;
import com.xizhuan.wx.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogExceptionHandler implements WxErrorExceptionHandler {

  private Logger log = LoggerFactory.getLogger(WxErrorExceptionHandler.class);

  @Override
  public void handle(WxErrorException e) {

    this.log.error("Error happens", e);

  }

}
