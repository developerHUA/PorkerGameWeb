package com.huarenkeji.porkergame.config;

public interface SocketConfig {

    // 信号类型 ---- start -----

    int READY = 1; //准备
    int PLAY_PORKER = 2;//出牌
    int JOIN_ROOM = 3; // 有人加入房间
    int EXIT_ROOM = 4; //有人退出房间
    int LANDLORD = 5; // 叫地主
    int SURPLUS_ONE = 6;//剩余一张牌
    int SURPLUS_TWO = 7; //剩余两张牌
    int NO_PLAY = 8; // 不出牌
    int CANCEL_READY = 9; //取消准备
    int DEAL_PORKER = 10; //发牌
    int UNKNOWN_PORKER = 11; // 牌型不正确

    // 信号类型 ---- end ------


    // json key
    String ROOM_NUMBER_KEY = "rn"; //房间号
    String USER_ID_KEY = "uid"; //用户id
    String MESSAGE_TYPE_KEY = "type"; //消息类型
    String PORKER_ARRAY_KEY = "params";

}
