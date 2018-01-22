package com.huarenkeji.porkergame.soket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.huarenkeji.porkergame.bean.*;
import com.huarenkeji.porkergame.config.SocketConfig;
import com.huarenkeji.porkergame.controller.RoomController;
import com.huarenkeji.porkergame.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ServerEndpoint(value = "/porkerGame/socket/{roomNumber}/{token}/{userId}", configurator = HttpSessionConfigurator.class)
public class DDZPorkerGameSocket {
    private static final Logger logger = LoggerFactory.getLogger(DDZPorkerGameSocket.class);
    private static Map<Integer, List<DDZPorkerGameSocket>> allSocket = new HashMap<>();
    private Session session;
    private User user;
    private boolean isReady;
    private List<DDZPorker> currentUserPorker = new ArrayList<>();
    private Room roomConfig;
    private List<DDZPorker> playPorker = new ArrayList<>();
    private List<DDZPorker> LandlordPorker = new ArrayList<>();


    @Autowired
    UserService userService;

    @OnOpen
    public void onOpen(@PathParam(value = "roomNumber") int roomNumber,
                       @PathParam(value = "userId") int userId, @PathParam(value = "token") String token,
                       Session session, EndpointConfig config) {
        this.session = session;
        user = userService.loadUserByUserId(userId);
        if (user == null || user.getToken().equals(token)) {
            user = null;
            return;
        }


//        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
//        String roomNumber = (String) httpSession.getAttribute("roomNumber");

        roomConfig = RoomController.getRoom(roomNumber);
        if (roomConfig == null) {
            user = null;
            return;
        }


        if (allSocket.get(roomNumber) == null) {
            List<DDZPorkerGameSocket> list = new ArrayList<>();
            list.add(this);
            allSocket.put(roomNumber, list);
        } else {
            List<DDZPorkerGameSocket> roomSocket = allSocket.get(roomNumber);
            roomSocket.add(this);
            // 有人加入房间
            processJoin(roomSocket);
        }

    }


    @OnMessage
    public void onMessage(String message) {
        logger.debug("onMessage --- " + message);
        if (user == null) {
            return;
        }


        List<DDZPorkerGameSocket> roomSocket = allSocket.get(roomConfig.getRoomNumber());
        if (user != null && roomSocket != null && roomSocket.size() > 0) {
            int type = (int) JSON.parse(SocketConfig.MESSAGE_TYPE_KEY);
            switch (type) {
                case SocketConfig.READY:
                    processReady(roomSocket);
                    break;
                case SocketConfig.CANCEL_READY:
                    processCancelReady(roomSocket);
                    break;
                case SocketConfig.PLAY_PORKER:
                    processPlayPorker(message, roomSocket);
                    break;
                case SocketConfig.NO_PLAY:
                    playPorker.clear();
                    sendRoom(roomSocket, JSON.toJSONString(SocketBean.messageType(SocketConfig.NO_PLAY, user.getUserId())));
                    break;
                case SocketConfig.EXIT_ROOM:
                    processExit(roomSocket);
                    break;
                case SocketConfig.LANDLORD:
                    processLandlord(roomSocket);
                    break;
                case SocketConfig.SURPLUS_ONE:
                    processSurplus(roomSocket, SocketConfig.SURPLUS_ONE);
                    break;
                case SocketConfig.SURPLUS_TWO:
                    processSurplus(roomSocket, SocketConfig.SURPLUS_TWO);
                    break;

            }

        }

    }

    /**
     * 处理用户取消准备
     */
    private void processCancelReady(List<DDZPorkerGameSocket> roomSocket) {
        isReady = false;
        sendRoom(roomSocket, JSON.toJSONString(SocketBean.messageType(SocketConfig.CANCEL_READY, user.getUserId())));
    }


    /**
     * 处理用户准备
     */
    private void processReady(List<DDZPorkerGameSocket> roomSocket) {
        isReady = true;
        currentUserPorker.clear();
        for (DDZPorkerGameSocket socket : roomSocket) {
            if (!socket.isReady) {
                sendRoom(roomSocket, JSON.toJSONString(SocketBean.messageType(SocketConfig.READY, user.getUserId())));
                return;
            }
        }

        processSendPoker(roomSocket);

    }


    /**
     * 处理发牌
     */

