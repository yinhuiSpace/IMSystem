package com.zmn.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zmn.manager.SessionManager;
import com.zmn.pojo.po.Msg;
import org.springframework.stereotype.Component;
import org.springframework.web.server.WebSession;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatHandler extends TextWebSocketHandler {

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Object sessionId = session.getAttributes().get("session_id");
        if (sessionId != null) {
            SessionManager.add(sessionId.toString(), session);
        } else {
            throw new RuntimeException("登录失效");
        }
        System.out.println("长连接建立,可以开始双向数据传递");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        Object sessionId = session.getAttributes().get("session_id");
        if (sessionId != null) {
            SessionManager.remove(sessionId.toString());
        }
        System.out.println("长连接关闭......");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        Msg msg = new Msg();
        Object sessionId = session.getAttributes().get("session_id");
        if (sessionId != null) {
            msg.setSessionId(sessionId.toString());
        } else {
            msg.setSessionId("session过期");
        }
        msg.setMsg(payload);
        msg.setCreateTime(LocalDateTime.now());
        SessionManager.broadcast(objectMapper.writeValueAsString(msg));
    }
}
