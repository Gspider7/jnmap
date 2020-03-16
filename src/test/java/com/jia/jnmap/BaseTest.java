package com.jia.jnmap;

import com.jia.jnmap.exec.NotifiableNmapEngine;
import com.jia.jnmap.utils.MD5Util;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.io.IOUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

/**
 * @author xutao
 * @date 2020-03-15 12:21
 */
class BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    @Test
    public void testMD5() {
        System.out.println(MD5Util.MD5("ayufan"));
    }

    @Test
    public void testNmap() throws IOException, DocumentException {
        CommandLine cmd = new CommandLine("nmap");
        cmd.addArgument("-sV");
        cmd.addArgument("-O");
        cmd.addArgument("-oX");
        cmd.addArgument("-");
        cmd.addArgument("www.bilibili.com");

        NotifiableNmapEngine engine = new NotifiableNmapEngine();
        InputStream is = engine.execute(cmd);

        StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer, "UTF-8");
        String xmlStr = writer.toString();

        // 使用dom4j解析xml
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(is);

        Element root = doc.getRootElement();
        logger.info("root name: {}", root.getName());
        List<Attribute> rootAttrList = root.attributes();
        for (Attribute attr : rootAttrList) {
            logger.info("root attribute name: {}, value: {}", attr.getName(), attr.getValue());
        }

        List<Element> hostElements = root.elements("host");
        for (int i = 0; i<hostElements.size(); i++) {
            Element hostElement = hostElements.get(i);
            logger.info("host info：{}", i + 1);

            List<Attribute> hostAttrList = hostElement.attributes();
            for (Attribute attr : hostAttrList) {
                logger.info("host attribute name: {}, value: {}", attr.getName(), attr.getValue());
            }

            List<Element> addressElements = hostElement.elements("address");
            for (int j=0; j<addressElements.size(); j++) {
                Element addressElement = addressElements.get(j);
                logger.info("host address：{}", j + 1);

                List<Attribute> addressAttrList = addressElement.attributes();
                for (Attribute attr : addressAttrList) {
                    logger.info("host address attribute name: {}, value: {}", attr.getName(), attr.getValue());
                }
            }

            List<Element> portsElements = hostElement.elements("ports");
            for (int j=0; j<portsElements.size(); j++) {
                Element portsElement = portsElements.get(j);
                logger.info("host ports：{}", j + 1);

                List<Attribute> addressAttrList = portsElement.attributes();
                for (Attribute attr : addressAttrList) {
                    logger.info("host ports attribute name: {}, value: {}", attr.getName(), attr.getValue());
                }

                List<Element> portElements = portsElement.elements("port");
                for (int k=0; k<portElements.size(); k++) {
                    Element portElement = portElements.get(k);
                    logger.info("host port：{}", k + 1);

                    List<Attribute> portAttrList = portElement.attributes();
                    for (Attribute attr : portAttrList) {
                        logger.info("host port attribute name: {}, value: {}", attr.getName(), attr.getValue());
                    }

                    // state

                    // service
                }
            }

            // os
        }
    }
}