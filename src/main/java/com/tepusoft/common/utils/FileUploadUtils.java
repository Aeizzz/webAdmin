package com.tepusoft.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 2017/3/7.
 * <br/>
 * @author panbb
 * <br/>
 * 文件上传工具
 */
public class FileUploadUtils {
    private Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);
    // 文件保存目录相对路径
    private String basePath = "/uploadFile/";
    // 文件保存目录路径
    private String savePath;
    // 文件保存目录url
    private String saveUrl;
    /**
     * 文件上传
     * @param file 上传文件
     * @param request 上传请求
     * @param dire 文件保存目录
     * @return
     */
    public Map<String, String> upload(MultipartFile file, HttpServletRequest request, String dire ){
        Map<String, String> infos = new HashMap<String, String>();
        try {
            if(dire != null && !dire.equals("")){
                basePath += dire+"/";
            }
            savePath = request.getSession().getServletContext().getRealPath("/")+basePath;//文件上传路径
            String fileName = file.getOriginalFilename();
            String prefix=fileName.substring(fileName.lastIndexOf(".")+1);
            String saveName = DateUtils.getDate("yyyyMMddHHmmss")+"."+prefix;//文件保存名称
            java.io.File targetFile = new java.io.File(savePath, saveName);
            saveUrl = basePath+saveName;
            if(!targetFile.exists()){
                targetFile.mkdirs();
            }
            file.transferTo(targetFile);
            infos.put("status", "ok");
            infos.put("saveName", saveName);
            infos.put("savePath", savePath+saveName);
            infos.put("saveUrl", saveUrl);
            infos.put("prefix", prefix);
        }catch (Exception e){
            infos.put("status", "error");
            logger.error("[文件上传]：", e);
        }
        return infos;
    }

}
