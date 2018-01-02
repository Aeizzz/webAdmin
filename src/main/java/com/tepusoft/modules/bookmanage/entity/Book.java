package com.tepusoft.modules.bookmanage.entity;

import com.tepusoft.common.persistence.DataEntity;
import com.tepusoft.common.utils.excel.annotation.ExcelField;

/**
 * @author XuYunXuan
 * @ClassName: Book
 * @Description:
 * @date 2017-12-05 15:49
 */
public class Book extends DataEntity<Book> {

    private String isbn;
    private String name;
    private String author;

    @ExcelField(title = "ISBN",sort = 0)
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    @ExcelField(title = "书名",sort = 1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @ExcelField(title = "作者",sort = 2)
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
