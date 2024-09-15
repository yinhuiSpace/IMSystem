package com.zmn.controller;

import com.zmn.result.RestResult;
import com.zmn.service.MsgLoginService;
import com.zmn.service.MsgService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;

@RestController
@RequestMapping("/api")
public class IMLoginController {

    @Resource
    private MsgLoginService msgLoginService;

    @PostMapping("/login")
    public RestResult<Long> login(){
        return msgLoginService.login();
    }
}
