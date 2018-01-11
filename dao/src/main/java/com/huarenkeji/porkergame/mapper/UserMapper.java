package com.huarenkeji.porkergame.mapper;

import com.huarenkeji.porkergame.bean.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Select(value = "select * from users where username = #{username} ")
    @Results({
            @Result(id = true, column = "userId", property = "userId"),
            @Result(column = "userId", property = "userId"),
            @Result(column = "headImageUrl", property = "headImageUrl"),
            @Result(column = "diamond", property = "diamond"),
            @Result(column = "phoneNumber", property = "phoneNumber"),
            @Result(column = "invitationCode", property = "invitationCode")
    })
    User loadUserByUsername(@Param("openId") String openId);

    @Insert(value = "insert into users (username, password, createDate,enabled,diamond,phoneNumber,invitationCode,lastLoginTime,openId,headImageUrl)" +
            " value(#{username},#{password},#{createDate},#{enabled},#{diamond},#{phoneNumber},#{invitationCode}," +
            "#{lastLoginTime},#{openId},#{headImageUrl})")
    void saveUser(User user);

}
