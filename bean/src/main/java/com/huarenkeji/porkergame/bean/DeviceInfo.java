package com.huarenkeji.porkergame.bean;

import java.io.Serializable;

public class DeviceInfo implements Serializable {

    private String d; //设备
    private String os;//系统类型
    private int osvc; //系统版本
    private int rw; //屏幕宽度
    private int rh;//屏幕高度
    private String ip; //用户ip
    private String m; //厂商
    private String mac; //mac地址
    private int n; //网络状态
    private int o; //运营商信息
    private String p; //
    private int cvc; //应用版本码
    private String cv; // 应用版本号

    public void setD(String d) {
        this.d = d;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public void setOsvc(int osvc) {
        this.osvc = osvc;
    }

    public void setRw(int rw) {
        this.rw = rw;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setM(String m) {
        this.m = m;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setN(int n) {
        this.n = n;
    }

    public void setO(int o) {
        this.o = o;
    }

    public void setP(String p) {
        this.p = p;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public void setRh(int rh) {
        this.rh = rh;
    }

    public String getD() {
        return d;
    }

    public String getOs() {
        return os;
    }

    public int getOsvc() {
        return osvc;
    }

    public int getRw() {
        return rw;
    }

    public String getIp() {
        return ip;
    }

    public String getM() {
        return m;
    }

    public String getMac() {
        return mac;
    }

    public int getN() {
        return n;
    }

    public int getO() {
        return o;
    }

    public String getP() {
        return p;
    }

    public int getCvc() {
        return cvc;
    }


    public String getCv() {
        return cv;
    }


    public int getRh() {
        return rh;
    }


}
