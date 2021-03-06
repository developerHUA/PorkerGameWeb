package com.huarenkeji.porkergame.bean;

import org.springframework.context.annotation.Bean;

import java.util.Date;


public class User {
    private Integer userId;
    private String userName;
    private String password;
    private Date createDate;
    private int diamond;
    private String phoneNumber;
    private String invitationCode;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
