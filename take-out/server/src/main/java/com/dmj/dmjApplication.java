package com.dmj;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //开启注解方式的事务管理
@Slf4j
@EnableCaching // 开启缓存注解功能
@EnableScheduling //开启任务注解
public class dmjApplication {
    public static void main(String[] args) {
        SpringApplication.run(dmjApplication.class, args);
        log.info("server started");
    }
}
