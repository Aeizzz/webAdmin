package com.tepusoft.modules.demo.entity;

import com.google.common.collect.Lists;
import com.tepusoft.common.persistence.DataEntity;
import com.tepusoft.modules.gen.entity.GenTable;
import com.tepusoft.modules.gen.entity.GenTableColumn;

import java.util.List;

/**
 * Created by liying on 2017/6/20.
 */
public class DesginTable extends DataEntity<DesginTable> {
    private String Id;
    private String isCheckbox;//是否带checkbox
    private String isDbsynch;//同步数据库状态
    private String isPagination;//是否分页
    private String isTree;//是否是树
    private String formType;//表类型:单表、主表、附表
    private String formVersion;//表单版本号
    private String querymode;//查询模式
    private String tableName;//表名
//    private String treeParentidFieldname;//树形表单父id
//    private String treeIdFieldname;//树表主键字段
    private String formCategory;//表单分类
    private List<DesginField>  fieldList = Lists.newArrayList();

    public List<DesginField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<DesginField> fieldList) {
        this.fieldList = fieldList;
    }


    public DesginTable() {
    }

    public DesginTable(String id) {
        super(id);

    }

    @Override
    public String getId() {
        return Id;
    }

    @Override
    public void setId(String id) {
        Id = id;
    }

    public String getIsCheckbox() {
        return isCheckbox;
    }

    public void setIsCheckbox(String isCheckbox) {
        this.isCheckbox = isCheckbox;
    }

    public String getIsDbsynch() {
        return isDbsynch;
    }

    public void setIsDbsynch(String isDbsynch) {
        this.isDbsynch = isDbsynch;
    }

    public String getIsPagination() {
        return isPagination;
    }

    public void setIsPagination(String isPagination) {
        this.isPagination = isPagination;
    }

    public String getIsTree() {
        return isTree;
    }

    public void setIsTree(String isTree) {
        this.isTree = isTree;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getFormVersion() {
        return formVersion;
    }

    public void setFormVersion(String formVersion) {
        this.formVersion = formVersion;
    }

    public String getQuerymode() {
        return querymode;
    }

    public void setQuerymode(String querymode) {
        this.querymode = querymode;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

//    public String getTreeParentidFieldname() {
//        return treeParentidFieldname;
//    }
//
//    public void setTreeParentidFieldname(String treeParentidFieldname) {
//        this.treeParentidFieldname = treeParentidFieldname;
//    }
//
//    public String getTreeIdFieldname() {
//        return treeIdFieldname;
//    }
//
//    public void setTreeIdFieldname(String treeIdFieldname) {
//        this.treeIdFieldname = treeIdFieldname;
//    }

    public String getFormCategory() {
        return formCategory;
    }

    public void setFormCategory(String formCategory) {
        this.formCategory = formCategory;
    }
}
