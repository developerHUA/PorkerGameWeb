package com.huarenkeji.porkergame.service;

import com.huarenkeji.porkergame.bean.User;
import com.huarenkeji.porkergame.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务接口
 *
 */
@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;


    @Transactional
    public User loadUserByOpenId(String openId) {
        return userMapper.loadUserByUserOpenId(openId);
    }


    @Transactional
    public User loadUserByUserId(int userId) {
        return userMapper.loadUserByUserId(userId);
    }

    @Transactional
    public User loginInfo(String openId) {
        return userMapper.loginInfo(openId);
    }
    @Transactional
    public void saveUser(User user) {
        userMapper.saveUser(user);
//        测试异常后数据是否回滚
//        getError();
    }


    @Transactional
    public void upDateUserInfo(User user) {
        userMapper.upDateUserInfo(user);
//        测试异常后数据是否回滚
//        getError();
    }


}