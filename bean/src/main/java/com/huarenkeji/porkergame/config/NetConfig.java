package com.huarenkeji.porkergame.config;

import com.alibaba.fastjson.JSON;
import com.huarenkeji.porkergame.base.Result;

public interface NetConfig {

    String SALT = "huarenkeji2018";



    int SUCCESS_CODE = 1; //成功
    String SUCCESS_MESSAGE = "success";

    int KEY_INVALID_CODE = 100;
    String KEY_INVALID_MESSAGE = "key is invalid";

    String KEY_INVALID_JSON = JSON.toJSONString(new Result());

}
