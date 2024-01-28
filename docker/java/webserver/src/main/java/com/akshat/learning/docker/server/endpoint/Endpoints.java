package com.akshat.learning.docker.server.endpoint;

import com.akshat.learning.docker.server.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/docker")
public class Endpoints {
    @Autowired
    private MessageService messageService;

    @GetMapping
    public String printHelloMessage() {
        return messageService.getHelloMessage();
    }
}
