/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tepusoft.modules.sys.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tepusoft.common.config.Global;
import com.tepusoft.common.dto.JqueryResult;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.modules.sys.entity.Menu;
import com.tepusoft.modules.sys.entity.Office;
import com.tepusoft.modules.sys.entity.Role;
import com.tepusoft.modules.sys.entity.User;
import com.tepusoft.modules.sys.service.OfficeService;
import com.tepusoft.modules.sys.service.SystemService;
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
import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.utils.Collections3;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.modules.sys.utils.UserUtils;

/**
 * 角色Controller
 * @author ThinkGem
 * @version 2013-12-05
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/role")
public class RoleController extends BaseController {

	@Autowired
	private SystemService systemService;
	
	@Autowired
	private OfficeService officeService;


	protected static  int SUCCESS_CODE=200;
	protected static  int FAIL_CODE=500;

	@ModelAttribute("role")
	public Role get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			Role role=systemService.getRole(id);
			return role;
		}else{
			return new Role();
		}
	}
	
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = {"list", ""})
	public String list(Role role, Model model) {
		/*List<Role> list = systemService.findAllRole();
		model.addAttribute("list", list);*/
		return "modules/sys/roleList";
	}

	@RequestMapping(value="dataList")
	@ResponseBody
	public List<Role> list(String roleName,Model model,HttpServletRequest request,HttpServletResponse response){
		List<Role> list = systemService.findAllRole();
		List<Role> dataList=Lists.newArrayList();
		if(StringUtils.isNoneBlank(roleName)){
			for(Role role:list){
				if(role!=null&&role.getName().contains(roleName)){
					dataList.add(role);
				}
			}
			return dataList;
		}
		return list;
	}

	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "form")
	public String form(Role role, Model model) {
		if (role.getOffice()==null){
			//role.setOffice(UserUtils.getUser().getOffice());
			Office office = new Office();
			office.setId(role.getOfficeId());
			office.setName(role.getOfficeName());
			role.setOffice(office);
		}
		model.addAttribute("role", role);
		/*model.addAttribute("menuList", systemService.findAllMenu());
		model.addAttribute("officeList", officeService.findAll());*/
		return "modules/sys/roleForm";
	}
	
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "save")
    @ResponseBody
	public JqueryResult save(Role role, Model model, RedirectAttributes redirectAttributes) {
        String errorMsg=beanValidatorStr(model, role);
        if (StringUtils.isNotBlank(errorMsg)){
			return new JqueryResult.Builder(FAIL_CODE).message(errorMsg).build();
		}
		if (!"true".equals(checkName(role.getOldName(), role.getName()))){
			return new JqueryResult.Builder(FAIL_CODE).message("保存角色'" + role.getName() + "'失败, 角色名已存在").build();
		}
		if (!"true".equals(checkEnname(role.getOldEnname(), role.getEnname()))){
			addMessage(model, "保存角色'" + role.getName() + "'失败, 英文名已存在");
            return new JqueryResult.Builder(FAIL_CODE).message("保存角色'" + role.getName() + "'失败, 英文名已存在").build();

        }
		systemService.saveRole(role);
        return new JqueryResult.Builder(SUCCESS_CODE).message("保存角色'" + role.getName() + "'成功").build();

    }
	
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public JqueryResult delete(Role role, RedirectAttributes redirectAttributes) {
		if(!UserUtils.getUser().isAdmin() && role.getSysData().equals(Global.YES)){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return new JqueryResult.Builder(FAIL_CODE).message("越权操作，只有超级管理员才能修改此数据！").build();
			//return "redirect:" + adminPath + "/sys/role/?repage";
		}
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return new JqueryResult.Builder(FAIL_CODE).message("演示模式，不允许操作！").build();

			//return "redirect:" + adminPath + "/sys/role/?repage";
		}
//		if (Role.isAdmin(id)){
//			addMessage(redirectAttributes, "删除角色失败, 不允许内置角色或编号空");
////		}else if (UserUtils.getUser().getRoleIdList().contains(id)){
////			addMessage(redirectAttributes, "删除角色失败, 不能删除当前用户所在角色");
//		}else{
			systemService.deleteRole(role);
			addMessage(redirectAttributes, "删除角色成功");
