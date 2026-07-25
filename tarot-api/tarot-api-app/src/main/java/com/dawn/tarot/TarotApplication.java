package com.dawn.tarot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.dawn.tarot")
@MapperScan("com.dawn.tarot.infrastructure.mapper")
public class TarotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TarotApplication.class, args);
    }
}
