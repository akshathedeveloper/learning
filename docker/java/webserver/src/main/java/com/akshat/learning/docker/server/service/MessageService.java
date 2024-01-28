package com.akshat.learning.docker.server.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
    public String getHelloMessage() {
        return "Hello!! Let's start learning Docker";
    }
}
