package com.jia.jnmap.nmap.mybatis;

import com.alibaba.fastjson.JSON;
import com.jia.jnmap.nmap.entity.MatchRule;
import com.jia.jnmap.nmap.entity.PortInfo;
import com.jia.jnmap.nmap.entity.Ref;
import com.jia.jnmap.nmap.entity.ScriptInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@MappedTypes({ArrayList.class})
@MappedJdbcTypes({JdbcType.LONGNVARCHAR})
public class ArrayListTypeHandler extends BaseTypeHandler<ArrayList<?>> {

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ArrayList<?> objects, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JSON.toJSONString(objects));
    }

    @Override
    public ArrayList<?> getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String value = resultSet.getString(columnName);
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        Class clazz = null;
        if ("CNNVD_MATCH_RULES".equals(columnName) || "CVE_MATCH_RULES".equals(columnName)) {
            clazz = MatchRule.class;
        } else if ("CNNVD_REFS".equals(columnName) || "CVE_REFS".equals(columnName)) {
            clazz = Ref.class;
        } else if ("PORT_INFOS".equals(columnName)) {
            clazz = PortInfo.class;
        } else if ("SCRIPT_INFOS".equals(columnName)) {
            clazz = ScriptInfo.class;
        }
        if (clazz != null) {
            return new ArrayList<>(JSON.parseArray(value, clazz));
        }
        return null;
    }

    @Override
    public ArrayList<?> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String columnName = resultSet.getMetaData().getColumnName(i);
        return getNullableResult(resultSet, columnName);
    }

    @Override
    public ArrayList<?> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }

}
