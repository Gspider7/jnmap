package com.jia.jnmap.nmap.mybatis;

import com.alibaba.fastjson.JSON;
import com.jia.jnmap.nmap.entity.SystemInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes({SystemInfo.class})
@MappedJdbcTypes({JdbcType.LONGNVARCHAR})
public class SystemInfoTypeHandler extends BaseTypeHandler<SystemInfo> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, SystemInfo objects, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JSON.toJSONString(objects));
    }

    @Override
    public SystemInfo getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String value = resultSet.getString(columnName);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return JSON.parseObject(value, SystemInfo.class);
    }

    @Override
    public SystemInfo getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String columnName = resultSet.getMetaData().getColumnName(i);
        return getNullableResult(resultSet, columnName);
    }

    @Override
    public SystemInfo getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }

}
