package com.tepusoft.modules.processformdefine.web;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.tepusoft.common.dto.JqueryResult;
import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.utils.DateUtils;
import com.tepusoft.common.utils.IdGen;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.modules.act.service.ActTaskService;
import com.tepusoft.modules.act.utils.ActUtils;
import com.tepusoft.modules.processformdefine.entity.TableColumnEntity;
import com.tepusoft.modules.processformdefine.service.ProcessFormDefineService;
import com.tepusoft.modules.sys.entity.Dict;
import com.tepusoft.modules.sys.entity.User;
import com.tepusoft.modules.sys.utils.UserUtils;

/**
 * 测试
 * @author wxp
 * @version 2017-04-27
 */
@Controller
@RequestMapping(value = "${adminPath}/processFormDefine/commons")
public class ProcessFormDefineController extends BaseController{
	
	@Autowired
	private ProcessFormDefineService processFormDefineService;
	
	@Autowired
	private ActTaskService actTaskService;
	
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RuntimeService runtimeService;
	
	
	/**
	 * 根据表名查询表中数据
	 * @author wxp
	 * @param model
	 * @return
	 */
	//@RequiresPermissions("rcs:rcsAnnex:view")
	@RequestMapping(value = {"searchInfo"})
	public String form(String tableName,Model model) {
		
		Map<String,String> chineseNameMap=processFormDefineService.getColumnsByTableName(tableName);
		List<String> pageColumnNameList=new ArrayList<String>();
		for(Entry<String, String> entry:chineseNameMap.entrySet()){
			pageColumnNameList.add(entry.getKey());
		}
		List<Map<String,Object>> columnList=processFormDefineService.getColumnsList(tableName,pageColumnNameList);
		model.addAttribute("pageColumnNameList", pageColumnNameList);
		model.addAttribute("chineseNameMap", chineseNameMap);
		model.addAttribute("columnList", columnList);
		model.addAttribute("tableName", tableName);
		return "modules/processformdefine/engineList";
	}
	
