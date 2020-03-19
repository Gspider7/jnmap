package com.jia.jnmap.mapper;

import com.jia.jnmap.nmap.entity.NmapScanResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface NmapScanResultMapper {

    int insertSelective(@Param("nmapScanResult") NmapScanResult nmapScanResult);

    List<NmapScanResult> findByScanPlanId(@Param("scanPlanId") String scanPlanId);

    int updateById(@Param("updated") NmapScanResult updated, @Param("id") String id);

    List<NmapScanResult> findByScanId(@Param("scanId") String scanId);

    NmapScanResult findOneById(@Param("id") String id);

    NmapScanResult findOneByIpv4AddressAndTemplateId(@Param("ipv4Address") String ipv4Address, @Param("templateId") String templateId);

}
