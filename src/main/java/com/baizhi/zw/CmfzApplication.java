package com.baizhi.zw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.baizhi.zw.dao")
public class CmfzApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmfzApplication.class, args);
	}

}
