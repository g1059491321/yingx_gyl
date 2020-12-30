package com.baizhi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan("com.baizhi.dao")
//@org.mybatis.spring.annotation.MapperScan("com.baizhi.mapper")
@SpringBootApplication
public class YingxGylApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxGylApplication.class, args);
    }

}
