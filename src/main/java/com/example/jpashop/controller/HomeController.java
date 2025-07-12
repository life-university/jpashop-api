package com.example.jpashop.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    Logger logger = LoggerFactory.getLogger(HomeController.class);

    @GetMapping("")
    public String home() {
        log.info("slf4j annotation - home controller called");
        logger.info("logger factory - home controller called");
        return "home";
    }

    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "Hello World!");
        return "hello";
    }

}
