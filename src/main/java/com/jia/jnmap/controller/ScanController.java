package com.jia.jnmap.controller;

import com.jia.jnmap.domain.*;
import com.jia.jnmap.entity.NmapScanResult;
import com.jia.jnmap.entity.Scan;
import com.jia.jnmap.entity.SystemLog;
import com.jia.jnmap.entity.VulnBaseInfo;
import com.jia.jnmap.mapper.NmapScanResultMapper;
import com.jia.jnmap.mapper.ScanMapper;
import com.jia.jnmap.mapper.SystemLogMapper;
import com.jia.jnmap.mapper.VulnerabilityMapper;
import com.jia.jnmap.nmap.NmapCommandUtil;
import com.jia.jnmap.nmap.NmapScanner;
import com.jia.jnmap.nmap.entity.PortInfo;
import com.jia.jnmap.nmap.entity.SystemInfo;
import com.jia.jnmap.nmap.exec.NmapOption;
import com.jia.jnmap.nmap.exec.NotifiableNmapEngine;
import com.jia.jnmap.utils.ResponseUtil;
import com.jia.jnmap.websocket.JnmapWebsocket;
import inet.ipaddr.IPAddressString;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.*;

/**
 * 扫描管理接口
 *
 * @version 1.0.0
 * @date 2020-03-30 10:49
 */
@Controller
@RequestMapping("/scan")
public class ScanController {

    private static final Logger logger = LoggerFactory.getLogger(ScanController.class);

    @Autowired
    private NotifiableNmapEngine notifiableNmapEngine;
    @Autowired
    private NmapScanner nmapScanner;

    @Resource
    private SystemLogMapper systemLogMapper;
    @Resource
    private ScanMapper scanMapper;
    @Resource
    private NmapScanResultMapper nmapScanResultMapper;
    @Resource
    private VulnerabilityMapper vulnerabilityMapper;


    /**
     * 分页查询扫描配置
     *
     * @param page      页码
     * @param pageSize  每页多少记录
     */
    @RequestMapping("/list")
    public String listScan(Model model, HttpSession session,
                           @RequestParam(name = "page", required = false) Integer page,
                           @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;

        // 查询漏洞库更新时间
        String username = (String) session.getAttribute("username");
        SystemLog log = systemLogMapper.selectLast(SystemLog.OP_VULN_OPLOAD, username, 1);
        model.addAttribute("vulnUpdateTime", log == null ? null : log.getOperationTime());

        // 分页查询扫描配置
        List<Scan> scanList = scanMapper.selectPage(pageSize, (page - 1) * pageSize);
        model.addAttribute("scanList", scanList);

        return "scan/list";
    }

    /**
     * 添加扫描配置
     *
     * @param name      扫描名称
     * @param target    目标网段
     */
    @ResponseBody
    @RequestMapping("/add")
    public String addScan(@RequestParam("name") String name,
                          @RequestParam("target") String target) {
        // 后台参数校验
        if (StringUtils.isBlank(name)) return ResponseUtil.lack_param("扫描名称");
        if (StringUtils.isBlank(target)) return ResponseUtil.lack_param("目标网段");

        // 检查目标网段
        IPAddressString ipString = new IPAddressString(target);
        if (!ipString.isIPv4()) return ResponseUtil.invalid_nmap_target();

        Scan scan = new Scan(name, target);
        scanMapper.insert(scan);

        return ResponseUtil.success();
    }

    /**
     * 删除扫描配置
     *
     * @param id    扫描id
     */
    @ResponseBody
    @RequestMapping("/delete")
    public String deleteScan(@RequestParam("id") String id) {
        scanMapper.delete(id);
        nmapScanResultMapper.deleteByScanId(id);

        return ResponseUtil.success();
    }

    /**
     * 执行扫描
     *
     * @param id    扫描id
     */
    @ResponseBody
    @RequestMapping("/execute")
    public String executeScan(@RequestParam("id") String id) {
        // 后台参数校验
        if (StringUtils.isBlank(id)) return ResponseUtil.lack_param("扫描id");

        Scan scan = scanMapper.selectById(id);
        if (scan == null) return ResponseUtil.miss_scan_config();

        // 新建线程，执行nmap扫描
        new Thread(() -> {
            // 组装执行参数
            List<NmapOption> optionList = new ArrayList<>();
            optionList.add(NmapOption.SERVICE_VERSION);         // 端口服务发现
            optionList.add(NmapOption.OS_DETECTION);            // 操作系统发现
            optionList.add(NmapOption.OUTPUT_XML);              // 指定输出格式为xml
//            CommandLine cmd = NmapCommandUtil.buildCommand(true, optionList, scan.getTarget());     // 适合linux的命令
            CommandLine cmd = NmapCommandUtil.buildCommand(false, optionList, scan.getTarget());     // 适合windows的命令

            try {
                // 执行命令
                InputStream is = notifiableNmapEngine.execute(cmd);
                StringWriter writer = new StringWriter();
                IOUtils.copy(is, writer, "UTF-8");

                // 解析输出
                nmapScanner.doResult(id, Collections.singletonList(writer.toString()));

                // 通知前端扫描成功
                JnmapWebsocket.sendMessageToAll(new ScanStatusVO(scan, "success"));
            } catch (Exception e) {
                // 通知前端扫描失败
                JnmapWebsocket.sendMessageToAll(new ScanStatusVO(scan, "fail"));
                throw new RuntimeException("执行扫描失败", e);
            }
        }).start();

        return ResponseUtil.success();
    }

