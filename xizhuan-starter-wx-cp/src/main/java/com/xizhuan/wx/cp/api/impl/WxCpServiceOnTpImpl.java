package com.xizhuan.wx.cp.api.impl;

import com.xizhuan.wx.common.bean.WxAccessToken;
import com.xizhuan.wx.common.error.WxErrorException;
import com.xizhuan.wx.cp.api.WxCpTpService;
import lombok.RequiredArgsConstructor;

/**
 * <pre>
 *  默认接口实现类，使用apache httpclient实现，配合第三方应用service使用
 * Created by zhenjun cai.
 * </pre>
 *
 * @author zhenjun cai
 */
@RequiredArgsConstructor
public class WxCpServiceOnTpImpl extends WxCpServiceApacheHttpClientImpl {
  private final WxCpTpService wxCpTpService;

  @Override
  public String getAccessToken(boolean forceRefresh) throws WxErrorException {
    if (!this.configStorage.isAccessTokenExpired() && !forceRefresh) {
      return this.configStorage.getAccessToken();
    }
    //access token通过第三方应用service获取
    //corpSecret对应企业永久授权码
    WxAccessToken accessToken = wxCpTpService.getCorpToken(this.configStorage.getCorpId(), this.configStorage.getCorpSecret());

    this.configStorage.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
    return this.configStorage.getAccessToken();
  }

}
