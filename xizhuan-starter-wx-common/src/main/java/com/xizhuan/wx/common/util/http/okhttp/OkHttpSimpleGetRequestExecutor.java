package com.xizhuan.wx.common.util.http.okhttp;

import com.xizhuan.wx.common.enums.WxType;
import com.xizhuan.wx.common.error.WxErrorException;
import com.xizhuan.wx.common.util.http.RequestHttp;
import com.xizhuan.wx.common.util.http.SimpleGetRequestExecutor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * .
 *
 * @author ecoolper
 * @date 2017/5/4
 */
public class OkHttpSimpleGetRequestExecutor extends SimpleGetRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
  public OkHttpSimpleGetRequestExecutor(RequestHttp requestHttp) {
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

    //得到httpClient
    OkHttpClient client = requestHttp.getRequestHttpClient();
    Request request = new Request.Builder().url(uri).build();
    Response response = client.newCall(request).execute();
    return this.handleResponse(wxType, response.body().string());
  }

}
