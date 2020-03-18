package com.jia.jnmap.controller;

import com.jia.jnmap.context.JnmapCache;
import com.jia.jnmap.utils.UUIDUtil;
import com.jia.jnmap.utils.WebUtil;
import com.jia.jnmap.utils.ZipUtil;
import org.apache.commons.io.IOUtils;
import org.infinispan.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 漏洞
 *
 * @author xutao
 * @date 2020-03-17 21:21
 */
@Controller
@RequestMapping("/vuln")
public class VulnController {

    private static final Logger logger = LoggerFactory.getLogger(VulnController.class);

    @RequestMapping(value = "/test", method = {RequestMethod.GET, RequestMethod.POST})
    public String testUpload(Model model) {
        Cache cache = JnmapCache.getTestCache();

        model.addAttribute("key", "value");
        return "upload";
    }

    /**
     * 上传漏洞库
     */
    @ResponseBody
    @PostMapping("/upload")
    public String uploadVulnStore(HttpServletRequest request) throws IOException {
        // 上传多个文件
        List<MultipartFile> mpfs = ((MultipartHttpServletRequest) request).getFiles("file");

        String projectHome = System.getProperty("user.dir");    // springboot项目根目录
        String path = projectHome + "/data";
        new File(path).mkdirs();

        for (MultipartFile mpf : mpfs) {
            // 保存到本地
//            String fileName = MessageFormat.format("upload-{0}", UUIDUtil.uuid());
            String fileName = UUIDUtil.uuid() + ".zip";
            File file = new File(path, fileName);

            OutputStream os = new FileOutputStream(file);
            IOUtils.copy(mpf.getInputStream(), os);
            os.close();

            // 解压缩，加载漏洞库
            ZipUtil.unzip(file, path);
//            getVulnerabilityLoader().loadCnnvd();
//            getVulnerabilityLoader().loadCve();
//            getVulnerabilityLoader().cleanCache();

            // 删除临时文件
            file.delete();
        }

        return WebUtil.success();
    }
}
