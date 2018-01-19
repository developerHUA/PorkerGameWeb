package com.huarenkeji.porkergame.bean;

import com.huarenkeji.porkergame.base.BaseParams;

import java.util.List;

public class Room extends BaseParams{

    private String roomNumber;
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
