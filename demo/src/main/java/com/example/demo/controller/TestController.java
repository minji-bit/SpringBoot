package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Slf4j
public class TestController {

    @GetMapping("/")
    public String index(){
        log.info("잘되니??");
        return "Hello World";
    }

    @GetMapping("/test")
    public List<String> test(){
        System.out.println("test 요청됨...");
        return List.of("A","B","C");
    }


}
