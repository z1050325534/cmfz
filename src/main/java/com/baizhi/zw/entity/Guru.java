package com.baizhi.zw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Guru implements Serializable {
  @Id
  private String id;
  private String name;
  private String photo;
  private String status;
  private String nickName;
  private String filename;


}
