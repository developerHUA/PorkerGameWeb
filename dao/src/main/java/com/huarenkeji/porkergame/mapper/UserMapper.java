package com.huarenkeji.porkergame.mapper;

import com.huarenkeji.porkergame.bean.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Select(value="select * from users where username = #{username} ")
    @Results({
            @Result(id = true,column = "id",property = "userId"),
            @Result(column = "id",property = "userId"),
            @Result(column = "username",property = "username"),
            @Result(column = "password",property = "password"),
            @Result(column = "create_date",property = "createDate"),
            @Result(column = "diamond",property = "diamond"),
            @Result(column = "phoneNumber",property = "phoneNumber"),
            @Result(column = "invitationCode",property = "invitationCode")
    })
    User loadUserByUsername(@Param("username") String username);
    @Insert(value="insert into users (username, password, create_date,enabled,diamond,phoneNumber,invitationCode) value(#{username},#{password},#{createDate},#{enabled},#{diamond},#{phoneNumber},#{invitationCode})")
    void saveUser(User user);

}
