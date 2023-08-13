package com.compass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class APIStarter {
    public static void main(String[] args) {

        SpringApplication.run(APIStarter.class, args);
    }
}