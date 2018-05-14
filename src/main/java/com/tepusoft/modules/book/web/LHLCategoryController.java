package com.tepusoft.modules.book.web;


import com.tepusoft.common.dto.JqueryResult;
import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.modules.book.entity.LHLBook;
import com.tepusoft.modules.book.entity.LHLCategory;
import com.tepusoft.modules.book.service.LHLBookService;
import com.tepusoft.modules.book.service.LHLCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping(value = "${adminPath}/lhl/category")
public class LHLCategoryController extends BaseController{

    @Autowired
    private LHLCategoryService lhlCategoryService;


    @ModelAttribute
    public LHLCategory get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return lhlCategoryService.get(id);
        } else {
            return new LHLCategory();
        }
    }

    @RequestMapping("li")
    public String li(){
        return "modules/book/category/list";
    }


    @ResponseBody
    @RequestMapping(value = "list")
    public List<LHLCategory> list(LHLCategory category){
        return lhlCategoryService.findList(category);
    }

    @ResponseBody
    @RequestMapping(value = "datalist")
    public Page<LHLCategory> datelist(LHLCategory category,HttpServletRequest request,HttpServletResponse response){
        return lhlCategoryService.findPage(new Page<LHLCategory>(request,response),category);
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public JqueryResult delete(LHLCategory category){
        try {
            lhlCategoryService.delete(category);
            return new JqueryResult.Builder(SUCCESS_CODE).message("删除成功").build();
        }catch (Exception e){
            logger.error("删除失败",e);
            return new JqueryResult.Builder(FAIL_CODE).message("删除失败").build();
        }
    }

    @RequestMapping(value = "form")
    public String from(LHLCategory category, Model model) {
        model.addAttribute("category",category);
        return "modules/book/category/form";
    }

    @ResponseBody
    @RequestMapping(value = "save")
    public JqueryResult save(LHLCategory category) {
        try {
            lhlCategoryService.save(category);
            return new JqueryResult.Builder(SUCCESS_CODE).message("保存成功").build();
        } catch (Exception e) {
            logger.error("保存失败", e);
            return new JqueryResult.Builder(FAIL_CODE).message("保存失败").build();
        }
    }







}
