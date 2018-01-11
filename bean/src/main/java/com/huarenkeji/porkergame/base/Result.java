package com.huarenkeji.porkergame.base;

import com.alibaba.fastjson.JSON;
import com.huarenkeji.porkergame.config.NetConfig;

import java.io.Serializable;

public class Result implements Serializable {
    private int code;
    private String message;
    private String errMsg;
    private Object result;

    public Result() {

    }

    public Result(int code, String message, String errMsg) {
        this.code = code;
        this.message = message;
        this.errMsg = errMsg;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public static String getSuccessJson() {
        return getSuccessJson(null);
    }


    public static String getSuccessJson(Object result) {
        Result baseResult = new Result(NetConfig.SUCCESS_CODE, NetConfig.SUCCESS_MESSAGE, NetConfig.SUCCESS_ERROR);
        baseResult.setResult(result);
        return JSON.toJSONString(baseResult);

    }


    public static String getInValidKeyJson() {
        return JSON.toJSONString(new Result(NetConfig.KEY_INVALID_CODE,NetConfig.KEY_INVALID_MESSAGE,NetConfig.KEY_INVALID_ERROR));
    }


}
