package com.baizhi.zw.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Chapter {
  @Id
  private String id;
  private String title;
  private String url;
  @Column(name = "`size`")
  private Double size;
  private String time;
  @JSONField(format = "yyyy-MM-dd")
  private Date createTime;
  private String albumId;
  private String filename;
}
