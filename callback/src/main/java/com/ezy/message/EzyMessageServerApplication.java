package com.ezy.message;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Caixiaowei-zy
 */
@SpringBootApplication
@MapperScan("com.ezy.message.mapper")
public class EzyMessageServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EzyMessageServerApplication.class, args);
    }

}
