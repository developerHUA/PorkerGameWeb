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
    int NO_LANDLORD = 12;// 不叫地主
    int IS_LANDLORD = 13;// 是地主
    int LANDLORD_COUNT_FINISH = 14;// 不叫地主次数已用完
    int LANDLORD_VICTORY = 15;// 地主胜利
    int FARMER_VICTORY = 16;// 农民胜利
    int SCORE_CHANGED = 18;// 当前游戏分数发生变化
    int USER_SCORE_CHANGED = 19;// 当前用户分数发生变化
    int CONNECTED = 20;// 连接成功
    int RECONNECTED = 21;// 重新连接成功
    int ROOM_IS_FULL = 22;// 房间人数已满
    // 信号类型 ---- end ------


    // json key
    String ROOM_NUMBER_KEY = "rn"; //房间号
    String USER_ID_KEY = "uid"; //用户id
    String MESSAGE_TYPE_KEY = "type"; //消息类型
    String PORKER_ARRAY_KEY = "params";

}