	/**
	 * 进入新增或者修改数据页面
	 * @param id
	 * @param tableName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"editInfo","addInfo"})
	public String editInfo(String id,String tableName,Model model){
		
		Map<String,String> chineseNameMap=processFormDefineService.getColumnsByTableName(tableName);
		List<String> pageColumnNameList=new ArrayList<String>();
		Map<String,Object> infoMap=new HashMap<String, Object>();
		if("".equals(id)){
			for(Entry<String, String> entry:chineseNameMap.entrySet()){
				pageColumnNameList.add(entry.getKey());
				infoMap.put(entry.getKey(),"");
			}
		}else{
			for(Entry<String, String> entry:chineseNameMap.entrySet()){
				pageColumnNameList.add(entry.getKey());
			}
			infoMap=processFormDefineService.getInfo(id,tableName,pageColumnNameList);
		}
		model.addAttribute("pageColumnNameList", pageColumnNameList);
		model.addAttribute("chineseNameMap", chineseNameMap);
		model.addAttribute("infoMap", infoMap);
		model.addAttribute("tableName", tableName);
		return "modules/processformdefine/addOrUpdateInfo";
	}
	
	
	/**
	 * 保存新增或者修改的数据
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = {"saveInfo"})
	public String saveInfo(HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes){
		boolean flag=false;
		String tableName=request.getParameter("tableName");
		Object id="";
		int count=0;
		try{
			List<TableColumnEntity> columnEntityList = processFormDefineService.getColumnEntityList(tableName);
			for(TableColumnEntity tableColumnEntity:columnEntityList){
				Object columnValue=request.getParameter(tableColumnEntity.getPageColumnName());
				if(tableColumnEntity.getPageColumnName().equals("id")&&columnValue.equals("")){
					columnValue=IdGen.uuid();
					flag=true;
				}else if(tableColumnEntity.getPageColumnName().equals("id")){
					id=columnValue;
				}
				tableColumnEntity.setColumnValue(columnValue);
			}
			if(flag){
				count=processFormDefineService.saveInfo(tableName,columnEntityList);
			}else{
				count=processFormDefineService.updateInfo(tableName,columnEntityList,id);
			}
		}catch(Exception e){
			e.printStackTrace();
			addMessage(redirectAttributes, "数据保存失败！失败信息:"+e.getMessage());
		}
		if(count>0){
			addMessage(redirectAttributes, "数据保存成功！");
		}
		return "redirect:" + adminPath + "/man/commons/searchInfo?tableName="+tableName;
	}
	
	/**
	 * 根据表名和id删除对应的数据
	 * @param tableName
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = {"delete"})
	public String deleteInfo(String tableName,String id,RedirectAttributes redirectAttributes) {
		int count=0;
		try{
			count=processFormDefineService.deleteInfo(tableName,id);
		}catch(Exception e){
			e.printStackTrace();
			addMessage(redirectAttributes, "数据删除失败！失败信息:"+e.getMessage());
		}
		if(count>0){
			addMessage(redirectAttributes, "数据删除成功！");
		}
		return "redirect:" + adminPath + "/man/commons/searchInfo?tableName="+tableName;
	}
	
	/**
	 * 启动流程
	 * @param tableName
	 * @param id
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = {"startProcess"})
	public String startProcess(String tableName,String id,RedirectAttributes redirectAttributes) {
		// 启动流程
		String procInstId="";
		try{
			procInstId=actTaskService.startProcess(ActUtils.PD_TEST_CUSTOMFORM[0], tableName, id, "自定义表单-流程发起时间："+DateUtils.formatDateTime(new Date()));
		}catch(Exception e){
			e.printStackTrace();
			addMessage(redirectAttributes, "流程启动失败！失败信息:"+e.getMessage());
		}
        if(procInstId.length()>0){
        	addMessage(redirectAttributes, "流程启动成功！");
        }
		return "redirect:" + adminPath + "/man/commons/searchInfo?tableName="+tableName;
	}
	
	/**
	 * 进入任务办理
	 * @return
	 */
	@RequestMapping(value = {"taskList"})
	public String getTaskList(HttpServletRequest request,Model model) {
		//获取业务信息id和业务表名
		String id=request.getParameter("businessNumber");
		String tableName=request.getParameter("businessTable");
		//工作流需要传的任务id和流程实例ID
		String taskId=request.getParameter("act.taskId");
		String procInstId=request.getParameter("act.procInstId");
		//1、获取业务信息
		Map<String,String> chineseNameMap=processFormDefineService.getColumnsByTableName(tableName);
		List<String> pageColumnNameList=new ArrayList<String>();
		Map<String,Object> infoMap=new HashMap<String, Object>();
		for(Entry<String, String> entry:chineseNameMap.entrySet()){
			pageColumnNameList.add(entry.getKey());
		}
		infoMap=processFormDefineService.getInfo(id,tableName,pageColumnNameList);
		//2、返回审核记录 TODO
		//List<Comment> commentList=null;
		
		model.addAttribute("pageColumnNameList", pageColumnNameList);
		model.addAttribute("chineseNameMap", chineseNameMap);
		model.addAttribute("infoMap", infoMap);
		model.addAttribute("tableName", tableName);
		
		//model.addAttribute("commentList", commentList);
		model.addAttribute("taskId",taskId);
		model.addAttribute("procInstId",procInstId);
		return "modules/processformdefine/processInfo";
	}
	
