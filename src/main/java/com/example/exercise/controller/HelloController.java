package com.example.exercise.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello/{callerName}")
    public String sayHello(@PathVariable("callerName") String callerName) {
        return String.format("Hello, %s!", callerName);
    }
}
