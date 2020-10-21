package com.xizhuan.wx.common.util.http.jodd;

import com.xizhuan.wx.common.enums.WxType;
import com.xizhuan.wx.common.error.WxErrorException;
import com.xizhuan.wx.common.util.http.RequestHttp;
import com.xizhuan.wx.common.util.http.SimpleGetRequestExecutor;
import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.util.StringPool;

import java.io.IOException;

/**
 * .
 *
 * @author ecoolper
 * @date 2017/5/4
 */
public class JoddHttpSimpleGetRequestExecutor extends SimpleGetRequestExecutor<HttpConnectionProvider, ProxyInfo> {
  public JoddHttpSimpleGetRequestExecutor(RequestHttp requestHttp) {
    super(requestHttp);
  }

  @Override
  public String execute(String uri, String queryParam, WxType wxType) throws WxErrorException, IOException {
    if (queryParam != null) {
      if (uri.indexOf('?') == -1) {
        uri += '?';
      }
      uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
    }

    HttpRequest request = HttpRequest.get(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
    }
    request.withConnectionProvider(requestHttp.getRequestHttpClient());
    HttpResponse response = request.send();
    response.charset(StringPool.UTF_8);

    return handleResponse(wxType, response.bodyText());
  }

}
