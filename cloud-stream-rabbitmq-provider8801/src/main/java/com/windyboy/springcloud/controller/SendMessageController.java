package com.windyboy.springcloud.controller;

import com.windyboy.springcloud.service.ImessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMessageController {

    @Resource
    private ImessageProvider messageProvider;

    @GetMapping("sendMessage")
    public String sendMessage() {
        return messageProvider.send();
    }
}
