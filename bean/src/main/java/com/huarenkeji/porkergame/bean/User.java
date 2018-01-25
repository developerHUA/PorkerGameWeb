package com.huarenkeji.porkergame.bean;



import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    private Integer userId;
    private String nickname;
    private Date createDate;
    private int diamond;
    private String phoneNumber;
    private String invitationCode;
    private String openid;
    private String headimgurl;
    private Date lastLoginTime;
    private String token;
    private String unionid;
    private int sex;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public Integer getUserId() {
        return userId;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
//    @Override
//    public String toString() {
//        return "User{" +
//                "userId=" + userId +
//                ", nickname ='" + nickname + '\'' +
//                ", createDate=" + createDate +
//                ", diamond=" + diamond +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", invitationCode='" + invitationCode + '\'' +
//                ", openid='" + openid + '\'' +
//                ", headimgurl='" + headimgurl + '\'' +
//                '}';
//    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", createDate=" + createDate +
                ", diamond=" + diamond +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", invitationCode='" + invitationCode + '\'' +
                ", openid='" + openid + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", token='" + token + '\'' +
                ", unionid='" + unionid + '\'' +
                ", sex=" + sex +
                '}';
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
