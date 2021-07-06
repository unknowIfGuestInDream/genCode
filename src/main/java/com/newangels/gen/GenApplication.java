package com.newangels.gen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GenApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenApplication.class, args);
    }

}