    /**
     * 分页查询扫描结果
     *
     * @param page      页码
     * @param pageSize  每页多少记录
     */
    @RequestMapping("/result/list")
    public String listScanResult(Model model,
                                 @RequestParam(name = "scanId", required = false) String scanId,
                                 @RequestParam(name = "page", required = false) Integer page,
                                 @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;

        // 分页查询
        if (StringUtils.isBlank(scanId)) scanId = null;
        List<NmapScanResult> resultList = nmapScanResultMapper.selectPage(scanId, pageSize, (page - 1) * pageSize);

        model.addAttribute("resultList", resultList);
        return "scan/resultList";
    }

    /**
     * 查询主机报告
     */
    @RequestMapping("/host-report")
    public String scanResult(Model model,
                             @RequestParam(name = "scanResultId") String scanResultId) {
        // 查询主机扫描结果
        NmapScanResult nmapScanResult = nmapScanResultMapper.selectById(scanResultId);

        // 封装数据返回
        NmapScanResultVo nmapScanResultVo = new NmapScanResultVo(nmapScanResult);
        List<PortInfo> portInfos = nmapScanResult.getPortInfos();
        List<VulnBaseInfo> vulnerabilityList = new ArrayList<>();
        List<PortInfoVo> portInfoVos = new ArrayList<>();
        for (PortInfo portInfo : portInfos) {
            PortInfoVo portInfoVo = new PortInfoVo(portInfo);
            List<String> ids = portInfo.getVulnerability();
            if (ids != null && ids.size() > 0) {
                List<VulnBaseInfo> vulnerabilities = vulnerabilityMapper.selectAllVulnBaseInfoByIds(ids);
                vulnerabilities.sort((v1, v2) -> v1.compare(v2));
                portInfoVo.setVulnerabilities(vulnerabilities);

                for (VulnBaseInfo vulnerability : vulnerabilities) {
                    if (!vulnerabilityList.contains(vulnerability)) {
                        vulnerabilityList.add(vulnerability);
                    }
                }
            }
            portInfoVos.add(portInfoVo);
        }
        vulnerabilityList.sort((v1, v2) -> v1.compare(v2));
        nmapScanResultVo.setVulnerabilities(vulnerabilityList);

        model.addAttribute("portInfoVos", portInfoVos);
        model.addAttribute("nmapScanResultVo", nmapScanResultVo);
        return "scan/report/host";
    }

