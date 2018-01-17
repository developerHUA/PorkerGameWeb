package com.huarenkeji.porkergame.controller;

import com.huarenkeji.porkergame.base.Params;
import com.huarenkeji.porkergame.base.Result;
import com.huarenkeji.porkergame.bean.User;
import com.huarenkeji.porkergame.common.MD5;
import com.huarenkeji.porkergame.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


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
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
        }

        return "/index/index";
    }

    @RequestMapping(value = "/wxLogin", method = RequestMethod.POST)
    @ResponseBody
    public Result saveUser(@RequestBody Params<User> params) {
        User user = params.getParams();
        Date currentDate = new Date();
        String token = MD5.getMessageDigest(String.valueOf(currentDate.getTime()).getBytes());
        user.setLastLoginTime(currentDate);
        user.setToken(token);

        if (userService.loadUserByOpenId(user.getOpenId()) != null) {
            userService.upDateUserInfo(user);
            logger.debug("-----upDateUserInfo");
        } else {
            user.setCreateDate(currentDate);
            user.setDiamond(8);
            userService.saveUser(user);
            logger.debug("-----saveUser");
        }

        return Result.getSuccessJson(userService.loadUserByOpenId(user.getOpenId()));
    }

}
