package com.applaptop.springapplaptop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${app.message}")
    String message;

    @GetMapping("/hello")
    public String hello(){
        System.out.println(message);
        return "Hello my friend!";
    }
}
