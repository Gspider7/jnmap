package com.jia.jnmap.mapper;

import com.jia.jnmap.entity.Scan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScanMapper {

    int insert(Scan record);

    int delete(@Param("id") String id);

    Scan selectById(@Param("id") String id);

    List<Scan> selectPage(@Param("limit") Integer limit, @Param("offset") Integer offset);
}