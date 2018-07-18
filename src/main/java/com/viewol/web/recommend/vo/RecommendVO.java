package com.viewol.web.recommend.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lenovo on 2018/7/18.
 */
public class RecommendVO {
    private int id;
    @ApiModelProperty(value = "1 展商  2产品")
    private int type;
    @ApiModelProperty(value = "产品或者展商的id")
    private int thirdId;

    @ApiModelProperty(value = "产品或者展商的名称")
    private String name;
    private String image;
    private String categoryId;
    private Date cTime;
    private Date mTime;

    @ApiModelProperty(value = "产品或者展商的图片")
    private String imageView;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getThirdId() {
        return thirdId;
    }

    public void setThirdId(int thirdId) {
        this.thirdId = thirdId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Date getmTime() {
        return mTime;
    }

    public void setmTime(Date mTime) {
        this.mTime = mTime;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }
}
