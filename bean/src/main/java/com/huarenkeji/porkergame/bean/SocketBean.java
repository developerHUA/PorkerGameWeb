package com.huarenkeji.porkergame.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocketBean {

    private int messageType;
    private int userId;
    private Object params;

    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }



    public static SocketBean messageType(int messageType,int userId) {

        SocketBean socketBean = new SocketBean();
        socketBean.setMessageType(messageType);
        socketBean.setUserId(userId);
        return socketBean;
    }

    public static SocketBean messageParams(int messageType,int userId,Object params) {
        SocketBean socketBean = messageType(messageType,userId);
        socketBean.setParams(params);
        return socketBean;
    }

}
