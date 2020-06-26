package com.npn.spring.learning.logger.smallsite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/")
    public String sayHello() {
        return "Main";
    }
}