	/**
	 * 完成任务
	 * @param taskId
	 * @param procInstId
	 * @param opinion
	 * @param pass
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"completeProcess"})
	public String completeProcess(String taskId,String procInstId,String opinion,String pass,String tableName,Model redirectAttributes) {
		Map<String, Object> vars = Maps.newHashMap();
		vars.put("pass", pass);
		try{
			actTaskService.complete(taskId, procInstId, opinion, vars);
		}catch(Exception e){
			e.printStackTrace();
			addMessage(redirectAttributes, "流程提交失败！失败信息:"+e.getMessage());
		}
		addMessage(redirectAttributes, "流程已提交！");
		return "redirect:" + adminPath + "/man/commons/searchInfo?tableName="+tableName;
	}
	
	/**
	 * 进入选择打回到哪个节点的页面
	 * @param taskId
	 * @param procInstId
	 * @param opinion
	 * @param pass
	 * @param tableName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"rejectTask"})
	public String rejectTask(String taskId,String procInstId,String opinion,String tableName,Model model) {
		//rejectNode存放可回退节点的key和name
		Map<String,String> rejectNode=new HashMap<String, String>();
		//获取当前活动节点，以便去从节点中去掉当前节点
		Task taskEntity=taskService.createTaskQuery().taskId(taskId).singleResult();
		//1、按照创建时间升序排序查询出历史任务节点信息
		List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery().processInstanceId(procInstId).orderByTaskCreateTime().asc().list();
		//历史任务表中不存在打回数据时，任务定义的key是不同的，
		//当historicTaskInstanceList中的第i条数据与当前活动key相同时，说明活动任务是被打回的，
		//由于historicTaskInstanceList是按照创建时间升序排序的，因此historicTaskInstanceList中第i条数据之后的数据，存在两种数据：
		//一种是当前活动节点之后的节点任务；第二种是活动节点的任务
		for(int i=0;i<historicTaskInstanceList.size();i++){
			if(historicTaskInstanceList.get(i).getTaskDefinitionKey().equals(taskEntity.getTaskDefinitionKey())){
				break;
			}
			if(!historicTaskInstanceList.get(i).getTaskDefinitionKey().equals(taskEntity.getTaskDefinitionKey())){
				//任务定义的key
				String key=historicTaskInstanceList.get(i).getTaskDefinitionKey();
				//任务节点名称
				String value=historicTaskInstanceList.get(i).getName();
				rejectNode.put(key, value);
			}
		}
		model.addAttribute("rejectNode", rejectNode);
		model.addAttribute("tableName", tableName);
		model.addAttribute("taskId", taskId);
		model.addAttribute("opinion", opinion);
		return "modules/processformdefine/taskNode";
	}
	
	/**
	 * 完成打回操作
	 * @param taskId
	 * @param procInstId
	 * @param opinion
	 * @param pass
	 * @param tableName
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"completeRejectTask"})
	public String completeRejectTask(String taskId,String nodeKey,String opinion,String pass,String tableName,RedirectAttributes redirectAttributes) {
		// 1、根据taskid获取当前的活动节点
		Task taskEntity = taskService.createTaskQuery().taskId(taskId)
				.singleResult();
		// 当前任务的key
		String taskDefKey = taskEntity.getTaskDefinitionKey();
		String procInstId = taskEntity.getProcessInstanceId();
		// 获得当前流程的定义模型

		ProcessDefinitionEntity processDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(taskEntity
						.getProcessDefinitionId());

		// 获得当前流程定义模型的所有任务节点

		List<ActivityImpl> activitilist = processDefinition.getActivities();
		ActivityImpl currActiviti = null;// 当前活动节点
		ActivityImpl destActiviti = null;// 驳回目标节点
		int sign = 0;
		for (ActivityImpl activityImpl : activitilist) {
			// 确定当前活动activiti节点
			if (taskDefKey.equals(activityImpl.getId())) {
				currActiviti = activityImpl;
				sign++;
			} else if (nodeKey.equals(activityImpl.getId())) {
				destActiviti = activityImpl;
				sign++;
			}
			if (sign == 2) {
				break;// 如果两个节点都获得,退出跳出循环
			}
		}

		List<PvmTransition> hisPvmTransitionList = new ArrayList<PvmTransition>(
				0);

		for (PvmTransition pvmTransition : currActiviti
				.getOutgoingTransitions()) {
			hisPvmTransitionList.add(pvmTransition);
		}
		// 清空当前活动几点的所有流出项
		currActiviti.getOutgoingTransitions().clear();
		/*System.out
				.println("//-->currActiviti.getOutgoingTransitions().clear():"
						+ currActiviti.getOutgoingTransitions().size());*/
		// 为当前节点动态创建新的流出项
		TransitionImpl newTransitionImpl = currActiviti
				.createOutgoingTransition();
		// 为当前活动节点新的流出目标指定流程目标
		newTransitionImpl.setDestination(destActiviti);
		// Description为0表示是驳回的操作
		taskEntity.setDescription("0");
		taskService.saveTask(taskEntity);

		// 设置驳回意见
		// 添加意见之前，需要先设置当前任务的审核人
		User user = UserUtils.getUser();
		Authentication.setAuthenticatedUserId(user.getId());
		taskService.addComment(taskId, procInstId, opinion);

		// 设定驳回标志
		 Map<String, Object> variables = new HashMap<String, Object>(0);
		 variables.put("pass","0");
		 
