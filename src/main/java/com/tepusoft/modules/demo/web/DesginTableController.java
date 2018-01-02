
package com.tepusoft.modules.demo.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tepusoft.common.dto.*;
import com.tepusoft.common.dto.JqueryResult;
import com.tepusoft.modules.sys.entity.User;
import com.tepusoft.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.modules.demo.entity.DesginTable;
import com.tepusoft.modules.demo.entity.DesginField;
import com.tepusoft.modules.demo.service.DesginTableService;
import com.tepusoft.modules.demo.service.DesginFiledService;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 业务表Controller
 * @author ThinkGem
 * @version 2013-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/demo/desginTable")
public class DesginTableController extends BaseController {

    @Autowired
    private DesginTableService desginTableService;
    @Autowired
    private DesginFiledService desginFiledService;
    protected static  int SUCCESS_CODE=200;
    protected static  int FAIL_CODE=500;
    @ModelAttribute
    public DesginTable get(@RequestParam(required=false) String id) {
        if (StringUtils.isNotBlank(id)){
            return desginTableService.get(id);
        }else{
            return new DesginTable();
        }
    }
    @RequiresPermissions("sys:desgin:view")
    @RequestMapping(value = {"list", ""})
    public String list(DesginTable desginTable, HttpServletRequest request, HttpServletResponse response, Model model) {
        return  "modules/demo/desginTableList";
    }

    @RequiresPermissions("sys:desgin:view")
    @RequestMapping("dataList")
    @ResponseBody
    public Page<DesginTable> dataList(DesginTable desginTable,Model model,HttpServletRequest request,HttpServletResponse response) {
        Page<DesginTable> page = desginTableService.find(new Page<DesginTable>(request, response), desginTable);
        desginTableService.checkTableName(  desginTable.getTableName());
        return page;
    }

    @RequestMapping(value = "form")
    public String form(DesginTable desginTable, Model model,HttpServletRequest request, HttpServletResponse response) {
        DesginTable  desginTable1= this.get(request.getParameter("id"));
        model.addAttribute("desginTable",desginTable1);
        return "modules/demo/desginTableForm";
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public JqueryResult save(DesginTable desginTable, Model model,HttpServletRequest request, RedirectAttributes redirectAttributes) {
        String message=beanValidatorStr(model,desginTable);
        String desginFieldList=  request.getParameter("desginFieldList");
        List<DesginField> desginFields=JSONArray.parseArray(desginFieldList,DesginField.class );
        for(DesginField field:desginFields){
            field.setTableId(desginTable.getTableName());
        }
        //判断desginTable 的Id是否为空，
        if( StringUtils.isBlank(desginTable.getId())){
//            //如果为空则为新增,,判断该表名是否存在
                if(desginTableService.checkTableName(desginTable.getTableName())){
            //        如果不存在
                    desginTableService.save(desginTable);
                    desginFiledService.save(desginFields);
                    message = "保存：" + desginTable.getTableName() + "表成功";
                    }else{
                            message = "保存失败！表名：" + desginTable.getTableName() + "已存在";
                    }
        }else{
            desginTableService.save(desginTable);
            desginFiledService.save(desginFields);
            message = "更新" + desginTable.getTableName() + "表数据成功";
        }
        return new JqueryResult.Builder(SUCCESS_CODE).message(message).build();
    }
    @RequestMapping(value = "delete")
    @ResponseBody
    public JqueryResult delete(DesginTable desginTable, RedirectAttributes redirectAttributes) {
        desginTableService.delete(desginTable);
        return new JqueryResult.Builder(SUCCESS_CODE).message("删除用户成功").build();

    }

    @RequestMapping(value = "createTable")
    @ResponseBody
    public JqueryResult    createTable(DesginTable desginTable, RedirectAttributes redirectAttributes) {
        desginTableService.createTable(desginTable);

        return new JqueryResult.Builder(SUCCESS_CODE).message("同步到数据库成功").build();

    }

    @RequestMapping(value = "updateTable")
    @ResponseBody
    public JqueryResult    updateTable(DesginTable desginTable, RedirectAttributes redirectAttributes) {
        desginTableService.updateTable(desginTable);
        return new JqueryResult.Builder(SUCCESS_CODE).message("同步到数据库成功").build();

    }
}
