package com.huarenkeji.porkergame.config;


public interface NetConfig {

    String SALT = "huarenkeji2018";


    int SUCCESS_CODE = 100; //成功
    String SUCCESS_MESSAGE = "success";
    String SUCCESS_ERROR = "成功";


    int TOKEN_INVALID_CODE = 101;
    String TOKEN_INVALID_MESSAGE = "token is invalid";
    String TOKEN_INVALID_ERROR = "token是无效的";


    int NO_SEARCH_CODE = 102;
    String NO_SEARCH_MESSAGE = "no search result";
    String NO_SEARCH_ERROR = "未找到搜索结果";


    int NO_ROOM_CODE = 103;
    String NO_ROOM_MESSAGE = "no search result";
    String NO_ROOM_ERROR = "未找到搜索结果";


    int KEY_INVALID_CODE = 104;
    String KEY_INVALID_MESSAGE = "key is invalid";
    String KEY_INVALID_ERROR = "key是无效的";
}
