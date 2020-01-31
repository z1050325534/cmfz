package com.baizhi.zw.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Article implements Serializable {
@Id
  private String id;
  private String title;
  private String image;
  private String content;
  @JSONField(format = "yyyy-MM-dd")
  private Date createDate;
  @JSONField(format = "yyyy-MM-dd")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date publishDate;
  private String status;
  private String guruId;
  private String filename;
  //多对一
  private  Guru guru;
}
