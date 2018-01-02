/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a> All rights reserved.
 */
package com.tepusoft.modules.sysform.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tepusoft.common.config.Global;
import com.tepusoft.common.dto.JqueryResult;
import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.modules.sysform.entity.SysForm;
import com.tepusoft.modules.sysform.service.SysFormService;

/**
 * 表单Controller
 * @author ly
 * @version 2017-06-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sysForm")
public class SysFormController extends BaseController {

	@Autowired
	private SysFormService sysFormService;
	
	@ModelAttribute
	public SysForm get(@RequestParam(required=false) String id) {
		SysForm entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysFormService.get(id);
		}
		if (entity == null){
			entity = new SysForm();
		}
		return entity;
	}
	
	@RequiresPermissions("sysfrom:sysForm:view")
	@RequestMapping(value = {"list", ""})
	public String list(SysForm sysForm, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/sysForm/sysFormList";
	}

	@RequiresPermissions("sysfrom:sysForm:view")
	@RequestMapping(value = "dataList")
	@ResponseBody
	public Page<SysForm> dataList(SysForm sysForm, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysForm> page = sysFormService.findPage(new Page<SysForm>(request, response), sysForm);
		return page;
	}

	@RequiresPermissions("sysfrom:sysForm:view")
	@RequestMapping(value = "form")
	public String form(SysForm sysForm, Model model) {
		model.addAttribute("sysForm", sysForm);
		return "modules/sysForm/sysFormForm";
	}

	@RequiresPermissions("sysfrom:sysForm:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public JqueryResult save(SysForm sysForm, Model model, RedirectAttributes redirectAttributes) {
		String message=beanValidatorStr(model, sysForm);
        if (StringUtils.isNotBlank(message)){
            return new JqueryResult.Builder(FAIL_CODE).message(message).build();
        }
        if(StringUtils.isNotBlank(sysForm.getFtlContent())){
        	sysForm.setFtlContent(StringEscapeUtils.unescapeHtml4(sysForm.getFtlContent()));
		}
		sysFormService.save(sysForm);
		return new JqueryResult.Builder(SUCCESS_CODE).message("保存模板成功").build();
	}

	/***
	 * 模版预览
	 * @param sysForm
	 * @return
	 */
	@RequestMapping(value="viewTpl")
	public String viewTpl(SysForm sysForm,Model model){
		model.addAttribute("sysForm",sysForm);
		return "modules/sysForm/viewTpl";
	}

	
	@RequiresPermissions("sysfrom:sysForm:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public JqueryResult delete(SysForm sysForm, RedirectAttributes redirectAttributes) {
		sysFormService.delete(sysForm);
		return new JqueryResult.Builder(SUCCESS_CODE).message("删除模板成功").build();
	}
	@RequiresPermissions("sysfrom:sysForm:edit")
	@RequestMapping(value = "update")
	@ResponseBody
	public JqueryResult update(SysForm sysForm,Model model, RedirectAttributes redirectAttributes) {
		String message=beanValidatorStr(model,sysForm);
			if("1".equals(sysForm.getIsActivate())){
			sysForm.setIsActivate("0");
			message="取消激活成功";
		}else{
			sysForm.setIsActivate("1");
				message="激活模板成功";
		}
		sysFormService.save(sysForm);
		return new JqueryResult.Builder(SUCCESS_CODE).message(message).build();
	}

}