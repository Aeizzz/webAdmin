/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tepusoft.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.tepusoft.common.config.Global;
import com.tepusoft.common.dto.JqueryResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.modules.sys.entity.Office;
import com.tepusoft.modules.sys.entity.User;
import com.tepusoft.modules.sys.service.OfficeService;
import com.tepusoft.modules.sys.utils.DictUtils;
import com.tepusoft.modules.sys.utils.UserUtils;

/**
 * 机构Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/office")
public class OfficeController extends BaseController {

	@Autowired
	private OfficeService officeService;

	//private Office office_all = new Office();
	protected static  int SUCCESS_CODE=200;
	protected static  int FAIL_CODE=500;

	@ModelAttribute("office")
	public Office get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return officeService.get(id);
		}else{
			return new Office();
		}
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {""})
	public String index(Office office, Model model) {
//        model.addAttribute("list", officeService.findAll());
		return "modules/sys/officeIndex";
	}

	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = {"list"})
	public String list(Office office, String flag, Model model) {
       //model.addAttribute("list", officeService.findList(office));
		//office_all = office;
		model.addAttribute("flag", flag);
		return "modules/sys/officeList";
	}


	@RequestMapping(value = {"dataList"})
	@ResponseBody
	public List<Office> dataList(Office office, Model model) {
		List<Office> officeList = Lists.newArrayList();
//		officeList = officeService.findList(office);
		officeList = officeService.findListByParent(office);
		return officeList;
	}
	@RequestMapping(value = {"dataListByParent"})
	@ResponseBody
	public List<Office> dataListByParent(String parent, Model model) {
		List<Office> officeList = Lists.newArrayList();
		Office office = new Office();
		office.setParent(new Office(parent));
		officeList = officeService.findListByParent(office);
		for(Office office1:officeList){
			Office office2 = new Office();
			office2.setParent(new Office(office1.getId()));
			List<Office> list = officeService.findListByParent(office2);
			if(null!=list&&list.size()>0){
				office1.setState("closed");
			}else {
				office1.setState("open");
			}

		}
		return officeList;
	}
	
	@RequiresPermissions("sys:office:view")
	@RequestMapping(value = "form")
	public String form(Office office, Model model) {
		User user = UserUtils.getUser();
		if (office.getParent()==null || office.getParent().getId()==null){
			office.setParent(user.getOffice());
		}
		office.setParent(officeService.get(office.getParent().getId()));

		// 自动获取排序号
		if (StringUtils.isBlank(office.getId())&&office.getParent()!=null){
			int size = 0;
			List<Office> list = officeService.findAll();
			for (int i=0; i<list.size(); i++){
				Office e = list.get(i);
				if (e.getParent()!=null && e.getParent().getId()!=null
						&& e.getParent().getId().equals(office.getParent().getId())){
					size++;
				}
			}
			office.setCode(office.getParent().getCode() + StringUtils.leftPad(String.valueOf(size > 0 ? size+1 : 1), 3, "0"));
		}
		model.addAttribute("office", office);
		return "modules/sys/officeForm";
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public JqueryResult save(Office office, Model model, RedirectAttributes redirectAttributes) {
		String message=beanValidatorStr(model, office);
		if (StringUtils.isNotBlank(message)){
			return new JqueryResult.Builder(FAIL_CODE).message(message).build();
		}
		/*if (!beanValidator(model, office)){
			return form(office, model);
		}*/
		officeService.save(office);
		
		if(office.getChildDeptList()!=null){
			Office childOffice = null;
			for(String id : office.getChildDeptList()){
				childOffice = new Office();
				childOffice.setName(DictUtils.getDictLabel(id, "sys_office_common", "未知"));
				childOffice.setParent(office);
//				childOffice.setArea(office.getArea());
				childOffice.setType("2");
				childOffice.setGrade(String.valueOf(Integer.valueOf(office.getGrade())+1));
				childOffice.setUseable(Global.YES);
				officeService.save(childOffice);
			}
		}
		message = "保存机构" + office.getName() + "成功";
		/*addMessage(redirectAttributes, "保存机构'" + office.getName() + "'成功");
		String id = "0".equals(office.getParentId()) ? "" : office.getParentId();*/
		return new JqueryResult.Builder(SUCCESS_CODE).message(message).build();
		//return "redirect:" + adminPath + "/sys/office/list?id="+id+"&parentIds="+office.getParentIds();
	}
	
	@RequiresPermissions("sys:office:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public JqueryResult delete(Office office, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return new JqueryResult.Builder(FAIL_CODE).message("演示模式，不允许操作！").build();
		}
//		if (Office.isRoot(id)){
//			addMessage(redirectAttributes, "删除机构失败, 不允许删除顶级机构或编号空");
//		}else{
			officeService.delete(office);
			addMessage(redirectAttributes, "删除机构成功");
		   return new JqueryResult.Builder(SUCCESS_CODE).message("删除机构成功").build();
//		}

	}

	/**
	 * 获取机构JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型（1：公司；2：部门/小组/其它：3：用户）
	 * @param grade 显示级别
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
			@RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findList(isAll);

		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);

			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				map.put("type", e.getType());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
					 /*map.put("id", e.getPrimaryPerson().getId());
					map.put("pId", e.getParentId());
					map.put("pIds", e.getParentIds());
					map.put("name", e.getPrimaryPerson().getName()); */
				}
				mapList.add(map);
			}
		}
		return mapList;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "tree")
	public List<Map<String, Object>> tree(@RequestParam(required = false) String id, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String, Object> map = Maps.newHashMap();
		if (StringUtils.isBlank(id)) {
			List<Office> list = officeService.getByParentId("0");
			map.put("id", list.get(0).getId());
			map.put("text", list.get(0).getName());
			map.put("type", list.get(0).getType());
			map.put("state", "closed");
			mapList.add(map);
		} else {
			List<Office> list = officeService.getByParentId(id);
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> child = Maps.newHashMap();
				Office e = list.get(i);
				child.put("id", list.get(i).getId());
				child.put("text", list.get(i).getName());
				child.put("type", list.get(i).getType());
				child.put("state", "closed");
				mapList.add(child);
			}
		}
		return mapList;
	}





}