    private void processSendPoker(List<DDZPorkerGameSocket> roomSocket) {
        if (roomConfig == null) {
            return;

        }
        List<DDZPorker> allPorker = DDZPorker.getMoveShufflePoker();
        int landlordSize = 3; // 地主牌的数量


        for (int i = allPorker.size() - landlordSize - 1; i >= landlordSize; i--) {
            roomSocket.get(i % roomSocket.size()).currentUserPorker.add(allPorker.get(i));
            allPorker.remove(i);
        }

        for (DDZPorkerGameSocket socket : roomSocket) {
            SocketBean socketBean = new SocketBean();
            socketBean.setMessageType(SocketConfig.DEAL_PORKER);
            socketBean.setParams(socket.currentUserPorker);
            sendSingle(JSON.toJSONString(socketBean), socket.session);
        }

        LandlordPorker = allPorker;


    }


    /**
     * 处理用户出牌
     */
    private void processPlayPorker(String message, List<DDZPorkerGameSocket> roomSocket) {
        JSONObject jsonObject = JSON.parseObject(message);
        JSONArray jsonArray = jsonObject.getJSONArray(SocketConfig.PORKER_ARRAY_KEY);
        String arrayJson = JSONObject.toJSONString(jsonArray, SerializerFeature.WriteClassName);//将array数组转换成字符串
        playPorker = JSON.parseArray(arrayJson, DDZPorker.class);
        // typeArr[0] 为牌的类型 typeArr[1] 为牌的大小
        int[] typeArr = DDZLogicBean.getPorkerType(playPorker);
        String sendJson;
        if (typeArr[0] == DDZLogicBean.UNKNOWN) { //用户出的牌型不正确
            playPorker.clear();
            sendJson = JSON.toJSONString(SocketBean.messageType(SocketConfig.UNKNOWN_PORKER, user.getUserId()));
            sendSingle(sendJson, session);
            return;
        }

        List<DDZPorker> lastPorker = null;
        int index = 0;
        for (int i = 0; i < roomSocket.size(); i++) {
            if (roomSocket.get(i) == this) {
                index = i;
                break;
            }
        }
        for (int i = index - 1; i >= 0; i++) {
            if (roomSocket.get(i).playPorker.size() != 0) {
                lastPorker = roomSocket.get(i).playPorker;
                break;
            }
        }
        if (lastPorker == null) {
            for (int i = roomSocket.size() - 1; i > index; i++) {
                if (roomSocket.get(i).playPorker.size() != 0) {
                    lastPorker = roomSocket.get(i).playPorker;
                    break;
                }
            }
        }

        if (DDZLogicBean.comparablePorker(playPorker, lastPorker)) {
            sendJson = JSON.toJSONString(SocketBean.messageParams(SocketConfig.PLAY_PORKER, user.getUserId(), playPorker));
            sendRoom(roomSocket, sendJson);
        } else {
            sendJson = JSON.toJSONString(SocketBean.messageType(SocketConfig.UNKNOWN_PORKER, user.getUserId()));
            sendSingle(sendJson, session);
        }


    }


    /**
     * 处理用户加入房间
     */
    private void processJoin(List<DDZPorkerGameSocket> roomSocket) {
        String json = JSON.toJSONString(SocketBean.messageParams(SocketConfig.JOIN_ROOM, user.getUserId(), user));
        sendRoom(roomSocket, json);
    }

    /**
     * 处理用户退出房间
     */
    private void processExit(List<DDZPorkerGameSocket> roomSocket) {
        String json = JSON.toJSONString(SocketBean.messageType(SocketConfig.JOIN_ROOM, user.getUserId()));
        sendRoom(roomSocket, json);
    }

    /**
     * 处理用户叫地主
     */
    private void processLandlord(List<DDZPorkerGameSocket> roomSocket) {
        String json = JSON.toJSONString(SocketBean.messageParams(SocketConfig.LANDLORD, user.getUserId(), LandlordPorker));
        sendRoom(roomSocket, json);
    }

    /**
     * 处理用户剩牌
     */
    private void processSurplus(List<DDZPorkerGameSocket> roomSocket, int surplus) {
        String json = JSON.toJSONString(SocketBean.messageType(surplus, user.getUserId()));
        sendRoom(roomSocket, json);
    }


    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    @OnClose
    public void onClose() {
        allSocket.remove(roomConfig.getRoomNumber());
    }

    /**
     * 发送给某个房间
     */
    private void sendRoom(List<DDZPorkerGameSocket> room, String message) {
        for (DDZPorkerGameSocket DDZPorkerGameSocket : room) {
            try {
                DDZPorkerGameSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送给所有人
     */
    private void sendAllPeople(String message) {
        for (Map.Entry<Integer, List<DDZPorkerGameSocket>> all : allSocket.entrySet()) {
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
