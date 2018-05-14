package com.tepusoft.modules.book.web;


import com.tepusoft.common.dto.JqueryResult;
import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.modules.book.entity.LHLPublishing;
import com.tepusoft.modules.book.service.LHLPublishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "${adminPath}/lhl/publishing")
public class LHLPublishingController extends BaseController {

    @Autowired
    private LHLPublishingService publishingService;


    @ResponseBody
    @RequestMapping("list")
    public List<LHLPublishing> list(LHLPublishing publishing) {
        return publishingService.findList(publishing);
    }


    @RequestMapping(value = "li")
    public String li() {
        return "modules/book/publishing/list";
    }


    @ResponseBody
    @RequestMapping(value = "datalist")
    public Page<LHLPublishing> datelist(LHLPublishing publishing, HttpServletRequest request, HttpServletResponse response) {
        return publishingService.findPage(new Page<LHLPublishing>(request, response), publishing);
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public JqueryResult delete(LHLPublishing publishing) {
        try {
            publishingService.delete(publishing);
            return new JqueryResult.Builder(SUCCESS_CODE).message("删除成功").build();
        } catch (Exception e) {
            logger.error("删除失败", e);
            return new JqueryResult.Builder(FAIL_CODE).message("删除失败").build();
        }
    }

    @RequestMapping(value = "form")
    public String from(LHLPublishing publishing, Model model) {
        model.addAttribute("publishing", publishing);
        return "modules/book/publishing/form";
    }


    @ResponseBody
    @RequestMapping(value = "save")
    public JqueryResult save(LHLPublishing publishing) {
        try {
            publishingService.save(publishing);
            return new JqueryResult.Builder(SUCCESS_CODE).message("保存成功").build();
        } catch (Exception e) {
            logger.error("保存失败", e);
            return new JqueryResult.Builder(SUCCESS_CODE).message("保存失败").build();
        }
    }


}
