package com.zmn.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

//@Controller
public class WebSocketController {

    @MessageMapping("/server")
    @SendTo("/topic/client")
    public String push(String message){
        return "ok";
    }

}
