package com.jia.jnmap.controller;

import com.jia.jnmap.domain.WebsocketMessageVO;
import com.jia.jnmap.entity.SystemLog;
import com.jia.jnmap.mapper.SystemLogMapper;
import com.jia.jnmap.nmap.vuln.VulnerabilityLoader;
import com.jia.jnmap.utils.ResponseUtil;
import com.jia.jnmap.utils.UUIDUtil;
import com.jia.jnmap.utils.ZipUtil;
import com.jia.jnmap.websocket.JnmapWebsocket;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
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

    // ------------------------------------------------------------------------------------

    @Resource
    private VulnerabilityLoader vulnerabilityLoader;

    @Resource
    private SystemLogMapper systemLogMapper;


    // ------------------------------------------------------------------------------------


//    @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
//    public String goToUploadPage(Model model) {
//        Cache cache = JnmapCache.getTestCache();
//
//        model.addAttribute("key", "value");
//        return "vuln/upload";
//    }

    /**
     * 上传漏洞库
     */
    @ResponseBody
    @PostMapping("/upload/req")
    public String uploadVulnStore(HttpServletRequest request, HttpSession session) throws Exception {
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

            // 解压缩
            ZipUtil.unzip(file, path);

            // 加载漏洞库
            vulnerabilityLoader.loadCnnvd();
            vulnerabilityLoader.loadCve();
            vulnerabilityLoader.cleanCache();

            // 记录系统日志
            String username = (String) session.getAttribute("username");
            SystemLog systemLog = new SystemLog(SystemLog.OP_VULN_OPLOAD, username, 1);
            systemLogMapper.insert(systemLog);

            // 删除临时文件
            file.delete();

            // 通知前端上传完成
            WebsocketMessageVO messageVO = new WebsocketMessageVO(WebsocketMessageVO.TYPE_VULN_UPLOAD);
            JnmapWebsocket.sendMessageToAll(messageVO);
        }

        return ResponseUtil.success();
    }
}
