package com.huarenkeji.porkergame.mapper;

import com.huarenkeji.porkergame.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select(value="select username,password from users where username = #{userName}")
    User loadUserByUsername(@Param("username") String username);
    @Insert(value="insert into users (username, password,  create_date) value(#{userName},#{password},#{createDate})")
    void saveUser(User user);

}
