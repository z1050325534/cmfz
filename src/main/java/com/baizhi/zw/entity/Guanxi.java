package com.baizhi.zw.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Guanxi {
@Id
  private String id;
  private String uid;
  private String guruId;
}
