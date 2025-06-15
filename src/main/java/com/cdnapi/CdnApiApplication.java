package com.cdnapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cdnapi"})
public class CdnApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CdnApiApplication.class, args);
    }
}
