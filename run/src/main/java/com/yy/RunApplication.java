package com.yy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching(proxyTargetClass = true)
public class RunApplication {


    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class, args);
    }

}
