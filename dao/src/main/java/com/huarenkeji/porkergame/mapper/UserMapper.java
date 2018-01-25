package com.huarenkeji.porkergame.mapper;

import com.huarenkeji.porkergame.bean.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Select(value = "select * from users where openId = #{openid} ")
    @Results({
            @Result(id = true, column = "userId", property = "userId"),
            @Result(column = "headimgurl", property = "headimgurl"),
            @Result(column = "diamond", property = "diamond"),
            @Result(column = "invitationCode", property = "invitationCode"),
            @Result(column = "token", property = "token")
    })
    User loadUserByUserOpenId(@Param("openid") String openid);


    @Select(value = "select * from users where userId = #{userId} ")
    User loadUserByUserId(int userId);


    @Select(value = "select userId,headimgurl,token,nickname,sex,diamond from users where openid = #{openid}")
    User loginInfo(@Param("openid") String openid);


    @Insert(value = "insert into users (nickname, sex,createDate,diamond,invitationCode,lastLoginTime,openid,headimgurl,token,unionid)" +
            " value(#{nickname},#{sex},#{createDate},#{diamond},#{invitationCode}," +
            "#{lastLoginTime},#{openid},#{headimgurl},#{token},#{unionid})")
    void saveUser(User user);

    @Update(value = "update users set nickname = #{nickname}, headimgurl = #{headimgurl}, " +
            "token = #{token}, sex = #{sex}, lastLoginTime = #{lastLoginTime} where openid = #{openid}")
    void upDateUserInfo(User user);

}
