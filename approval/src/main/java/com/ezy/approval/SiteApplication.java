package com.ezy.approval;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.ezy.approval.handler.CompensateHandler;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Author: Kevin Liu
 * @CreateDate: 2020/6/28 15:16
 * @Desc 启动类
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.ezy.approval.mapper")
@EnableScheduling
@EnableTransactionManagement
@EnableCaching
@EnableAsync
@Slf4j
public class SiteApplication implements CommandLineRunner {

    @Autowired
    private CompensateHandler compensateHandler;

    public static void main(String[] args) {
        SpringApplication.run(SiteApplication.class, args);
    }

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    /**
     * 审批节点不可用重启:
     * 从mq 中消费未消费 消息, 更新审批单据
     *
     * @param
     * @return
     * @author Caixiaowei
     * @updateTime 2020/9/14 10:41
     */
    @Override
    public void run(String... args) throws Exception {
        log.info("compensateApprovalRestart --->run--->");
        compensateHandler.compensateApprovalRestart();
    }
}
