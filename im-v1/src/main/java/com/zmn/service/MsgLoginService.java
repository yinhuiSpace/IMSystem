package com.zmn.service;

import com.zmn.dao.MsgMapper;
import com.zmn.result.RestResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class MsgLoginService {

    @Resource
    private MsgMapper msgMapper;

    private static final AtomicLong cnt=new AtomicLong(0);


    public RestResult<Long> login() {

        long deviceId = cnt.get();
        cnt.set(deviceId + 1);

        msgMapper.addDevice(deviceId);

        return RestResult.success(deviceId);
    }
}
