package com.tepusoft.modules.book.entity;

import com.tepusoft.common.persistence.DataEntity;

/**
 * 图书分类
 */
public class LHLCategory extends DataEntity<LHLCategory> {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}