package com.jia.jnmap.mapper;

import com.jia.jnmap.entity.NmapScanResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NmapScanResultMapper {

    int insertSelective(@Param("nmapScanResult") NmapScanResult nmapScanResult);

    int insertList(List<NmapScanResult> list);

    List<NmapScanResult> findByScanId(@Param("scanId") String scanId);

    List<NmapScanResult> selectPage(@Param("scanId") String scanId,
                                    @Param("limit") Integer limit, @Param("offset") Integer offset);

    NmapScanResult selectById(@Param("id") String id);

    int deleteByScanId(@Param("scanId") String scanId);
}
