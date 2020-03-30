package com.jia.jnmap.nmap;

import com.gaff.staff.cpe.WFN;
import com.jia.jnmap.entity.NmapScanResult;
import com.jia.jnmap.mapper.NmapScanResultMapper;
import com.jia.jnmap.nmap.entity.PortInfo;
import com.jia.jnmap.nmap.entity.SystemInfo;
import com.jia.jnmap.nmap.vuln.VulnerabilityLoader;
import com.jia.jnmap.utils.UUIDUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.xml.sax.helpers.DefaultHandler;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
public class NmapScanner extends DefaultHandler {

    private static final Logger logger = LoggerFactory.getLogger(NmapScanner.class);


    @Resource
    private NmapScanResultMapper mapper;
    @Resource
    private VulnerabilityLoader loader;


//    @Autowired
//    private LstBean lstBean;

//    public void doResult(String scanPlanId, String scanId, String templateId, List<String> nmapResults, boolean isMatchVuln) throws DocumentException {
    public void doResult(String scanId, List<String> nmapResults) throws DocumentException {
        List<NmapScanResult> nmapScanResults = new ArrayList<>();
        for (String nmapResult : nmapResults) {
            try {
//                List<NmapScanResult> list = parseScanResult(scanPlanId, scanId, templateId, nmapResult);
                List<NmapScanResult> list = parseScanResult(scanId, nmapResult);
                if (list != null && list.size() > 0) {
                    nmapScanResults.addAll(list);
                }
            } catch (Exception e) {
                logger.error(MessageFormat.format("parseScanResult error, nmapResult = {0}", nmapResult), e);
            }
        }

        // 执行漏洞匹配
        logger.info("开始分析漏洞，scanId：{}", scanId);
        loader.matchVulnerability(nmapScanResults);
        logger.info("漏洞分析完成，scanId：{}", scanId);

        // 保存解析出的扫描结果和匹配的漏洞
        int start = 0;
        while (start < nmapScanResults.size()) {
            List<NmapScanResult> subList = nmapScanResults.subList(start, Math.min(start + 1000, nmapScanResults.size()));
            mapper.insertList(subList);
            start += 1000;
        }
//        for (NmapScanResult nmapScanResult : nmapScanResults) {
//            mapper.insertSelective(nmapScanResult);
//        }
    }

