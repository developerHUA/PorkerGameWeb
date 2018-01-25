package com.huarenkeji.porkergame.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SocketBean {

    private int type; //消息类型
    private int uid; // 用户id
    private Object params;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public Object getParams() {
        return params;
    }

    public void setParams(Object params) {
        this.params = params;
    }



    public static SocketBean messageType(int messageType,int userId) {

        SocketBean socketBean = new SocketBean();
        socketBean.setType(messageType);
        socketBean.setUid(userId);
        return socketBean;
    }

    public static SocketBean messageParams(int messageType,int userId,Object params) {
        SocketBean socketBean = messageType(messageType,userId);
        socketBean.setParams(params);
        return socketBean;
    }

}
