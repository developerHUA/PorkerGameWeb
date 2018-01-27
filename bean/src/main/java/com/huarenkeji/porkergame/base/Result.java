package com.huarenkeji.porkergame.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.huarenkeji.porkergame.config.NetConfig;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_NULL)
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

    /**
     *  返回成功结果的code 和 信息
     */
    public static Result getSuccessResult() {
        return getSuccessResult(null);
    }

    /**
     *  返回成功结果的code 信息 数据
     */
    public static Result getSuccessResult(Object result) {
        Result baseResult = new Result(NetConfig.SUCCESS_CODE, NetConfig.SUCCESS_MESSAGE, NetConfig.SUCCESS_ERROR);
        baseResult.setResult(result);
        return baseResult;

    }


    /**
     *  返回缺少必传参数结果 code 信息 数据
     */
    public static Result getLackParam() {
        return new Result(NetConfig.LACK_PARAM_CODE,NetConfig.LACK_PARAM_MESSAGE,NetConfig.LACK_PARAM_ERROR);
    }

    /**
     *  返回无效的key code 信息 数据
     */
    public static Result getInValidKeyResult() {
        return new Result(NetConfig.KEY_INVALID_CODE,NetConfig.KEY_INVALID_MESSAGE,NetConfig.KEY_INVALID_ERROR);
    }

    /**
     *  返回无效的token code 信息 数据
     */
    public static Result getInValidTokenResult() {
        return new Result(NetConfig.TOKEN_INVALID_CODE,NetConfig.TOKEN_INVALID_MESSAGE,NetConfig.TOKEN_INVALID_ERROR);
    }


    /**
     *  返回未找到搜索结果 code 信息 数据
     */
    public static Result getNoSearchResult() {
        return new Result(NetConfig.NO_SEARCH_CODE,NetConfig.NO_SEARCH_MESSAGE,NetConfig.NO_SEARCH_ERROR);
    }


    /**
     *  返回未找到对方房间结果 code 信息 数据
     */
    public static Result getNoRoomResult() {
        return new Result(NetConfig.NO_ROOM_CODE,NetConfig.NO_ROOM_MESSAGE,NetConfig.NO_ROOM_ERROR);
    }

}
