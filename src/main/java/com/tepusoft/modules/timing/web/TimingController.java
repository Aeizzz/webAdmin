/**
 * Copyright &copy; 2012-2016 <a href="https://tepusoft.com">tepusoft</a> All rights reserved.
 */
package com.tepusoft.modules.timing.web;

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

import com.tepusoft.common.config.Global;
import com.tepusoft.common.dto.JqueryResult;
import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.modules.timing.entity.Timing;
import com.tepusoft.modules.timing.service.TimingService;

/**
 * 增删改查Controller
 * @author liy
 * @version 2017-07-01
 */
@Controller
@RequestMapping(value = "${adminPath}/timing/timing")
public class TimingController extends BaseController {

	@Autowired
	private TimingService timingService;
	
	@ModelAttribute
	public Timing get(@RequestParam(required=false) String id) {
		Timing entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = timingService.get(id);
		}
		if (entity == null){
			entity = new Timing();
		}
		return entity;
	}
	
	@RequiresPermissions("timing:timing:view")
	@RequestMapping(value = {"list", ""})
	public String list(Timing timing, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Timing> page = timingService.findPage(new Page<Timing>(request, response), timing); 
		model.addAttribute("page", page);
		return "modules/timing/timingList";
	}

	@RequiresPermissions("timing:timing:view")
	@RequestMapping(value = "dataList")
	@ResponseBody
	public Page<Timing> dataList(Timing timing, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Timing> page = timingService.findPage(new Page<Timing>(request, response), timing);
		return page;
	}

	@RequiresPermissions("timing:timing:view")
	@RequestMapping(value = "form")
	public String form(Timing timing, Model model) {
		model.addAttribute("timing", timing);
		return "modules/timing/timingForm";
	}

	@RequiresPermissions("timing:timing:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public JqueryResult save(Timing timing, Model model, RedirectAttributes redirectAttributes) {
		String message=beanValidatorStr(model, timing);
        if (StringUtils.isNotBlank(message)){
            return new JqueryResult.Builder(FAIL_CODE).message(message).build();
        }
		timingService.save(timing);
		addMessage(redirectAttributes, "保存任务成功");
		return new JqueryResult.Builder(SUCCESS_CODE).message("保存任务成功").build();
	}
	@RequiresPermissions("timing:timing:edit")
	@RequestMapping(value = "isStop")
	@ResponseBody
	public JqueryResult isStop(Timing timing, Model model, RedirectAttributes redirectAttributes) {
		String message=beanValidatorStr(model,timing);
		if("1".equals(timing.getStatus())){
			timing.setStatus("0");
			message="停止任务成功";
		}else{
			timing.setStatus("1");
			message="启动任务成功";
		}
		timingService.save(timing);
		return new JqueryResult.Builder(SUCCESS_CODE).message(message).build();
	}
	@RequiresPermissions("timing:timing:edit")
	@RequestMapping(value = "isEffect")
	@ResponseBody
	public JqueryResult isEffect(Timing timing, Model model, RedirectAttributes redirectAttributes) {
		String message=beanValidatorStr(model,timing);
		if("1".equals(timing.getIsEffect())){
			timing.setIsEffect("0");
			message="任务失效成功";
		}else{
			timing.setIsEffect("1");
			message="任务生效成功";
		}
		timingService.save(timing);
		return new JqueryResult.Builder(SUCCESS_CODE).message(message).build();
	}
	@RequiresPermissions("timing:timing:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public JqueryResult delete(Timing timing, RedirectAttributes redirectAttributes) {
		timingService.delete(timing);
		addMessage(redirectAttributes, "删除任务成功");
		return new JqueryResult.Builder(SUCCESS_CODE).message("删除任务成功").build();
	}

}