package com.baizhi.zw.entity;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Log implements Serializable {
  @Id
  @ExcelProperty(index = 0)
  private String id;
  @ExcelProperty(index = 1)
  private String name;
  @ExcelProperty(index = 2)
  private String annotation;
  @JSONField(format = "yyyy-MM-dd")
  @ExcelProperty(index = 3)
  private Date time;
  @ExcelProperty(index = 4)
  private String flag;
}
