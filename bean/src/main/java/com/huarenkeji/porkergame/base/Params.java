package com.huarenkeji.porkergame.base;

import com.huarenkeji.porkergame.bean.DeviceInfo;

import java.io.Serializable;


public class Params<T> implements Serializable {
    private String key; //验证请求接口
    private DeviceInfo deviceInfo;//设备信息
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



}
