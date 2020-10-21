package com.xizhuan.wx.cp.api.impl;

import com.xizhuan.wx.common.error.WxErrorException;
import com.xizhuan.wx.common.util.json.WxGsonBuilder;
import com.xizhuan.wx.cp.api.WxCpService;
import com.xizhuan.wx.cp.api.WxCpTaskCardService;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xizhuan.wx.cp.constant.WxCpApiPathConsts.TaskCard.UPDATE_TASK_CARD;

/**
 * <pre>
 *  任务卡片管理接口.
 *  Created by Jeff on 2019-05-16.
 * </pre>
 *
 * @author <a href="https://github.com/domainname">Jeff</a>
 * @date 2019-05-16
 */
@RequiredArgsConstructor
public class WxCpTaskCardServiceImpl implements WxCpTaskCardService {
  private final WxCpService mainService;

  @Override
  public void update(List<String> userIds, String taskId, String clickedKey) throws WxErrorException {
    Integer agentId = this.mainService.getWxCpConfigStorage().getAgentId();

    Map<String, Object> data = new HashMap<>(4);
    data.put("userids", userIds);
    data.put("agentid", agentId);
    data.put("task_id", taskId);
    data.put("clicked_key", clickedKey);

    String url = this.mainService.getWxCpConfigStorage().getApiUrl(UPDATE_TASK_CARD);
    this.mainService.post(url, WxGsonBuilder.create().toJson(data));
  }
}
