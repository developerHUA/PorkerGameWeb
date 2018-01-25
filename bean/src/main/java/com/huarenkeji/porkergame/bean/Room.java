package com.huarenkeji.porkergame.bean;

import com.huarenkeji.porkergame.base.BaseParams;

import java.util.List;

public class Room extends BaseParams{

    public static final int D_D_Z_THREE_TYPE = 3; //三人斗地主类型
    public static final int D_D_Z_FOUR_TYPE = 4; //四人人斗地主类型



    public static final int ABANDON_DOUBLE_TWO = 1; // 去掉两个2
    public static final int ABANDON_DOUBLE_THREE = 2; // 去掉两个3
    public static final int ABANDON_ONE_AND_TWEO = 3; // 去掉一个2一个A


    private int roomNumber; //房间号
    private List<User> users; //房间内用户
    private int playType; // 房间类型 目前只有斗地主类型
    private int ruleType; //去掉牌的类型

    public int getRuleType() {
        return ruleType;
    }

    public void setRuleType(int ruleType) {
        this.ruleType = ruleType;
    }

    public int getPlayType() {
        return playType;
    }

    public void setPlayType(int playType) {
        this.playType = playType;
    }


    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
