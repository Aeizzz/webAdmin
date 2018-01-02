package com.tepusoft.modules.sys.entity;

import com.tepusoft.common.persistence.DataEntity;

/**
 * Created by liying on 2017/7/28.
 */
public class Post extends DataEntity<Post> {
    private String id;//ID
    private String  familyId;//族ID
    private String classId;//类ID
    private String childClassId;//子类Id
    private String postName;//岗位名称
    private String depId;//部门ID
    private String departmentId;//科室
    private String postLevelId;//岗层
    private String depName;//部门名称
    private String departmentName;//科室名
    private String familyName;//族名称
    private String className;//类名称
    private String childClassName;//子类名
    private String levelName;//岗层名

    public Post() {
    }

    public Post(String id) {
        super(id);
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getChildClassId() {
        return childClassId;
    }

    public void setChildClassId(String childClassId) {
        this.childClassId = childClassId;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostLevelId() {
        return postLevelId;
    }

    public void setPostLevelId(String postLevelId) {
        this.postLevelId = postLevelId;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getChildClassName() {
        return childClassName;
    }

    public void setChildClassName(String childClassName) {
        this.childClassName = childClassName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getDepId() {
        return depId;
    }

    public void setDepId(String depId) {
        this.depId = depId;
    }
}
