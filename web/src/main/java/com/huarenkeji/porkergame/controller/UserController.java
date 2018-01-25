package com.huarenkeji.porkergame.controller;

import com.huarenkeji.porkergame.base.Params;
import com.huarenkeji.porkergame.base.Result;
import com.huarenkeji.porkergame.bean.User;
import com.huarenkeji.porkergame.common.MD5;
import com.huarenkeji.porkergame.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


@Controller
@RequestMapping(value = "/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;



    @RequestMapping(value = "/wxLogin", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUser(@RequestBody Params<User> params) {
        User user = params.getParams();
        Date currentDate = new Date();
        String token = MD5.md5(String.valueOf(currentDate.getTime()));
        user.setLastLoginTime(currentDate);
        user.setToken(token);

        if (userService.loadUserByOpenId(user.getOpenid()) != null) {
            userService.upDateUserInfo(user);
        } else {
            user.setCreateDate(currentDate);
            user.setDiamond(8);
            userService.saveUser(user);
        }

        return Result.getSuccessResult(userService.loginInfo(user.getOpenid()));
    }

}
