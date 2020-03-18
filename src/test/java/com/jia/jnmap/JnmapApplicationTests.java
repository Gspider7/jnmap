package com.jia.jnmap;

import com.jia.jnmap.nmap.exec.NmapOption;
import com.jia.jnmap.nmap.exec.NotifiableNmapEngine;
import com.jia.jnmap.nmap.NmapCommandUtil;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class JnmapApplicationTests {

    @Test
    void contextLoads() throws IOException {
        Resource resource = new ClassPathResource("");

        System.out.println(resource.getFile().getAbsolutePath());
    }


    @Test
    void testNmapCommand() throws IOException {
        List<NmapOption> optionList = new ArrayList<>();
        optionList.add(NmapOption.SCAN_LIST);
        optionList.add(NmapOption.OUTPUT_XML);

        CommandLine cmd = NmapCommandUtil.buildCommand(true, optionList, "www.baidu.com");

        NotifiableNmapEngine engine = new NotifiableNmapEngine();
        InputStream is = engine.execute(cmd);

        StringWriter writer = new StringWriter();
        IOUtils.copy(is, writer, "UTF-8");

        System.out.println(writer.toString());
    }

}
