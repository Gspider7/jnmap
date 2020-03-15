package com.jia.jnmap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.jia.jnmap.mapper")
public class JnmapApplication {

    public static void main(String[] args) {
        SpringApplication.run(JnmapApplication.class, args);
    }

}
