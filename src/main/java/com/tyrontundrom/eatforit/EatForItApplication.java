package com.tyrontundrom.eatforit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EatForItApplication {

    public static void main(String[] args) {
        SpringApplication.run(EatForItApplication.class, args);
    }

}
