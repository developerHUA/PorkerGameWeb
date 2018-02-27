package com.huarenkeji.porkergame.bean;

import com.huarenkeji.porkergame.base.BaseParams;

import java.util.List;

public class Room extends BaseParams {

    public static final int D_D_Z_THREE_TYPE = 3; //三人斗地主类型
    public static final int D_D_Z_FOUR_TYPE = 4; //四人人斗地主类型


    public static final int NO_REMOVE = 0; //不去牌
    public static final int REMOVE_DOUBLE_TWO = 1; // 去掉两个2
    public static final int REMOVE_DOUBLE_THREE = 2; // 去掉两个3
    public static final int REMOVE_ONE_AND_TWO = 3; // 去掉一个2一个A


    private int roomNumber; //房间号
    private List<User> users; //房间内用户
    private int playType; // 房间类型 目前只有斗地主类型
    private int ruleType; //去掉牌的类型
    private int defaultScore; //房间底分

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


    public int getRoomPersonCount() {
        if (this.playType == D_D_Z_FOUR_TYPE) {
            return 4;
        } else if (this.playType == D_D_Z_THREE_TYPE) {
            return 3;
        }

        return 0;
    }

    public int getDefaultScore() {
        if (0 == defaultScore) {
            return 1;
        }
        return defaultScore;
    }

    public void setDefaultScore(int defaultScore) {
        this.defaultScore = defaultScore;
    }

}
