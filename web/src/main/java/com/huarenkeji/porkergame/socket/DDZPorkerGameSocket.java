package com.huarenkeji.porkergame.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.huarenkeji.porkergame.bean.*;
import com.huarenkeji.porkergame.config.SocketConfig;
import com.huarenkeji.porkergame.controller.RoomController;
import com.huarenkeji.porkergame.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@Component
@Scope("prototype")
@ServerEndpoint(value = "/porkerGame/socket/{roomNumber}/{token}/{userId}", configurator = HttpSessionConfigurator.class)
public class DDZPorkerGameSocket {
    private static final Logger logger = LoggerFactory.getLogger(DDZPorkerGameSocket.class);
    private static Map<Integer, List<DDZPorkerGameSocket>> allSocket = new HashMap<>();
    // 已当前玩家顺序进行排座
    private List<DDZPorkerGameSocket> userOrder = new ArrayList<>();
    private Session session;
    private User user;
    private boolean isReady;
    private List<DDZPorker> currentUserPorker = new ArrayList<>();
    private Room roomConfig;
    private List<DDZPorker> playPorker = new ArrayList<>();
    private List<DDZPorker> landlordPorker = new ArrayList<>();
    private int noLocationCount;
    private boolean isLandlord; //当前是否是地主
    private boolean isNoPlay; //当前用户是否没出牌

    @Autowired
    UserService userService;

    @OnOpen
    public void onOpen(@PathParam(value = "roomNumber") int roomNumber,
                       @PathParam(value = "userId") int userId, @PathParam(value = "token") String token,
                       Session session) {
        this.session = session;

        logger.debug("DDZPorkerGameSocket = " + this);

        user = userService.loadUserByUserId(userId);
        if (user == null || !user.getToken().equals(token)) {
            logger.debug("user = " + user);
            logger.debug("token = " + token);
            user = null;
            return;
        }


        roomConfig = RoomController.getRoom(roomNumber);
        if (roomConfig == null) {
            user = null;
            logger.debug("roomConfig = null");
            return;
        }


        if (allSocket.get(roomNumber) == null) {
            List<DDZPorkerGameSocket> list = new ArrayList<>();
            list.add(this);
            allSocket.put(roomNumber, list);
        } else {
            List<DDZPorkerGameSocket> roomSocket = allSocket.get(roomNumber);
            for (int i = 0; i < roomSocket.size(); i++) {
                if (roomSocket.get(i).user.getUserId() == userId) {
                    roomSocket.remove(i);

                    roomSocket.add(i, this);
                }
            }
            if (roomSocket.size() >= roomConfig.getRoomPersonCount()) {
                logger.debug("房间人数已满");
                String message = JSON.toJSONString(SocketBean.messageType(SocketConfig.EXCEED_ROOM_PERSON_COUNT, userId));
                sendSingle(message, session);
                return;
            }
            processOtherUserReady(roomSocket);
            roomSocket.add(this);
            initUserOrder(roomSocket);

            logger.debug("有人加入房间：" + user.getNickname());
            // 有人加入房间
            processJoin(roomSocket);


        }
    }

    /**
     * 处理比当前用户进来早的准备
     */
    private void processOtherUserReady(List<DDZPorkerGameSocket> roomSocket) {
        // 是否有人准备，如果有给当前用户发通知
        for (DDZPorkerGameSocket socket : roomSocket) {
            if (socket != this && socket.isReady) {
                String message = SocketBean.messageType(SocketConfig.READY, socket.user.getUserId()).toJson();
                sendSingle(message, session);
            }
        }
    }

    /**
     * 初始化用户顺序
     */
    private void initUserOrder(List<DDZPorkerGameSocket> roomSocket) {
        if (roomSocket.size() == roomConfig.getRoomPersonCount()) {
            for (int i = 0; i < roomSocket.size(); i++) {
                for (int j = 0; j < i; j++) {
                    roomSocket.get(i).userOrder.add(roomSocket.get(j));
                }

                for (int j = roomSocket.size() - 1; j > i; j--) {
                    roomSocket.get(i).userOrder.add(0, roomSocket.get(j));
                }

                roomSocket.get(i).userOrder.add(roomSocket.get(i));
            }
        }
    }


