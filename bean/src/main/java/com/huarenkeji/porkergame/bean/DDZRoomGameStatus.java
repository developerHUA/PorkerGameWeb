package com.huarenkeji.porkergame.bean;

/**
 * 记录斗地主每个房间游戏状态值
 */
public class DDZRoomGameStatus {
    public int gameScore; // 当前这一局的分数
    public boolean isInTheGame; //是否在游戏中
    public int curGameCT; // 当前游戏局数
    public int nextPlayUserId;//该哪个用户出牌
}
