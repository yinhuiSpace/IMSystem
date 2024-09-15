package com.zmn.dao;

import com.zmn.pojo.po.Msg;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MsgMapper {
    private static final List<Msg> msgRepository = new ArrayList<>();
    private static final Map<Long, List<Msg>> msgBeanStore = new HashMap<>();
    private static final Object monitor = MsgMapper.class;
    private static final Long timeout = 10L * 1000L;

    public boolean deviceExists(Long deviceId) {
        return msgBeanStore.containsKey(deviceId);
    }

    public void addDevice(Long deviceId) {
        if (msgBeanStore.containsKey(deviceId)) {
            throw new RuntimeException("该设备已存在");
        }
        msgBeanStore.put(deviceId, new ArrayList<>());
    }

    public void addMsg(Msg msg) {
        synchronized (monitor) {
            for (Map.Entry<Long, List<Msg>> entry : msgBeanStore.entrySet()) {
                List<Msg> msgList = entry.getValue();
                msgList.add(msg);
            }
            monitor.notifyAll();
            msgRepository.add(msg);
        }
    }

    public Msg getMsgByDeviceId(Long deviceId) {
        if (deviceId == null || !deviceExists(deviceId)) {
            addDevice(deviceId);
            return null;
        }
        List<Msg> msgList = msgBeanStore.get(deviceId);

        try {
            synchronized (monitor) {
                if (msgList.isEmpty()) {
                    monitor.wait(timeout);
                    return getNewMsg(deviceId);
                } else {
                    Msg msg = msgList.get(0);
                    msgList.remove(0);
                    return msg;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Msg> getAllMsg() {
        return msgRepository;
    }

    private Msg getNewMsg(Long deviceId) throws InterruptedException {
        List<Msg> msgList = msgBeanStore.get(deviceId);
        if (msgList.isEmpty()) {
            return null;
        } else {
            Msg msg = msgList.get(0);
            msgList.remove(0);
            return msg;
        }
    }
}
