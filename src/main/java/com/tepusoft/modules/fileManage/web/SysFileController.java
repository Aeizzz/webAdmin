/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a> All rights reserved.
 */
package com.tepusoft.modules.fileManage.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tepusoft.common.dto.UploadFile;
import com.tepusoft.modules.fileManage.service.SysFileRecordService;
import com.tepusoft.modules.fileManage.service.SysFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.tepusoft.common.dto.JqueryResult;
import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.modules.fileManage.service.SysFileService;
import com.tepusoft.modules.fileManage.entity.SysFile;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 文件Controller
 * @author liy
 * @version 2017-07-09
 */
@Controller
@RequestMapping(value = "${adminPath}/fileManage/sysFile")
public class SysFileController extends BaseController {

	@Autowired
	private SysFileService sysFileService;
	@Autowired
	private SysFolderService sysFolderService;
	@Autowired
	private SysFileRecordService sysFileRecordService;
	
	@ModelAttribute
	public SysFile get(@RequestParam(required=false) String id) {
		SysFile entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysFileService.get(id);
		}
		if (entity == null){
			entity = new SysFile();
		}
		return entity;
	}
	@ResponseBody
	@RequestMapping(value = "save")
	public JqueryResult save(MultipartFile file, SysFile sysFile, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
//		String message=beanValidatorStr(model, sysFile);
		String message="";
        if (StringUtils.isNotBlank(message)){
            return new JqueryResult.Builder(FAIL_CODE).message(message).build();
        }
        if(StringUtils.isBlank(sysFile.getParentId())){
        	sysFile.setParentId("0");
		}
//		ParentIds 为文件夹的ParentIds，parentId为文件夹的Id
		sysFile.setParentIds(sysFile.getParentIds()+sysFile.getParentId()+",");
		UploadFile result = new UploadFile(file, request, sysFile.getParentIds());
	/*因为文件编辑的时候是从页面返回到sysfolderController，
	文件更新时直接调用service的save方法，
	只有新增是调用此方法，可以直接设置文件属性值
	*/
	String fileName=result.getFileName();
	fileName=fileName.substring(0,fileName.lastIndexOf("."));
//	System.out.println("filename:"+fileName);
		sysFile.setFilePath(result.getAbsolutePath());
		sysFile.setName(fileName);
//		sysFile.setFileSize(result.getFileSize());
		sysFile.setType("1");
		sysFile.setFileType(result.getFileType());
		sysFile.setClickNum(0);
		sysFile.setDownloadNum(0);
		sysFileService.save(sysFile);
		//更新文件夹大小
		sysFolderService.updateSize(sysFile.getParentIds(),sysFile.getFileSize(),true);
		addMessage(redirectAttributes, "保存文件成功");
		return new JqueryResult.Builder(SUCCESS_CODE).message("保存文件成功").build();
	}

	/**
	 * 文件下载
	 * @return
	 */
	@RequestMapping(value = "download")
	public void download(SysFile sysFile,HttpServletRequest request, HttpServletResponse response){
		FileInputStream in=null;
		OutputStream out = null;
		try {
//			String t = request.getSession().getServletContext().getRealPath("/");
			String t= URLDecoder.decode(sysFile.getName(), "utf-8");
//			请本地文件夹
//			File file=new File(t+"templateFile");
			//设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(t, "UTF-8")+"."+sysFile.getFileType());
			in = new FileInputStream(new File(sysFile.getFilePath()));
			out = response.getOutputStream();
			//创建缓冲区
			byte buffer[] = new byte[1024];
			int len = 0;
			while((len=in.read(buffer))>0){
				out.write(buffer, 0, len);
			}
			//下载量+1
			sysFile.setDownloadNum(sysFile.getDownloadNum()+1);
			sysFileService.save(sysFile);
			//新增下载记录type 1 文件  operation 1 下载
			sysFileRecordService.addRecord(sysFile.getId(),"1","1");
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("用户取消下载");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try{
				if(in!=null){
					in.close();
				}
				if(out!=null){
					out.close();
				}
			}catch (Exception e){
			}
		}
	}

//	@RequestMapping(value = "delete")
//	@ResponseBody
//	public JqueryResult delete(SysFile sysFile, RedirectAttributes redirectAttributes) {
//		sysFileService.delete(sysFile);
//
//		addMessage(redirectAttributes, "删除文件成功");
//		return new JqueryResult.Builder(SUCCESS_CODE).message("删除文件成功").build();
//	}

}