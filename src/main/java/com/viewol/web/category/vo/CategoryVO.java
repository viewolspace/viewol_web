package com.viewol.web.category.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lenovo on 2018/7/18.
 */
public class CategoryVO {

    @ApiModelProperty(value = "分类ID")
    private String id;

    private String parentId;

    @ApiModelProperty(value = "类型 1展商 2产品")
    private Integer type; //1 展商分类  2 产品分类

    @ApiModelProperty(value = "排序")
    private Integer num;

    private String  logo;

    @ApiModelProperty(value = "名称")
    private String  name;

    private Date cTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }
}
