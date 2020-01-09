package com.baizhi.zw.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Album {
  @Id
  private String id;
  private String title;
  private String score;
  private String author;
  private String broadcast;
  private Integer counts;
  private String description;
  private String url;
  @JSONField(format = "yyyy-MM-dd")
  private Date createDate;
  private String filename;
  private List<Chapter> chapters;
}
