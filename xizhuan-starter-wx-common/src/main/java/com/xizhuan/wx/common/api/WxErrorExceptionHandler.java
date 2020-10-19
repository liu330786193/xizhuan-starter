package com.xizhuan.wx.common.api;

import com.xizhuan.wx.common.error.WxErrorException;

/**
 * WxErrorException处理器.
 *
 * @author Daniel Qian
 */
public interface WxErrorExceptionHandler {

  void handle(WxErrorException e);

}
