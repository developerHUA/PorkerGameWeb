package com.huarenkeji.porkergame.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.huarenkeji.porkergame.base.Params;
import com.huarenkeji.porkergame.base.Result;
import com.huarenkeji.porkergame.bean.User;
import com.huarenkeji.porkergame.config.NetConfig;
import com.huarenkeji.porkergame.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")// isAuthenticated 如果用户不是匿名用户就返回true
    public String showHomePage() {
        try {
//            userService.loadUserByUsername("admin");
            logger.info("load user ");
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
        }

        return "/index/index";
    }
    @RequestMapping(value = "/wxLogin", method = RequestMethod.POST)
    @ResponseBody
    public String saveUser(@RequestBody String params) {
        logger.debug("----" + params);


        Params<User> baseParam = JSON.parseObject(params, new TypeReference<Params<User>>() {
        });
        Result result = new Result();
        if(!baseParam.keyIsValid()) {
            result.setCode(NetConfig.KEY_INVALID_CODE);
            result.setMessage(NetConfig.KEY_INVALID_MESSAGE);
            return JSON.toJSONString(result);
        }




        result.setCode(NetConfig.SUCCESS_CODE);
        result.setMessage(NetConfig.SUCCESS_MESSAGE);

        return JSON.toJSONString(result);
    }

}
