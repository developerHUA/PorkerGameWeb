package com.huarenkeji.porkergame.base;

import com.alibaba.fastjson.JSON;
import com.huarenkeji.porkergame.bean.DeviceInfo;
import com.huarenkeji.porkergame.common.MD5;

import java.io.Serializable;

import static com.huarenkeji.porkergame.config.NetConfig.SALT;

public class Params<T> implements Serializable {
    private String key; //验证请求接口
    private DeviceInfo deviceInfo;//设备信息
    private String u;// 系统类型 i-->ios , a --> android
    private String appV; // 应用版本
    private long time;
    private T params;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getU() {
        return u;
    }

    public void setU(String u) {
        this.u = u;
    }

    public String getAppV() {
        return appV;
    }

    public void setAppV(String appV) {
        this.appV = appV;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    /**
     *  验证key是否有效
     */
    public boolean keyIsValid() {
        String key = MD5.getMessageDigest(MD5.getMessageDigest((JSON.toJSONString(getParams()) + getTime() + SALT).getBytes()).getBytes());
        return key.equals(getKey());

    }

}