		// 执行当前任务驳回到目标任务draft

		taskService.complete(taskEntity.getId(),variables);

		// 清除目标节点的新流入项

		destActiviti.getIncomingTransitions().remove(newTransitionImpl);
		// 清除原活动节点的临时流程项

		currActiviti.getOutgoingTransitions().clear();
		// 还原原活动节点流出项参数

		currActiviti.getOutgoingTransitions().addAll(hisPvmTransitionList);

		addMessage(redirectAttributes, "流程已驳回！");
		return "redirect:" + adminPath + "/man/commons/searchInfo?tableName="+tableName;
	}
	
	
	//进入我发起的流程页面
	@RequestMapping(value = { "intoProcessListJsp" })
	public String intoProcessListJsp() {

		return "modules/processformdefine/myProcessList";
	}
	
	//获取我发起的流程列表
	@RequestMapping(value = {"myRuningProcessList"})
	@ResponseBody
	public Page<HistoricProcessInstance> myRuningProcessList(Model model,HttpServletRequest request,HttpServletResponse response){
		//通过用户登录名查询出当前用户的已发起流程
		User user = UserUtils.getUser();
		String loginName=user.getLoginName();
		List<HistoricProcessInstance> myProcessList = historyService.createHistoricProcessInstanceQuery().startedBy(loginName).list();
		Page<HistoricProcessInstance> page=new Page<HistoricProcessInstance>(request, response);
		page.setList(myProcessList);
        model.addAttribute("page", page);
		return page;
		
	}
	
	/**
	 * 取回流程（这个功能还没做完 呢）
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = {"getBackProcess"})
	@ResponseBody
	public JqueryResult getBackProcess(String processInstId,HttpServletRequest request,HttpServletResponse response){
		//获取流程实例
		ProcessInstance processInstance= runtimeService.createProcessInstanceQuery().processInstanceId(processInstId).singleResult();
		if(processInstance==null){
			return new JqueryResult.Builder(FAIL_CODE).message("流程取回失败，流程已经结束！").build();
		}
		// 取得流程定义
		ProcessDefinitionEntity definition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
                .getDeployedProcessDefinition(processInstance.getProcessDefinitionId());
		//获取当前任务(可能有多个)
		Map<String, Object> variables=processInstance.getProcessVariables();
		List<Task> taskList=taskService.createTaskQuery().processInstanceId(processInstId).list();
		for(Task task:taskList){
			// 取得下一步活动
	        ActivityImpl currActivity = ((ProcessDefinitionImpl) definition)
	                .findActivity(task.getTaskDefinitionKey()); 
	        List<PvmTransition> nextTransitionList = currActivity
                    .getOutgoingTransitions();
	        for (PvmTransition nextTransition : nextTransitionList) {
	        	PvmActivity nextActivity = nextTransition.getDestination();
	        	List<Task> nextTasks = taskService.createTaskQuery().processInstanceId(processInstId)
                        .taskDefinitionKey(nextActivity.getId()).list();
	        	for (Task nextTask : nextTasks) {   
                    //取活动，清除活动方向
                    List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
                    List<PvmTransition> pvmTransitionList = nextActivity
                            .getOutgoingTransitions();
                    for (PvmTransition pvmTransition : pvmTransitionList) {
                        oriPvmTransitionList.add(pvmTransition);
                    }
                    pvmTransitionList.clear();
                    //建立新方向
                    ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) definition)
                            .findActivity(nextTask.getTaskDefinitionKey());
                    TransitionImpl newTransition = nextActivityImpl
                            .createOutgoingTransition();
                    newTransition.setDestination(currActivity);
                    //完成任务
                    taskService.complete(nextTask.getId(), variables);
                    historyService.deleteHistoricTaskInstance(nextTask.getId());
                    //恢复方向
                    currActivity.getIncomingTransitions().remove(newTransition);
                    List<PvmTransition> pvmTList = nextActivity
                            .getOutgoingTransitions();
                    pvmTList.clear();
                    for (PvmTransition pvmTransition : oriPvmTransitionList) {
                        pvmTransitionList.add(pvmTransition);
                    }
                }
	        }
	        historyService.deleteHistoricTaskInstance(task.getId());
		}
		
		return new JqueryResult.Builder(SUCCESS_CODE).message("流程取回成功！").build();
		
	} 
    
}
