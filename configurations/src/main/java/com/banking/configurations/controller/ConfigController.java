package com.banking.configurations.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("config")
public class ConfigController {

    @GetMapping("/hello")
    public String getAccountDetails() {
        return "Hello World";
    }
}
