package com.tepusoft.modules.book.web;


import com.tepusoft.common.dto.JqueryResult;
import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.common.utils.excel.ExportExcel;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.modules.book.entity.LHLBook;
import com.tepusoft.modules.book.service.LHLBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping(value = "${adminPath}/lhl/book")
public class LHLBookController extends BaseController {

    @Autowired
    private LHLBookService lhlBookService;


    @ModelAttribute
    public LHLBook get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return lhlBookService.get(id);
        } else {
            return new LHLBook();
        }
    }

    @RequestMapping(value = "list")
    public String list() {
        return "modules/book/list";
    }

    /**
     * 获取列表
     *
     * @param lhlBook
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "datalist")
    public Page<LHLBook> BookList(LHLBook lhlBook, HttpServletRequest request, HttpServletResponse response) {
        return lhlBookService.findPage(new Page<LHLBook>(request, response), lhlBook);
    }

    /**
     * 删除
     *
     * @param book
     * @return
     */
    @ResponseBody
    @RequestMapping("delete")
    public JqueryResult delete(LHLBook book) {

        try {
            lhlBookService.delete(book);
            return new JqueryResult.Builder(SUCCESS_CODE).message("删除成功").build();
        } catch (Exception e) {
            logger.error("删除失败", e);
            return new JqueryResult.Builder(FAIL_CODE).message("删除失败").build();
        }
    }

    @RequestMapping(value = "form")
    public String from(LHLBook book, Model model) {
        model.addAttribute("book",book);
        return "modules/book/form";
    }

    @ResponseBody
    @RequestMapping(value = "save")
    public JqueryResult save(LHLBook book) {
        System.out.println(book.toString());
        try {
            lhlBookService.save(book);
            return new JqueryResult.Builder(SUCCESS_CODE).message("保存成功").build();
        } catch (Exception e) {
            logger.error("保存失败", e);
            return new JqueryResult.Builder(FAIL_CODE).message("保存失败").build();
        }
    }

    @RequestMapping("export")
    public void export(LHLBook book,HttpServletRequest request,HttpServletResponse response) throws Exception {
        try {
            List<LHLBook> list = lhlBookService.findList(book);
            new ExportExcel("图书信息",LHLBook.class).setDataList(list).write(response,"图书信息.xlsx").dispose();
        } catch (IOException e) {
            logger.error("导出失败",e);
            throw e;
        }
    }




}
