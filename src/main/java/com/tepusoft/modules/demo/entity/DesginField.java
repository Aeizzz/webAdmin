package com.tepusoft.modules.demo.entity;

import com.google.common.collect.Lists;
import com.tepusoft.common.persistence.DataEntity;
import com.tepusoft.common.utils.StringUtils;
import com.tepusoft.modules.gen.entity.GenTable;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * Created by liying on 2017/6/20.
 */
public class DesginField extends DataEntity<DesginField> {
//    private static final long serialVersionUID = 1L;

    private DesginTable desginTable;	// 归属表
//    private String name; 		// 列名
    private String content;	// 字段备注
    private String dictField;//字典code
    private String dictTable;//字典表
    private String dictText;//字典text
    private String  fieldDefault;//字典默认值
    private  String  fieldHref;//跳转路径
    private int  fieldLength;//表单控件长度
    private String fieldName;//表单长度
    private String  fieldValidType;//表单字段校验规则
    private String fieldMustInput;//字段是否为必填
    private String isNull;		// 是否可为空（1：可为空；0：不为空）
    private String isKey;		// 是否主键（1：主键）
    private String isQuery;		// 是否查询字段（1：查询字段）
    private String isShow; //是否显示
    private String iShowList;//列表是否显示
    private int length;//数据库字段长度
    private String mainField;//外表主键字段长度
    private String mainTable;//外键主表名
    private  String oldFieldName;//原字段名
    private int orderNum ;// 原排列序号
    private int pointLength;//小数点
    private String queryMode;//查询模式
    private String showType;	// 字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择
    private String Type;	// 字段类型
    private String tableId;	//

    public DesginField() {
        super();
    }


    public DesginField (String id){
        super(id);
    }

    public DesginField(DesginTable desginTable){
        this.desginTable = desginTable;
    }

