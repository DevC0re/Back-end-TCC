package com.devcore.medup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync

public class MedupApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedupApplication.class, args);
    }

}

