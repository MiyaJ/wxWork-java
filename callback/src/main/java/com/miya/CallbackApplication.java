package com.miya;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Caixiaowei
 * @ClassName CallbackApplication
 * @Description
 * @createTime 2020/9/7 15:31$
 */
@SpringBootApplication
@MapperScan("com.miya.mapper")
public class CallbackApplication {

    public static void main(String[] args) {
        SpringApplication.run(CallbackApplication.class, args);
    }

}
