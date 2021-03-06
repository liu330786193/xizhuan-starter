package com.xizhuan.wx.common.util.http.jodd;

import com.xizhuan.wx.common.bean.result.WxMediaUploadResult;
import com.xizhuan.wx.common.enums.WxType;
import com.xizhuan.wx.common.error.WxError;
import com.xizhuan.wx.common.error.WxErrorException;
import com.xizhuan.wx.common.util.http.MediaUploadRequestExecutor;
import com.xizhuan.wx.common.util.http.RequestHttp;
import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.util.StringPool;

import java.io.File;
import java.io.IOException;

/**
 * .
 *
 * @author ecoolper
 * @date 2017/5/5
 */
public class JoddHttpMediaUploadRequestExecutor extends MediaUploadRequestExecutor<HttpConnectionProvider, ProxyInfo> {
  public JoddHttpMediaUploadRequestExecutor(RequestHttp requestHttp) {
    super(requestHttp);
  }

  @Override
  public WxMediaUploadResult execute(String uri, File file, WxType wxType) throws WxErrorException, IOException {
    HttpRequest request = HttpRequest.post(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
    }
    request.withConnectionProvider(requestHttp.getRequestHttpClient());
    request.form("media", file);
    HttpResponse response = request.send();
    response.charset(StringPool.UTF_8);

    String responseContent = response.bodyText();
    WxError error = WxError.fromJson(responseContent, wxType);
    if (error.getErrorCode() != 0) {
      throw new WxErrorException(error);
    }
    return WxMediaUploadResult.fromJson(responseContent);
  }
}
