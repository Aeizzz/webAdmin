package com.tepusoft.modules.bookmanage.web;

import com.tepusoft.common.dto.JqueryResult;
import com.tepusoft.common.persistence.Page;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.common.utils.excel.ExportExcel;
import com.tepusoft.common.web.BaseController;
import com.tepusoft.modules.bookmanage.entity.Book;
import com.tepusoft.modules.bookmanage.service.BookService;
import com.tepusoft.modules.gen.entity.GenTable;
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

/**
 * @author XuYunXuan
 * @ClassName: BookController
 * @Description:
 * @date 2017-12-05 15:49
 */
@Controller
@RequestMapping(value = "${adminPath}/book")
public class BookController extends BaseController {

    @Autowired
    private BookService bookService;

    @ModelAttribute
    public Book get(@RequestParam(required=false) String id){
        if (StringUtils.isNotBlank(id)){
            return bookService.get(id);
        }else{
            return new Book();
        }
    }

    @RequestMapping(value = "list")
    public String list(){
        return "modules/bookmanage/list";
    }

    @ResponseBody
    @RequestMapping(value = "dataList")
    public Page<Book> dataList(Book book, HttpServletRequest request, HttpServletResponse response){
        return bookService.findPage(new Page<Book>(request,response),book);
    }

    @RequestMapping(value = "form")
    public String form(Book book,Model model){
        model.addAttribute("book",book);
        return "modules/bookmanage/form";
    }

    @ResponseBody
    @RequestMapping(value = "save")
    public JqueryResult save(Book book){
        try {
            bookService.save(book);
            return new JqueryResult.Builder(SUCCESS_CODE).message("保存成功").build();
        } catch (Exception e) {
            logger.error("保存失败",e);
            return new JqueryResult.Builder(FAIL_CODE).message("保存失败").build();
        }
    }

    @ResponseBody
    @RequestMapping(value = "delete")
    public JqueryResult delete(Book book){
        try {
            bookService.delete(book);
            return new JqueryResult.Builder(SUCCESS_CODE).message("删除成功").build();
        } catch (Exception e) {
            logger.error("删除失败",e);
            return new JqueryResult.Builder(FAIL_CODE).message("删除失败").build();
        }
    }

    @RequestMapping(value = "export")
    public void export(Book book, HttpServletRequest request, HttpServletResponse response) throws Exception{
        try {
            List<Book> books = bookService.findList(book);
            new ExportExcel("图书信息",Book.class).setDataList(books).write(response,"图书信息导出.xlsx").dispose();
        } catch (Exception e) {
            logger.error("导出失败",e);
            throw e;
        }
    }



    public void test(){

    }
}
