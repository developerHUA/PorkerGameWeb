package com.huarenkeji.porkergame.bean;

import java.util.List;

public class PlayPorker {

    private int type; // 出牌类型
    private int porkerSize; //出牌大小
    private List<DDZPorker> porkerList; //出的所有牌
    private int userId; // 出牌用户id
    private int playStatus; // 户出牌状态  0 == 未出牌  1 == 不出牌  2 == 已出牌

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPorkerSize() {
        return porkerSize;
    }

    public void setPorkerSize(int porkerSize) {
        this.porkerSize = porkerSize;
    }

    public List<DDZPorker> getPorkerList() {
        return porkerList;
    }

    public void setPorkerList(List<DDZPorker> porkerList) {
        this.porkerList = porkerList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(int playStatus) {
        this.playStatus = playStatus;
    }
}
