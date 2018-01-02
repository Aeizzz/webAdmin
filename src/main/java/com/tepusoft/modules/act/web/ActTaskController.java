/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tepusoft.modules.act.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.modules.act.entity.Act;
import com.tepusoft.modules.act.service.ActTaskService;
import com.tepusoft.modules.act.utils.ActUtils;
import com.tepusoft.modules.sys.utils.UserUtils;


/**
 * 流程个人任务相关Controller
 * @author ThinkGem
 * @version 2013-11-03
 */
@Controller
@RequestMapping(value = "${adminPath}/act/task")
public class ActTaskController extends BaseController {

	@Autowired
	private ActTaskService actTaskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;

//	/**
//	 * 进入待办页面
//	 * @param baseInfo
//	 * @param response
//	 * @param model
//	 * @return
//	 * @throws Exception
//	 */
//	@RequestMapping(value = "toTaskList")
//	public String toTaskList(BaseInfo baseInfo, HttpServletResponse response, Model model) throws Exception {
//		List<BaseInfo> list= actTaskService.getTaskList(null,baseInfo);
//		model.addAttribute("count",list.size());
//		return "modules/act/taskTodoList";
//	}
//
//	/**
//	 * 获取待办数据
//	 * @param baseInfo
//	 * @param model
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("getTaskList")
//	@ResponseBody
//	public Page<BaseInfo> getTaskList(BaseInfo baseInfo,Model model,HttpServletRequest request,HttpServletResponse response) {
//		Page<BaseInfo> page=new Page<BaseInfo>(request, response);
//		baseInfo.setPage2(page);
//		List<BaseInfo> list= actTaskService.getTaskList(page,baseInfo);
//		page.setList(list);
//		model.addAttribute("page", page);
//		return page;
//	}
	/*
	*//**
	 * 获取待办列表
	 * @param act
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 *//*
	@RequestMapping(value = {"todo", ""})
	public String todoList(Act act, HttpServletResponse response, Model model) throws Exception {
		List<Act> list = actTaskService.todoList(act);
		model.addAttribute("list", list);
		if (UserUtils.getPrincipal().isMobileLogin()){
			return renderString(response, list);
		}
		return "modules/act/actTaskTodoList";
	}*/

	/**
	 * 获取已办任务
	 * @param act
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
//	@RequestMapping(value = "historic")
//	public String historicList(Act act, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
//		Page<Act> page = new Page<Act>(request, response);
//		page = actTaskService.historicList(page, act);
//		model.addAttribute("page", page);
//		if (UserUtils.getPrincipal().isMobileLogin()){
//			return renderString(response, page);
//		}
//		return "modules/act/actTaskHistoricList";
//	}

	/**
	 * 获取流转历史列表
	 * @param act 流程实例
	 * @param startAct 开始活动节点名称
	 * @param endAct 结束活动节点名称
	 */
	@RequestMapping(value = "histoicFlow")
	public String histoicFlow(Act act, String startAct, String endAct, Model model){
		if (StringUtils.isNotBlank(act.getProcInstId())){
			List<Act> histoicFlowList = actTaskService.histoicFlowList(act.getProcInstId(), startAct, endAct);
			model.addAttribute("histoicFlowList", histoicFlowList);
		}
		return "modules/act/actTaskHistoricFlow";
	}
	
	/**
	 * 获取流程列表
	 * @param category 流程分类
	 */
	@RequestMapping(value = "process")
	public String processList(String category, HttpServletRequest request, HttpServletResponse response, Model model) {
	    Page<Object[]> page = new Page<Object[]>(request, response);
	    page = actTaskService.processList(page, category);
		model.addAttribute("page", page);
		model.addAttribute("category", category);
		return "modules/act/actTaskProcessList";
	}

	/**
	 * 获取流程表单
	 * @param act
	 * @return
	 */
	@RequestMapping(value = "form")
	public String form(Act act){
		// 获取流程XML上的表单KEY
		String formKey = actTaskService.getFormKey(act.getProcDefId(), act.getTaskDefKey());

		// 获取流程实例对象
		if (act.getProcInstId() != null){
			ProcessInstance p =actTaskService.getProcIns(act.getProcInstId());
			if(p==null){
				HistoricProcessInstance h =actTaskService.getHistoricProcIns(act.getProcInstId());
				if(h!=null){
					String[] business_key_=h.getBusinessKey().split(":");
					if(business_key_.length>=1){
						act.setBusinessId(h.getBusinessKey().split(":")[1]);
					}
				}

			}else {
				act.setProcIns(p);
			}
		}
		
		return "redirect:" + ActUtils.getFormUrl(formKey, act);
	}

	/**
	 * 启动流程
	 * @param act
	 * @param table
	 * @param id
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "start")
	@ResponseBody
	public String start(Act act, String table, String id, Model model) throws Exception {
		actTaskService.startProcess(act.getProcDefKey(), act.getBusinessId(), act.getBusinessTable(), act.getTitle());
		return "true";//adminPath + "/act/task";
	}

	/**
	 * 签收任务
	 * @param act
	 * @return
	 */
	@RequestMapping(value = "claim")
	@ResponseBody
	public String claim(Act act) {
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
		actTaskService.claim(act.getTaskId(), userId);
		return "true";//adminPath + "/act/task";
	}