//		}
		return new JqueryResult.Builder(SUCCESS_CODE).message("删除角色成功").build();
		//return "redirect:" + adminPath + "/sys/role/?repage";
	}
	
	/**
	 * 角色分配页面
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "assign")
	public String assign(Role role, Model model) {
		/*List<User> userList = systemService.findUser(new User(new Role(role.getId())));
		model.addAttribute("userList", userList);*/
		model.addAttribute("roleId","'"+role.getId()+"'");
		model.addAttribute("roleName","'"+role.getName()+"'");
		return "modules/sys/roleAssign";
	}

	/*@RequiresPermissions("sys:role:edit")*/
	@RequestMapping(value = "roleUser")
	@ResponseBody
	public List<User> roleUser(Role role, Model model, HttpServletResponse response, HttpServletRequest request) {
		List<User> userList = systemService.findUser(new User(new Role(role.getId())));
		//model.addAttribute("userList", userList);
		return userList;
	}

	@RequestMapping(value = "roleUserByPage")
	@ResponseBody
	public Page<User> roleUserByPage(Role role, Model model, HttpServletResponse response, HttpServletRequest request) {
		Page<User> userList = systemService.findPageUser(new Page<User>(request,response),new User(new Role(role.getId())));
		//model.addAttribute("userList", userList);
		return userList;
	}

	
	/**
	 * 角色分配 -- 打开角色分配对话框
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@RequestMapping(value = "usertorole")
	public String selectUserToRole(Role role, Model model) {
		List<User> userList = systemService.findUser(new User(new Role(role.getId())));
		model.addAttribute("role", role);
		model.addAttribute("userList", userList);
		model.addAttribute("selectIds", Collections3.extractToString(userList, "name", ","));
		model.addAttribute("officeList", officeService.findAll());
		return "modules/sys/selectUserToRole";
	}
	
	/**
	 * 角色分配 -- 根据部门编号获取用户列表
	 * @param officeId
	 * @param response
	 * @return
	 */
	@RequiresPermissions("sys:role:view")
	@ResponseBody
	@RequestMapping(value = "users")
	public List<Map<String, Object>> users(String officeId,HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		User user = new User();
		if(StringUtils.isNotBlank(officeId)){
			user.setOffice(new Office(officeId));
		}
		Page<User> page = systemService.findUser(new Page<User>(1, -1), user);
		for (User e : page.getList()) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", 0);
			map.put("name", e.getName());
			mapList.add(map);			
		}
		return mapList;
	}
	
	/**
	 * 角色分配 -- 从角色中移除用户
	 * @param userId
	 * @param roleId
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "outrole")
	@ResponseBody
	public JqueryResult outrole(String userId, String roleId, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return new JqueryResult.Builder(FAIL_CODE).message("演示模式，不允许操作！").build();
			//return "redirect:" + adminPath + "/sys/role/assign?id="+roleId;
		}
		Role role = systemService.getRole(roleId);
		User user = systemService.getUser(userId);
		String message="";
		if (UserUtils.getUser().getId().equals(userId)) {
			message="无法从角色【" + role.getName() + "】中移除用户【" + user.getName() + "】自己！";
			return new JqueryResult.Builder(FAIL_CODE).message(message).build();

		}else {
			if (user.getRoleList().size() <= 1){
				message="用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！这已经是该用户的唯一角色，不能移除。";
				return new JqueryResult.Builder(FAIL_CODE).message(message).build();
			}else{
				Boolean flag = systemService.outUserInRole(role, user);
				if (!flag) {
					message="用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！";
					return new JqueryResult.Builder(FAIL_CODE).message(message).build();

				}else {
					message= "用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除成功！";
					return new JqueryResult.Builder(SUCCESS_CODE).message(message).build();

				}
			}		
		}
		//return "redirect:" + adminPath + "/sys/role/assign?id="+role.getId();
	}
	
	/**
	 * 角色分配
	 * @param role
	 * @param idsArr
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:edit")
	@RequestMapping(value = "assignrole")
	public String assignRole(Role role, String[] idsArr, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/role/assign?id="+role.getId();
		}
		StringBuilder msg = new StringBuilder();
		int newNum = 0;
		for (int i = 0; i < idsArr.length; i++) {
			User user = systemService.assignUserToRole(role, systemService.getUser(idsArr[i]));
			if (null != user) {
				msg.append("<br/>新增用户【" + user.getName() + "】到角色【" + role.getName() + "】！");
				newNum++;
			}
		}
		addMessage(redirectAttributes, "已成功分配 "+newNum+" 个用户"+msg);
		return "redirect:" + adminPath + "/sys/role/assign?id="+role.getId();
	}

	/**
	 * 验证角色名是否有效
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkName")
	public String checkName(String oldName, String name) {
		if (name!=null && name.equals(oldName)) {
			return "true";
		} else if (name!=null && systemService.getRoleByName(name) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 验证角色英文名是否有效
	 * @param oldEnname
	 * @param enname
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkEnname")
	public String checkEnname(String oldEnname, String enname) {
		if (enname!=null && enname.equals(oldEnname)) {
			return "true";
		} else if (enname!=null && systemService.getRoleByEnname(enname) == null) {
			return "true";
		}
		return "false";
	}




	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
											  @RequestParam(required=false) Long grade, @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Office> list = officeService.findAll();
		List<Menu> menuList = systemService.findAllMenu();


		for (int i=0; i<list.size(); i++){
			Office e = list.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& (grade == null || (grade != null && Integer.parseInt(e.getGrade()) <= grade.intValue()))
					&& Global.YES.equals(e.getUseable())){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		for (int i=0; i<menuList.size(); i++){
			Menu e = menuList.get(i);
			if ((StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1))
					 ){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIds());
				map.put("name", e.getName());
				if (type != null && "3".equals(type)){
					map.put("isParent", true);
				}
				mapList.add(map);
			}
		}
		return mapList;
	}


}