    /**
     * 查看漏洞报告
     */
    @RequestMapping("/vuln-report")
    public String viewVulnReport(Model model,
                                 @RequestParam("scanId") String scanId) {
        // 查询扫描配置
        Scan scan = scanMapper.selectById(scanId);

        // 查询扫描结果列表
        List<NmapScanResult> nmapScanResults = nmapScanResultMapper.findByScanId(scanId);

        // 组装VO
        CounterResult counter = new CounterResult();
        counter.setTotalHost(nmapScanResults.size());

        List<NmapScanResultVo> nmapScanResultVos = new ArrayList<>();
        List<NmapScanResultVo> kylinScanResultVos = new ArrayList<>();
        for (NmapScanResult nmapScanResult : nmapScanResults) {
            NmapScanResultVo nmapScanResultVo = new NmapScanResultVo(nmapScanResult);
            List<PortInfo> portInfos = nmapScanResult.getPortInfos();
            Set<String> vulnIds = new TreeSet<>();
            for (PortInfo portInfo : portInfos) {
                List<String> ids = portInfo.getVulnerability();
                if (ids == null || ids.size() == 0) {
                    continue;
                }
                vulnIds.addAll(ids);
            }
            List<VulnBaseInfo> vulnerabilities = null;
            if (vulnIds.size() == 0) {
                vulnerabilities = new ArrayList<>();
            } else {
                vulnerabilities = vulnerabilityMapper.selectAllVulnBaseInfoByIds(new ArrayList(vulnIds));
            }
            for (VulnBaseInfo vulnBaseInfo : vulnerabilities) {
                String severity = vulnBaseInfo.getSeverity();
                if ("超危".equals(severity)) {
                    counter.setTotalVulnerabilityCritical(counter.getTotalVulnerabilityCritical() + 1);
                    nmapScanResultVo.setVulnerabilityCriticalCount(nmapScanResultVo.getVulnerabilityCriticalCount() + 1);
                } else if ("高危".equals(severity)) {
                    counter.setTotalVulnerabilityHigh(counter.getTotalVulnerabilityHigh() + 1);
                    nmapScanResultVo.setVulnerabilityHighCount(nmapScanResultVo.getVulnerabilityHighCount() + 1);
                } else if ("中危".equals(severity)) {
                    counter.setTotalVulnerabilityMedium(counter.getTotalVulnerabilityMedium() + 1);
                    nmapScanResultVo.setVulnerabilityMediumCount(nmapScanResultVo.getVulnerabilityMediumCount() + 1);
                } else if ("低危".equals(severity)) {
                    counter.setTotalVulnerabilityLow(counter.getTotalVulnerabilityLow() + 1);
                    nmapScanResultVo.setVulnerabilityLowCount(nmapScanResultVo.getVulnerabilityLowCount() + 1);
                } else {
                    counter.setTotalVulnerabilityUnknow(counter.getTotalVulnerabilityUnknow() + 1);
                    nmapScanResultVo.setVulnerabilityUnknowCount(nmapScanResultVo.getVulnerabilityUnknowCount() + 1);
                }
            }
            counter.setTotalVulnerability(counter.getTotalVulnerability() + vulnIds.size());
            vulnerabilities.sort((v1, v2) -> v1.compare(v2));
            nmapScanResultVo.setVulnerabilities(vulnerabilities);
            SystemInfo systemInfo = nmapScanResult.getSystemInfo();
            String family = systemInfo.getFamily();
            if ("linux".equalsIgnoreCase(family)) {
                counter.setTotalFamilyLinux(counter.getTotalFamilyLinux() + 1);
            } else if ("android".equalsIgnoreCase(family)) {
                counter.setTotalFamilyAndroid(counter.getTotalFamilyAndroid() + 1);
            } else if ("windows".equalsIgnoreCase(family)) {
                counter.setTotalFamilyWindows(counter.getTotalFamilyWindows() + 1);
            } else if ("unix".equalsIgnoreCase(family)) {
                counter.setTotalFamilyUnix(counter.getTotalFamilyUnix() + 1);
            } else if ("kylin".equalsIgnoreCase(family)) {
                counter.setTotalFamilyKylin(counter.getTotalFamilyKylin() + 1);
                kylinScanResultVos.add(nmapScanResultVo);
            } else {
                counter.setTotalFamilyOther(counter.getTotalFamilyOther() + 1);
            }
            counter.setTotalPort(counter.getTotalPort() + portInfos.size());
            nmapScanResultVos.add(nmapScanResultVo);
        }

        model.addAttribute("scan", scan);
        model.addAttribute("counter", counter);
        model.addAttribute("nmapScanResultVos", nmapScanResultVos);
        model.addAttribute("vulnerabilityVos", getVulnerabilityVoFromNmapScanResultVos(nmapScanResultVos));
        model.addAttribute("kylinScanResultVos", kylinScanResultVos);

        return "scan/report/vuln";
    }

    // ------------------------------------------------------------------------------------

    private List<VulnerabilityVo> getVulnerabilityVoFromNmapScanResultVos(List<NmapScanResultVo> nmapScanResultVos) {
        Map<String, VulnerabilityVo> map = new HashMap<>();
        for (NmapScanResultVo nmapScanResultVo : nmapScanResultVos) {
            for (VulnBaseInfo vulnerability : nmapScanResultVo.getVulnerabilities()) {
                if (!map.containsKey(vulnerability.getId())) {
                    VulnerabilityVo vo = new VulnerabilityVo(vulnerability);
                    vo.addNmapScanResultVo(nmapScanResultVo);
                    map.put(vo.getVulnerability().getId(), vo);
                } else {
                    VulnerabilityVo vo = map.get(vulnerability.getId());
                    if (!vo.getNmapScanResultVos().contains(nmapScanResultVo)) {
                        vo.addNmapScanResultVo(nmapScanResultVo);
                    }
                }
            }
        }

        List<VulnerabilityVo> vulnerabilityVos = new ArrayList<>(map.values());
        vulnerabilityVos.sort((v1, v2) -> {
            return v1.compare(v2);
        });
        return vulnerabilityVos;
    }
}