	/**
	 * 完成任务
	 * @param act
	 * @return
	 */
	@RequestMapping(value = "complete")
	@ResponseBody
	public String complete(Act act) {
		actTaskService.complete(act.getTaskId(), act.getProcInstId(), act.getComment(), act.getVars().getVariableMap());
		return "true";//adminPath + "/act/task";
	}
	
	/**
	 * 读取带跟踪的图片
	 */
	@RequestMapping(value = "trace/photo/{procDefId}/{execId}")
	public void tracePhoto(@PathVariable("procDefId") String procDefId, @PathVariable("execId") String execId, HttpServletResponse response) throws Exception {
		InputStream imageStream = actTaskService.tracePhoto(procDefId, execId);
		
		// 输出资源内容到相应对象
		byte[] b = new byte[1024];
		int len;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	
	/**
	 * 输出跟踪流程信息
	 * 
	 * @param proInsId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "trace/info/{proInsId}")
	public List<Map<String, Object>> traceInfo(@PathVariable("proInsId") String proInsId) throws Exception {
		List<Map<String, Object>> activityInfos = actTaskService.traceProcess(proInsId);
		return activityInfos;
	}

	/**
	 * 显示流程图
	 */
	@RequestMapping(value = "processPic")
	public void processPic(String procDefId, HttpServletResponse response) throws Exception {
		ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();
		String diagramResourceName = procDef.getDiagramResourceName();
		InputStream imageStream = repositoryService.getResourceAsStream(procDef.getDeploymentId(), diagramResourceName);
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = imageStream.read(b, 0, 1024)) != -1) {
			response.getOutputStream().write(b, 0, len);
		}
	}
	
	/**
	 * 获取跟踪信息
	 */
	@RequestMapping(value = "processMap")
	public String processMap(String procDefId, String proInstId, Model model)
			throws Exception {
		List<ActivityImpl> actImpls = new ArrayList<ActivityImpl>();
		ProcessDefinition processDefinition = repositoryService
				.createProcessDefinitionQuery().processDefinitionId(procDefId)
				.singleResult();
		ProcessDefinitionImpl pdImpl = (ProcessDefinitionImpl) processDefinition;
		String processDefinitionId = pdImpl.getId();// 流程标识
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		List<ActivityImpl> activitiList = def.getActivities();// 获得当前任务的所有节点
		List<String> activeActivityIds = runtimeService.getActiveActivityIds(proInstId);
		for (String activeId : activeActivityIds) {
			for (ActivityImpl activityImpl : activitiList) {
				String id = activityImpl.getId();
				if (activityImpl.isScope()) {
					if (activityImpl.getActivities().size() > 1) {
						List<ActivityImpl> subAcList = activityImpl
								.getActivities();
						for (ActivityImpl subActImpl : subAcList) {
							String subid = subActImpl.getId();
							System.out.println("subImpl:" + subid);
							if (activeId.equals(subid)) {// 获得执行到那个节点
								actImpls.add(subActImpl);
								break;
							}
						}
					}
				}
				if (activeId.equals(id)) {// 获得执行到那个节点
					actImpls.add(activityImpl);
					System.out.println(id);
				}
			}
		}
		model.addAttribute("procDefId", procDefId);
		model.addAttribute("proInstId", proInstId);
		model.addAttribute("actImpls", actImpls);
		return "modules/act/actTaskMap";
	}
	
	/**
	 * 删除任务
	 * @param taskId 流程实例ID
	 * @param reason 删除原因
	 */
	@RequiresPermissions("act:process:edit")
	@RequestMapping(value = "deleteTask")
	public String deleteTask(String taskId, String reason, RedirectAttributes redirectAttributes) {
		if (StringUtils.isBlank(reason)){
			addMessage(redirectAttributes, "请填写删除原因");
		}else{
			actTaskService.deleteTask(taskId, reason);
			addMessage(redirectAttributes, "删除任务成功，任务ID=" + taskId);
		}
		return "redirect:" + adminPath + "/act/task";
	}
	
	/**
	 * 任务签收并办理
	 * @param act
	 * @return
	 */
	@RequestMapping(value = "claimForm")
	public String claimForm(Act act){
		System.out.println("任务办理人编号："+act.getAssignee());
		String userId = UserUtils.getUser().getLoginName();//ObjectUtils.toString(UserUtils.getUser().getId());
		actTaskService.claim(act.getTaskId(), userId);
		// 获取流程XML上的表单KEY
		String formKey = actTaskService.getFormKey(act.getProcDefId(), act.getTaskDefKey());

		// 获取流程实例对象
		if (act.getProcInstId() != null){
			ProcessInstance p =actTaskService.getProcIns(act.getProcInstId());
			if(p==null){
				HistoricProcessInstance h =actTaskService.getHistoricProcIns(act.getProcInstId());
				if(h!=null){
					String[] business_key_=h.getBusinessKey().split(":");
					if(business_key_.length>=1){
						act.setBusinessId(h.getBusinessKey().split(":")[1]);
					}
				}

			}else {
				act.setProcIns(p);
			}
		}
		
		return "redirect:" + ActUtils.getFormUrl(formKey, act);
	}
	
}
