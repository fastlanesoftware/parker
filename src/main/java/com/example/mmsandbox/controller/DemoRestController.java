package com.example.mmsandbox.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DemoRestController {

    @GetMapping("/test")
    public String getTest() {
        return "it works";
    }

}
