package com.baizhi.zw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Admin implements Serializable {
    @Id
    private String id;
    private String username;
    private String password;

}
