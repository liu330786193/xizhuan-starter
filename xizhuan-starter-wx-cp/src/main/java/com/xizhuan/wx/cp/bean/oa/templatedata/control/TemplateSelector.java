package com.xizhuan.wx.cp.bean.oa.templatedata.control;

import com.xizhuan.wx.cp.bean.oa.templatedata.TemplateOptions;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author gyv12345@163.com
 */
@Data
public class TemplateSelector implements Serializable {

  private static final long serialVersionUID = 4995408101489736881L;
  /**
   * single-单选；multi-多选
   */
  private String type;

  private List<TemplateOptions> options;
}
