package com.tepusoft.modules.book.entity;

import com.tepusoft.common.persistence.DataEntity;
import com.tepusoft.common.utils.excel.annotation.ExcelField;


/**
 * 图书
 */
public class LHLBook extends DataEntity<LHLBook> {
    private String name;
    private String author;
    private String isbn;
    private String category;
    private String publishing;

    @ExcelField(title = "书名", sort = 1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ExcelField(title = "作者", sort = 2)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @ExcelField(title = "ISBN", sort = 0)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @ExcelField(title = "分类", sort = 3)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @ExcelField(title = "出版社", sort = 4)
    public String getPublishing() {
        return publishing;
    }

    public void setPublishing(String publishing) {
        this.publishing = publishing;
    }
}
