package com.jia.jnmap.controller;

import com.jia.jnmap.domain.ScanStatusVO;
import com.jia.jnmap.entity.NmapScanResult;
import com.jia.jnmap.entity.Scan;
import com.jia.jnmap.mapper.NmapScanResultMapper;
import com.jia.jnmap.mapper.ScanMapper;
import com.jia.jnmap.nmap.NmapCommandUtil;
import com.jia.jnmap.nmap.NmapScanner;
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
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private ScanMapper scanMapper;
    @Resource
    private NmapScanResultMapper nmapScanResultMapper;


    /**
     * 分页查询扫描配置
     *
     * @param page      页码
     * @param pageSize  每页多少记录
     */
    @RequestMapping("/list")
    public String listScan(Model model,
                           @RequestParam("page") Integer page,
                           @RequestParam("pageSize") Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;

        // 分页查询
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
                JnmapWebsocket.sendMessageToAll(new ScanStatusVO(id, "success"));
            } catch (Exception e) {
                // 通知前端扫描失败
                JnmapWebsocket.sendMessageToAll(new ScanStatusVO(id, "fail"));
                throw new RuntimeException("执行扫描失败", e);
            }
        }).start();

        return ResponseUtil.success();
    }

    /**
     * 分页查询扫描配置
     *
     * @param page      页码
     * @param pageSize  每页多少记录
     */
    @RequestMapping("/result/list")
    public String listScanResult(Model model,
                                 @RequestParam("scanId") String scanId,
                                 @RequestParam("page") Integer page,
                                 @RequestParam("pageSize") Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 20 : pageSize;

        // 分页查询
        if (StringUtils.isBlank(scanId)) scanId = null;
        List<NmapScanResult> resultList = nmapScanResultMapper.selectPage(scanId, pageSize, (page - 1) * pageSize);

        model.addAttribute("resultList", resultList);
        return "scan/resultList";
    }
}
