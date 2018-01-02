/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a> All rights reserved.
 */
package com.tepusoft.modules.fileManage.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tepusoft.common.dto.JqueryResult;
import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.modules.fileManage.entity.SysFileRecord;
import com.tepusoft.modules.fileManage.service.SysFileRecordService;

/**
 * 文件操作记录Controller
 * @author liy
 * @version 2017-07-08
 */
@Controller
@RequestMapping(value = "${adminPath}/fileManage/sysFileRecord")
public class SysFileRecordController extends BaseController {

	@Autowired
	private SysFileRecordService sysFileRecordService;
	
	@ModelAttribute
	public SysFileRecord get(@RequestParam(required=false) String id) {
		SysFileRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysFileRecordService.get(id);
		}
		if (entity == null){
			entity = new SysFileRecord();
		}
		return entity;
	}
	
	@RequiresPermissions("agm:sysFileRecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysFileRecord sysFileRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysFileRecord> page = sysFileRecordService.findPage(new Page<SysFileRecord>(request, response), sysFileRecord); 
		model.addAttribute("page", page);
		return "modules/agm/sysFileRecordList";
	}

	@RequiresPermissions("agm:sysFileRecord:view")
	@RequestMapping(value = "dataList")
	@ResponseBody
	public Page<SysFileRecord> dataList(SysFileRecord sysFileRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysFileRecord> page = sysFileRecordService.findPage(new Page<SysFileRecord>(request, response), sysFileRecord);
		return page;
	}

	@RequiresPermissions("agm:sysFileRecord:view")
	@RequestMapping(value = "form")
	public String form(SysFileRecord sysFileRecord, Model model) {
		model.addAttribute("sysFileRecord", sysFileRecord);
		return "modules/agm/sysFileRecordForm";
	}

	@RequiresPermissions("agm:sysFileRecord:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public JqueryResult save(SysFileRecord sysFileRecord, Model model, RedirectAttributes redirectAttributes) {
		String message=beanValidatorStr(model, sysFileRecord);
        if (StringUtils.isNotBlank(message)){
            return new JqueryResult.Builder(FAIL_CODE).message(message).build();
        }
		sysFileRecordService.save(sysFileRecord);
		return new JqueryResult.Builder(SUCCESS_CODE).message("保存记录成功").build();
	}
	
	@RequiresPermissions("agm:sysFileRecord:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public JqueryResult delete(SysFileRecord sysFileRecord, RedirectAttributes redirectAttributes) {
		sysFileRecordService.delete(sysFileRecord);
		addMessage(redirectAttributes, "删除记录成功");
		return new JqueryResult.Builder(SUCCESS_CODE).message("删除记录成功").build();
	}

}