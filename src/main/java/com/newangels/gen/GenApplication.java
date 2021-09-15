package com.newangels.gen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@Slf4j
public class GenApplication {

    private static final String TIPS = "\n\n" +
            "******************** 代码生成3.0启动成功 ********************\n";

    public static void main(String[] args) {
        SpringApplication.run(GenApplication.class, args);
        log.info(TIPS);
    }

}