    // 解析nmap命令执行的xml输出
//    private List<NmapScanResult> parseScanResult(String scanPlanId, String scanId, String templateId, String nmapResult) throws DocumentException {
    private List<NmapScanResult> parseScanResult(String scanId, String nmapResult) throws DocumentException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(nmapResult.getBytes());
        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element rootElement = document.getRootElement();
        List<Element> hostElements = rootElement.elements("host");
        List<NmapScanResult> result = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String scanTime = dateFormat.format(Calendar.getInstance().getTime());
        hostElements.parallelStream().forEach(hostElement -> {
            NmapScanResult nmapScanResult = new NmapScanResult();
            nmapScanResult.setId(UUIDUtil.uuid());
//            nmapScanResult.setScanPlanId(scanPlanId);
            nmapScanResult.setScanTime(scanTime);
            nmapScanResult.setScanId(scanId);
//            nmapScanResult.setTemplateId(templateId);
            List<Element> addressElements = hostElement.elements("address");
            for (Element addressElement : addressElements) {
                String type = addressElement.attributeValue("addrtype");
                String address = addressElement.attributeValue("addr");
                if ("mac".equals(type)) {
                    nmapScanResult.setMacAddress(address);
                } else if ("ipv4".equals(type)) {
                    nmapScanResult.setIpv4Address(address);
                } else if ("ipv6".equals(type)) {
                    nmapScanResult.setIpv6Address(address);
                }
            }
            List<Element> hostnameElements = hostElement.element("hostnames").elements("hostname");
            if (hostnameElements != null && hostnameElements.size() > 0) {
                nmapScanResult.setHostname(hostnameElements.get(0).attributeValue("name"));
            }

            // 端口扫描结果
            SystemInfo portSystemInfo = null;
            List<Element> portElements = hostElement.element("ports").elements("port");
            for (Element portElement : portElements) {
                Element stateElement = portElement.element("state");
                if (!"open".equals(stateElement.attributeValue("state"))) {
                    continue;
                }
                Element serviceElement = portElement.element("service");
                PortInfo portInfo = new PortInfo();
                portInfo.setPort(Integer.valueOf(portElement.attributeValue("portid")));
                portInfo.setProtocol(portElement.attributeValue("protocol"));
                if (serviceElement == null) {
                    portInfo.setName("");
                    portInfo.setProduct("");
                    portInfo.setVersion("");
                } else {
                    portInfo.setName(serviceElement.attributeValue("name"));
                    portInfo.setProduct(serviceElement.attributeValue("product"));
                    portInfo.setVersion(serviceElement.attributeValue("version"));
                    List<Element> cpeElements = serviceElement.elements("cpe");
                    if (cpeElements != null && cpeElements.size() > 0) {
                        Element bestCpeElement = null;
                        for (Element ele : cpeElements) {
                            String text = ele.getTextTrim().toLowerCase();
                            // cpe的完整格式为：cpe:/<part>:<vendor>:<product>:<version>:<update>:<edition>:<language>
                            // cpe:/a: = application应用程序
                            if (text.startsWith("cpe:/a:")) {
                                bestCpeElement = ele;
                            }
                            // cpe:/o: = os操作系统
                            if (text.startsWith("cpe:/o:microsoft:windows") && portSystemInfo == null) {
                                portSystemInfo = new SystemInfo();
                                portSystemInfo.setName("UNKNOW");
                                portSystemInfo.setVendor("UNKNOW");
                                portSystemInfo.setFamily("Windows");
                                portSystemInfo.setAccuracy(100);
                            }
                        }
                        if (bestCpeElement == null) {
                            bestCpeElement = cpeElements.get(0);
                        }
                        // cpe版本格式转换：22 -> 23
                        if (WFN.isV22(bestCpeElement.getTextTrim())) {
                            portInfo.setCpe(WFN.forURI22(bestCpeElement.getTextTrim()).toURI23());
                        }
                    }
                }

                List<Element> scriptElements = portElement.elements("script");
                if (scriptElements != null && scriptElements.size() > 0) {
                    for (Element scriptElement : scriptElements) {
                        String id = scriptElement.attributeValue("id");
                        String output = scriptElement.attributeValue("output");
                        if ("banner".equals(id)) {
                            portInfo.setBanner(output);
                            continue;
                        }
//                        if (lstBean.existsGaffsmbId(id)) {
//                            portInfo.addGaffsmb(id);
//                            continue;
//                        }
                        portInfo.addScriptInfo(id, output);
                    }
                }
                nmapScanResult.addPortInfo(portInfo);
            }

            // 操作系统匹配结果
            List<Element> osElements = hostElement.element("os").elements("osmatch");
            if (osElements == null || osElements.size() == 0) {
                if (portSystemInfo == null) {
                    nmapScanResult.setSystemInfo(SystemInfo.UNKNOW_SYSTEM);
                } else {
                    nmapScanResult.setSystemInfo(portSystemInfo);
                }
            } else {
                Element osElement = osElements.get(0);
                List<Element> osClassElements = osElement.elements("osclass");
                String name = osElement.attributeValue("name");
                Element bestOsClassElement = null;
                if (name.contains("or")) {
                    bestOsClassElement = osClassElements.get(0);
                } else {
                    for (Element osClassElement : osClassElements) {
                        Element cpeElement = osClassElement.element("cpe");
                        if (cpeElement != null && cpeElement.getTextTrim().toLowerCase().startsWith("cpe:/o:")) {
                            bestOsClassElement = osClassElement;
                            break;
                        }
                    }
                    if (bestOsClassElement == null) {
                        bestOsClassElement = osClassElements.get(0);
                    }
                }

                SystemInfo systemInfo = new SystemInfo();
                systemInfo.setName(osElement.attributeValue("name"));
                systemInfo.setVendor(bestOsClassElement.attributeValue("vendor"));
                systemInfo.setFamily(bestOsClassElement.attributeValue("osfamily"));
                systemInfo.setAccuracy(Integer.valueOf(bestOsClassElement.attributeValue("accuracy")));
                Element cpeElement = bestOsClassElement.element("cpe");
                if (cpeElement != null) {
                    if (WFN.isV22(cpeElement.getTextTrim())) {
                        systemInfo.setCpe(WFN.forURI22(cpeElement.getTextTrim()).toURI23());
                    }
                }
                nmapScanResult.setSystemInfo(systemInfo);

                Element hostscriptElement = hostElement.element("hostscript");
                if (hostscriptElement != null) {
                    List<Element> scriptElements = hostscriptElement.elements("script");
                    if (scriptElements != null && scriptElements.size() > 0) {
                        for (Element scriptElement : scriptElements) {
                            String id = scriptElement.attributeValue("id");
                            String output = scriptElement.attributeValue("output");
                            nmapScanResult.addScriptInfo(id, output);
                        }
                    }
                }
            }

            result.add(nmapScanResult);
        });
        return result;
    }

}
