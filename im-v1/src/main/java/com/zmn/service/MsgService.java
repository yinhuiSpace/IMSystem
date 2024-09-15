package com.zmn.service;

import com.zmn.dao.MsgMapper;
import com.zmn.pojo.bo.MsgBo;
import com.zmn.pojo.po.Msg;
import com.zmn.pojo.vo.MsgBean;
import com.zmn.result.RestResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Service
public class MsgService {

    @Resource
    private MsgMapper msgMapper;

    public RestResult<Void> sendMsg(MsgBo msgBo) {
        if (!msgMapper.deviceExists(msgBo.getDeviceId())) {
            msgMapper.addDevice(msgBo.getDeviceId());
        }
        Msg msg = Msg.fromBo(msgBo);
        msgMapper.addMsg(msg);
        return RestResult.success();
    }

    public RestResult<List<Msg>> getAllMsg() {
        return RestResult.success(msgMapper.getAllMsg());
    }

    public RestResult<Msg> getNewMsg(Long deviceId) {
        Msg msg = msgMapper.getMsgByDeviceId(deviceId);
        if (msg == null) {
            return RestResult.fail("暂无新消息");
        }
        return RestResult.success(msg);
    }
}
