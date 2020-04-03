package com.jia.jnmap.mapper;

import com.jia.jnmap.entity.SystemLog;
import org.apache.ibatis.annotations.Param;

public interface SystemLogMapper {

    int insert(SystemLog record);

    SystemLog selectLast(@Param("operation") String operation, @Param("username") String username,
                         @Param("status") Integer status);
}