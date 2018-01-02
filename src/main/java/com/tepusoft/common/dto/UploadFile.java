package com.tepusoft.common.dto;

import com.tepusoft.common.config.Global;
import com.tepusoft.common.utils.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by 10320 on 2017-6-19.
 */
public class UploadFile {
    private Log logger = LogFactory.getLog(UploadFile.class);
    /**
     * 是否可以上传
     */
    private boolean isValid = true;
    /**
     * 提示信息
     */
    private String msg = "";
    /**
     * 文件大小
     */
    private Long fileSize;
    /**
     * 网络路径
     */
    private String httpPath = "";
    /***
     *绝对路径(本地路径)
     */
    private String absolutePath = "";
    /**
     * 相对路径
     */
    private String relativePath = "";
    /***
     * 文件名称
     */
    private String fileName;
    /**
     * 文件后缀
     */
    private String fileType;

    /**
     * @param file    文件名称
     * @param request
     */
    public UploadFile(MultipartFile file, HttpServletRequest request, String directory) {
        this.fileName = file.getOriginalFilename();
        this.fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        this.fileSize = file.getSize();
        this.relativePath = directory + File.separator;

        this.relativePath += this.getFileName();//文件保存名称
        try {
            File targetFile = new File(Global.getFileBasePath() + relativePath);
            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }
            file.transferTo(targetFile);
            this.absolutePath = Global.getFileBasePath() + relativePath;
            this.httpPath = Global.getHttpFileBasePath() + relativePath;
        } catch (IOException e) {
            this.isValid = false;
            logger.error(e.toString());
        }
    }

    public UploadFile(MultipartFile file, HttpServletRequest request) {
        this(file, request, "");
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getHttpPath() {
        return httpPath;
    }

    public void setHttpPath(String httpPath) {
        this.httpPath = httpPath;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
