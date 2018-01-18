package com.huarenkeji.porkergame.soket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/porkerGame/socket", configurator = HttpSessionConfigurator.class)
public class PorkerGameSocket {
    private static final Logger logger = LoggerFactory.getLogger(PorkerGameSocket.class);
    private static CopyOnWriteArraySet<PorkerGameSocket> webSocketSet = new CopyOnWriteArraySet<>();
    private static Map<String, List<PorkerGameSocket>> allSocket = new HashMap<>();
    private Session session;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        logger.debug("onOpen ---");
        this.session = session;
//        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
//        String roomNumber = (String) httpSession.getAttribute("roomNumber");
        String roomNumber = "001";
        logger.debug("onOpen ---" + roomNumber);


        if (allSocket.get(roomNumber) == null) {
            List<PorkerGameSocket> list = new ArrayList<>();
            list.add(this);
            allSocket.put(roomNumber, list);
        } else {
            allSocket.get(roomNumber).add(this);
        }


    }


    @OnMessage
    public void onMessage(String message) {
        logger.debug("onMessage --- " + message);
        sendSingle("我收到了消息 你发送的是：" + message, session);
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
    }

    /**
     * 发送给某个房间
     */
    private void sendRoom(List<PorkerGameSocket> room, String message) {
        for (PorkerGameSocket porkerGameSocket : room) {
            try {
                porkerGameSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送给所有人
     */
    private void sendAllPeople(String message) {
        for (Map.Entry<String, List<PorkerGameSocket>> all : allSocket.entrySet()) {
            sendRoom(all.getValue(), message);
        }

    }


    private void sendSingle(String message, Session session) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
