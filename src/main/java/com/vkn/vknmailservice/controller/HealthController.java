package com.vkn.vknmailservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/v1/send-mail")
public class HealthController {

    @GetMapping("/health")
    public String getHealth(){
        return "SUCCESS";
    }
}
