package com.springbootswaggergradle.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greetings")
public class GreetingsController {

    @GetMapping("/getGreeting/{name}")
    public String getGreeting(@PathVariable("name") String name) throws InterruptedException {
        Thread.sleep(2000);
        return name + " , " + "Good morning!";
    }
}