    private void gameOver(List<DDZPorkerGameSocket> roomSocket) {
        for (DDZPorkerGameSocket aRoomSocket : roomSocket) {
            aRoomSocket.isReady = false;
            aRoomSocket.currentUserPorker.clear();
            aRoomSocket.isLandlord = false;
            aRoomSocket.isNoPlay = true;
            aRoomSocket.noLocationCount = 0;
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
            logger.debug("房间人数" + roomSocket.size());
            JsonElement jsonElement = new JsonParser().parse(message);
            int type = jsonElement.getAsJsonObject().get(SocketConfig.MESSAGE_TYPE_KEY).getAsInt();
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
                    isNoPlay = true;
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
                case SocketConfig.NO_LANDLORD:
                    processNoLandlord(roomSocket);
                    break;

            }

        }

    }


    /**
     * 处理不叫地主
     */
    private void processNoLandlord(List<DDZPorkerGameSocket> roomSocket) {
        noLocationCount++;
        //　发送不叫地主信息
        String noLandlord = SocketBean.messageType(SocketConfig.NO_LANDLORD, user.getUserId()).toJson();
        sendRoom(roomSocket, noLandlord);
        if (userOrder.get(0).noLocationCount >= 1) {
            // 如果不叫地主次数用完　强行成为地主并且告诉客户端
            processLandlordCountFinish(roomSocket, userOrder.get(0));
        } else {
            // 告诉客户端现在地主用id
            processIsLandlord(roomSocket, userOrder.get(0).user.getUserId());
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
     * 处理叫地主次数已用完
     */
    private void processLandlordCountFinish(List<DDZPorkerGameSocket> roomSocket, DDZPorkerGameSocket socket) {
        socket.isLandlord = true;
        currentUserPorker.addAll(landlordPorker);
        String json = JSON.toJSONString(SocketBean.messageParams(SocketConfig.LANDLORD_COUNT_FINISH, socket.user.getUserId(), landlordPorker));
        sendRoom(roomSocket, json);
    }


    /**
     * 处理用户准备
     */
    private void processReady(List<DDZPorkerGameSocket> roomSocket) {
        isReady = true;
        isLandlord = false;
        noLocationCount = 0;
        currentUserPorker.clear();
        if (roomSocket.size() == roomConfig.getPlayType()) {
            for (DDZPorkerGameSocket socket : roomSocket) {
                if (!socket.isReady) {
                    sendRoom(roomSocket, JSON.toJSONString(SocketBean.messageType(SocketConfig.READY, user.getUserId())));
                    return;
                }
            }
            processSendPoker(roomSocket);
        } else {
            sendRoom(roomSocket, JSON.toJSONString(SocketBean.messageType(SocketConfig.READY, user.getUserId())));
        }

    }


    /**
     * 处理发牌
     */

    private void processSendPoker(List<DDZPorkerGameSocket> roomSocket) {
        if (roomConfig == null) {
            return;
        }
        List<DDZPorker> allPorker = DDZPorker.getShufflePoker(roomConfig.getRuleType());
        int landlordSize = 4; // 地主牌的数量
        if (roomConfig.getPlayType() == Room.D_D_Z_THREE_TYPE
                && roomConfig.getRuleType() == Room.NO_REMOVE) {
            landlordSize = 3;
        }

        for (int i = allPorker.size() - 1; i >= landlordSize; i--) {
            roomSocket.get(i % roomSocket.size()).currentUserPorker.add(allPorker.get(i));
            allPorker.remove(i);
        }

        for (DDZPorkerGameSocket socket : roomSocket) {
            SocketBean socketBean = new SocketBean();
            socketBean.setType(SocketConfig.DEAL_PORKER);
            socketBean.setParams(socket.currentUserPorker);
            socket.landlordPorker = allPorker;
            sendSingle(JSON.toJSONString(socketBean), socket.session);
        }

        Random random = new Random();
        int landlordIndex = random.nextInt(roomSocket.size());
        processIsLandlord(roomSocket, roomSocket.get(landlordIndex).user.getUserId());
    }


    /**
     * 处理当前地主是谁
     */
    private void processIsLandlord(List<DDZPorkerGameSocket> roomSocket, int userId) {
        String json = JSON.toJSONString(SocketBean.messageType(SocketConfig.IS_LANDLORD, userId));
        sendRoom(roomSocket, json);
    }

    /**
     * 处理用户出牌
     */
    private void processPlayPorker(String message, List<DDZPorkerGameSocket> roomSocket) {
        JSONObject jsonObject = JSON.parseObject(message);
        JSONArray jsonArray = jsonObject.getJSONArray(SocketConfig.PORKER_ARRAY_KEY);
        playPorker = JSON.parseArray(jsonArray.toJSONString(), DDZPorker.class);
        // typeArr[0] 为牌的类型 typeArr[1] 为牌的大小
        int[] typeArr = DDZLogicBean.getPorkerType(playPorker, roomConfig.getPlayType(), roomConfig.getRuleType());
        String sendJson;
        if (typeArr[0] == DDZLogicBean.UNKNOWN) { //用户出的牌型不正确
            playPorker.clear();
            sendJson = JSON.toJSONString(SocketBean.messageType(SocketConfig.UNKNOWN_PORKER, user.getUserId()));
            sendSingle(sendJson, session);
            return;
        }

        List<DDZPorker> lastPorker = null;
        for (int i = userOrder.size() - 2; i >= 0; i--) {
            if (!userOrder.get(i).isNoPlay) { //上个玩家出过牌
                lastPorker = userOrder.get(i).playPorker;
                break;
            }

        }


        if (DDZLogicBean.comparablePorker(playPorker, lastPorker, roomConfig.getPlayType(), roomConfig.getRuleType())) {
            sendJson = JSON.toJSONString(SocketBean.messageParams(SocketConfig.PLAY_PORKER, user.getUserId(), playPorker));
            sendRoom(roomSocket, sendJson);
            isNoPlay = false;
            for (int i = currentUserPorker.size() - 1; i >= 0; i--) {
                for (int j = playPorker.size() - 1; j >= 0; j--) {
                    if (currentUserPorker.get(i).porkerId == playPorker.get(j).porkerId) {
                        currentUserPorker.remove(i);
                        break;
                    }
                }

            }
            if (currentUserPorker.size() == 2) {
                processSurplus(roomSocket, SocketConfig.SURPLUS_TWO);
            } else if (currentUserPorker.size() == 1) {
                processSurplus(roomSocket, SocketConfig.SURPLUS_ONE);
            } else if (currentUserPorker.size() == 0) {
                processGameOver(roomSocket);
            }

        } else {
            sendJson = JSON.toJSONString(SocketBean.messageType(SocketConfig.UNKNOWN_PORKER, user.getUserId()));
            sendSingle(sendJson, session);
        }


    }

    /**
     * 处理游戏结束
     */
    private void processGameOver(List<DDZPorkerGameSocket> roomSocket) {

        int messageType;
        if (isLandlord) {
            messageType = SocketConfig.LANDLORD_VICTORY;
        } else {
            messageType = SocketConfig.FARMER_VICTORY;
        }
        gameOver(roomSocket); // 重新初始化
        sendRoom(roomSocket, JSON.toJSONString(SocketBean.messageType(messageType, user.getUserId())));

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
        RoomController.exitRoom(roomConfig.getRoomNumber(), user.getUserId());
        for (DDZPorkerGameSocket socket : roomSocket) {
            socket.userOrder.clear();
        }
        roomSocket.remove(this);
        if (roomSocket.size() == 0) {
            allSocket.remove(roomConfig.getRoomNumber());
        }

        String json = JSON.toJSONString(SocketBean.messageType(SocketConfig.EXIT_ROOM, user.getUserId()));
        sendRoom(roomSocket, json);
    }

    /**
     * 处理用户叫地主
     */
    private void processLandlord(List<DDZPorkerGameSocket> roomSocket) {
        isLandlord = true;
        currentUserPorker.addAll(landlordPorker);
        String json = JSON.toJSONString(SocketBean.messageParams(SocketConfig.LANDLORD, user.getUserId(), landlordPorker));
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
        logger.debug("onError ");
        error.printStackTrace();
        if (roomConfig != null) {
            List<DDZPorkerGameSocket> roomSocket = allSocket.get(roomConfig.getRoomNumber());
            if (roomSocket != null) {
                processExit(roomSocket);
            }
        }
    }

    @OnClose
    public void onClose() {
        logger.debug("onClose ");
        if (user != null) {
            logger.debug("onClose " + user.getNickname());
        }
        if (roomConfig != null) {
            List<DDZPorkerGameSocket> roomSocket = allSocket.get(roomConfig.getRoomNumber());
            if (roomSocket != null) {
                processExit(roomSocket);
            }
        }
    }

    /**
     * 发送给某个房间
     */
    private void sendRoom(List<DDZPorkerGameSocket> room, String message) {
        logger.debug("room size = " + room.size());
        for (DDZPorkerGameSocket dDZPorkerGameSocket : room) {
            try {
                logger.debug(dDZPorkerGameSocket.user.getNickname());
                dDZPorkerGameSocket.session.getBasicRemote().sendText(message);
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