    public DesginTable getDesginTable() {
        return desginTable;
    }
    public void setDesginTable(DesginTable desginTable) {
        this.desginTable = desginTable;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDictField() {
        return dictField;
    }

    public void setDictField(String dictField) {
        this.dictField = dictField;
    }

    public String getDictTable() {
        return dictTable;
    }

    public void setDictTable(String dictTable) {
        this.dictTable = dictTable;
    }

    public String getDictText() {
        return dictText;
    }

    public void setDictText(String dictText) {
        this.dictText = dictText;
    }

    public String getFieldDefault() {
        return fieldDefault;
    }

    public void setFieldDefault(String fieldDefault) {
        this.fieldDefault = fieldDefault;
    }

    public String getFieldHref() {
        return fieldHref;
    }

    public void setFieldHref(String fieldHref) {
        this.fieldHref = fieldHref;
    }

    public int getFieldLength() {
        return fieldLength;
    }

    public void setFieldLength(int fieldLength) {
        this.fieldLength = fieldLength;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValidType() {
        return fieldValidType;
    }

    public void setFieldValidType(String fieldValidType) {
        this.fieldValidType = fieldValidType;
    }

    public String getFieldMustInput() {
        return fieldMustInput;
    }

    public void setFieldMustInput(String fieldMustInput) {
        this.fieldMustInput = fieldMustInput;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getIsKey() {
        return isKey;
    }

    public void setIsKey(String isKey) {
        this.isKey = isKey;
    }

    public String getIsQuery() {
        return isQuery;
    }

    public void setIsQuery(String isQuery) {
        this.isQuery = isQuery;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getiShowList() {
        return iShowList;
    }

    public void setiShowList(String iShowList) {
        this.iShowList = iShowList;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getMainField() {
        return mainField;
    }

    public void setMainField(String mainField) {
        this.mainField = mainField;
    }

    public String getMainTable() {
        return mainTable;
    }

    public void setMainTable(String mainTable) {
        this.mainTable = mainTable;
    }

    public String getOldFieldName() {
        return oldFieldName;
    }

    public void setOldFieldName(String oldFieldName) {
        this.oldFieldName = oldFieldName;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getPointLength() {
        return pointLength;
    }

    public void setPointLength(int pointLength) {
        this.pointLength = pointLength;
    }

    public String getQueryMode() {
        return queryMode;
    }

    public void setQueryMode(String queryMode) {
        this.queryMode = queryMode;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

/**
     * 获取列名和说明
     * @return
     */
//    public String getNameAndComments() {
//        return getName() + (comments == null ? "" : "  :  " + comments);
//    }

    /**
     * 获取字符串长度
     * @return

    public String getDataLength(){
        String[] ss = StringUtils.split(StringUtils.substringBetween(getJdbcType(), "(", ")"), ",");
        if (ss != null && ss.length == 1){// && "String".equals(getJavaType())){
            return ss[0];
        }
        return "0";
    }
     */
    /**
     * 获取简写Java类型
     * @return

    public String getSimpleJavaType(){
        if ("This".equals(getJavaType())){
            return StringUtils.capitalize(genTable.getClassName());
        }
        return StringUtils.indexOf(getJavaType(), ".") != -1
                ? StringUtils.substringAfterLast(getJavaType(), ".")
                : getJavaType();
    }
     */
    /**
     * 获取简写Java字段
     * @return

    public String getSimpleJavaField(){
        return StringUtils.substringBefore(getJavaField(), ".");
    }
     */
    /**
     * 获取Java字段，如果是对象，则获取对象.附加属性1
     * @return

    public String getJavaFieldId(){
        return StringUtils.substringBefore(getJavaField(), "|");
    }
     */
    /**
     * 获取Java字段，如果是对象，则获取对象.附加属性2
     * @return

    public String getJavaFieldName(){
        String[][] ss = getJavaFieldAttrs();
        return ss.length>0 ? getSimpleJavaField()+"."+ss[0][0] : "";
    }
     */
    /**
     * 获取Java字段，所有属性名
     * @return

    public String[][] getJavaFieldAttrs(){
        String[] ss = StringUtils.split(StringUtils.substringAfter(getJavaField(), "|"), "|");
        String[][] sss = new String[ss.length][2];
        if (ss!=null){
            for (int i=0; i<ss.length; i++){
                sss[i][0] = ss[i];
                sss[i][1] = StringUtils.toUnderScoreCase(ss[i]);
            }
        }
        return sss;
    }
     */
    /**
     * 获取列注解列表
     * @return

    public List<String> getAnnotationList(){
        List<String> list = Lists.newArrayList();
        // 导入Jackson注解
        if ("This".equals(getJavaType())){
            list.add("com.fasterxml.jackson.annotation.JsonBackReference");
        }
        if ("java.util.Date".equals(getJavaType())){
            list.add("com.fasterxml.jackson.annotation.JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")");
        }
        // 导入JSR303验证依赖包
        if (!"1".equals(getIsNull()) && !"String".equals(getJavaType())){
            list.add("javax.validation.constraints.NotNull(message=\""+getComments()+"不能为空\")");
        }
        else if (!"1".equals(getIsNull()) && "String".equals(getJavaType()) && !"0".equals(getDataLength())){
            list.add("org.hibernate.validator.constraints.Length(min=1, max="+getDataLength()
                    +", message=\""+getComments()+"长度必须介于 1 和 "+getDataLength()+" 之间\")");
        }
        else if ("String".equals(getJavaType()) && !"0".equals(getDataLength())){
            list.add("org.hibernate.validator.constraints.Length(min=0, max="+getDataLength()
                    +", message=\""+getComments()+"长度必须介于 0 和 "+getDataLength()+" 之间\")");
        }
        return list;
    }
     */
    /**
     * 获取简写列注解列表
     * @return

    public List<String> getSimpleAnnotationList(){
        List<String> list = Lists.newArrayList();
        for (String ann : getAnnotationList()){
            list.add(StringUtils.substringAfterLast(ann, "."));
        }
        return list;
    }
     */
    /**
     * 是否是基类字段
     * @return

    public Boolean getIsNotBaseField(){
        return !StringUtils.equals(getSimpleJavaField(), "id")
                && !StringUtils.equals(getSimpleJavaField(), "remarks")
                && !StringUtils.equals(getSimpleJavaField(), "createBy")
                && !StringUtils.equals(getSimpleJavaField(), "createDate")
                && !StringUtils.equals(getSimpleJavaField(), "updateBy")
                && !StringUtils.equals(getSimpleJavaField(), "updateDate")
                && !StringUtils.equals(getSimpleJavaField(), "delFlag");
    }
     */
}
