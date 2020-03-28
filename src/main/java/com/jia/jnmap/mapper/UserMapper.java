package com.jia.jnmap.mapper;

import com.jia.jnmap.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    int insert(User record);

    int update(User record);

    User selectByUsername(@Param("username")String username);
}