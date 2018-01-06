package com.huarenkeji.porkergame.controller;

import com.huarenkeji.porkergame.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    @PreAuthorize("isAuthenticated()")// isAuthenticated 如果用户不是匿名用户就返回true
    public String showHomePage() {


        try {
            userService.loadUserByUsername("huayaowei");
            logger.info("load user ");
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
        }

        return "/index/index";
    }


}
