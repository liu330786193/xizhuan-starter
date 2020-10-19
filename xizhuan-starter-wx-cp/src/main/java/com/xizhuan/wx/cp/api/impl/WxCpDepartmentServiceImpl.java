package com.xizhuan.wx.cp.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import com.xizhuan.wx.common.error.WxErrorException;
import com.xizhuan.wx.common.util.json.GsonHelper;
import com.xizhuan.wx.common.util.json.GsonParser;
import com.xizhuan.wx.cp.api.WxCpDepartmentService;
import com.xizhuan.wx.cp.api.WxCpService;
import com.xizhuan.wx.cp.bean.WxCpDepart;
import com.xizhuan.wx.cp.util.json.WxCpGsonBuilder;

import java.util.List;

import static com.xizhuan.wx.cp.constant.WxCpApiPathConsts.Department.*;
import static com.xizhuan.wx.cp.constant.WxCpApiPathConsts.Department.DEPARTMENT_CREATE;
import static com.xizhuan.wx.cp.constant.WxCpApiPathConsts.Department.DEPARTMENT_DELETE;
import static com.xizhuan.wx.cp.constant.WxCpApiPathConsts.Department.DEPARTMENT_LIST;
import static com.xizhuan.wx.cp.constant.WxCpApiPathConsts.Department.DEPARTMENT_UPDATE;

/**
 * <pre>
 *  部门管理接口
 *  Created by BinaryWang on 2017/6/24.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RequiredArgsConstructor
public class WxCpDepartmentServiceImpl implements WxCpDepartmentService {
  private final WxCpService mainService;

  @Override
  public Long create(WxCpDepart depart) throws WxErrorException {
    String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_CREATE);
    String responseContent = this.mainService.post(url, depart.toJson());
    JsonObject tmpJsonObject = GsonParser.parse(responseContent);
    return GsonHelper.getAsLong(tmpJsonObject.get("id"));
  }

  @Override
  public void update(WxCpDepart group) throws WxErrorException {
    String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_UPDATE);
    this.mainService.post(url, group.toJson());
  }

  @Override
  public void delete(Long departId) throws WxErrorException {
    String url = String.format(this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_DELETE), departId);
    this.mainService.get(url, null);
  }

  @Override
  public List<WxCpDepart> list(Long id) throws WxErrorException {
    String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_LIST);
    if (id != null) {
      url += "?id=" + id;
    }

    String responseContent = this.mainService.get(url, null);
    JsonObject tmpJsonObject = GsonParser.parse(responseContent);
    return WxCpGsonBuilder.create()
      .fromJson(tmpJsonObject.get("department"),
        new TypeToken<List<WxCpDepart>>() {
        }.getType()
      );
  }
}
