package com.zmn.manager;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {

    private static final Map<String, WebSocketSession> clients=new ConcurrentHashMap<>();

    public static void add(String key,WebSocketSession val){
        clients.put(key,val);
    }

    public static void remove(String key){
        clients.remove(key);
    }

    public static void removeAndClose(String key){
        WebSocketSession session = clients.remove(key);
        try {
            session.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static WebSocketSession get(String key){
        return clients.get(key);
    }

    public static void broadcast(String msg){
        try {
            for (String key : clients.keySet()) {
                WebSocketSession session = clients.get(key);
                session.sendMessage(new TextMessage(msg));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
