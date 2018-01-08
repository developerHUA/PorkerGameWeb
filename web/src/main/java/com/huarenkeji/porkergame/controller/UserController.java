package com.huarenkeji.porkergame.controller;

import com.alibaba.fastjson.JSON;
import com.huarenkeji.porkergame.bean.User;
import com.huarenkeji.porkergame.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")// isAuthenticated 如果用户不是匿名用户就返回true
    public String showHomePage() {
        System.out.println("------ showHomePage");

        try {
            userService.loadUserByUsername("huayaowei");
            logger.info("load user ");
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
        }

        return "/index/index";
    }



    @RequestMapping(value = "getUser")
    @ResponseBody
    public String getUser(String username) {


       return JSON.toJSONString(userService.loadUserByUsername(username));

    }

    @RequestMapping(value = "register")
    @ResponseBody
    public String saveUser(String name) {

        User user = new User();
        user.setUsername(name);
        user.setDiamond(8);
        user.setCreateDate(new Date(System.currentTimeMillis()));
        user.setPhoneNumber("18835700570");
        user.setInvitationCode("47sfk");
        user.setPassword("324245");
        user.setEnabled(true);
        userService.saveUser(user);
        return "{注册成功}";
    }


}
