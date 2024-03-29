package com.banking.configurations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigurationsServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigurationsServerApplication.class, args);
    }

}
