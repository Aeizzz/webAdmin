package com.tepusoft.modules.demo.web;

import com.tepusoft.common.dto.JqueryResult;
import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.modules.demo.entity.DesginField;
import com.tepusoft.modules.demo.entity.DesginTable;
import com.tepusoft.modules.demo.service.DesginFiledService;
import com.tepusoft.modules.demo.service.DesginTableService;
import com.tepusoft.modules.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liying on 2017/6/22.
 */
@Controller
@RequestMapping(value = "${adminPath}/demo/desginField")
public class DesginFiledController {
    @Autowired
    private DesginTableService desginTableService;
    @Autowired
    private DesginFiledService desginFiledService;
    protected static  int SUCCESS_CODE=200;
    protected static  int FAIL_CODE=500;

//    @ModelAttribute
//    public DesginField get(@RequestParam(required=false) String id) {
//        if (StringUtils.isNotBlank(id)){
//            return desginFiledService.getField(id);
//        }else{
//            return new DesginField();
//        }
//    }

    @RequestMapping("dataList")
    @ResponseBody
    public Page<DesginField> dataList(DesginField desginField, Model model, HttpServletRequest request, HttpServletResponse response) {
        String tableId=  request.getParameter("tableId");
        Page<DesginField> page = desginFiledService.find(new Page<DesginField>(request, response), desginField);
        return page;
    }
    @RequestMapping(value = "delete")
    @ResponseBody
    public JqueryResult delete(DesginField desginField, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        String  id=request.getParameter("Id");
        desginField.setId(id);
        desginFiledService.delete(desginField);
        return new JqueryResult.Builder(SUCCESS_CODE).message("删除成功").build();
    }
}
