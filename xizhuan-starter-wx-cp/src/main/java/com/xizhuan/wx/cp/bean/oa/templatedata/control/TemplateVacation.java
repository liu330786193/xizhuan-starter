package com.xizhuan.wx.cp.bean.oa.templatedata.control;

import com.xizhuan.wx.cp.bean.oa.templatedata.TemplateVacationItem;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author gyv12345@163.com
 */
@Data
public class TemplateVacation implements Serializable {

  private List<TemplateVacationItem> item;

}
