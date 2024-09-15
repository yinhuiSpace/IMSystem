package com.zmn.controller;

import com.zmn.pojo.bo.MsgBo;
import com.zmn.pojo.po.Msg;
import com.zmn.result.RestResult;
import com.zmn.service.MsgService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api")
public class IMController {

    @Resource
    private MsgService msgService;

    @PostMapping("/send_message")
    public RestResult<Void> sendMessage(@RequestBody MsgBo msgBo){

        return msgService.sendMsg(msgBo);
    }

    @GetMapping("/get_all_msg")
    public RestResult<List<Msg>> getAllMsg(){
        return msgService.getAllMsg();
    }

    @GetMapping("/get_new_msg")
    public RestResult<Msg> getNewMsg(Long deviceId){
        return msgService.getNewMsg(deviceId);
    }
}
