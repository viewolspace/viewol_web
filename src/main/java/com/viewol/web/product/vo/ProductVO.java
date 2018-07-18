package com.viewol.web.product.vo;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by lenovo on 2018/7/17.
 */
public class ProductVO {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "所属公司的id")
    private Integer companyId;

    @ApiModelProperty(value = "分类的id")
    private String categoryId;

    @ApiModelProperty(value = "状态 0 上架  1下架")
    private Integer status; //0 上架  1 下架

    @ApiModelProperty(value = "产品名称")
    private String name;

    private String image;

    @ApiModelProperty(value = "产品介绍 图文  前端不使用")
    private String content;


    private String pdfUrl;

    @ApiModelProperty(value = "pdf名称")
    private String pdfName;
    private Integer isRecommend;
    private Integer recommendNum;
    private Date cTime;
    private Date mTime;

    @ApiModelProperty(value = "翻页的序列")
    private long seq;

    @ApiModelProperty(value = "图片真实地址 前端使用字段")
    private String imageView;

    @ApiModelProperty(value = "图文内容 前端使用字段")
    private String contentView;

    @ApiModelProperty(value = "pdf下载地址 前端使用字段")
    private String pdfUrlView;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Integer getRecommendNum() {
        return recommendNum;
    }

    public void setRecommendNum(Integer recommendNum) {
        this.recommendNum = recommendNum;
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

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getContentView() {
        return contentView;
    }

    public void setContentView(String contentView) {
        this.contentView = contentView;
    }

    public String getPdfUrlView() {
        return pdfUrlView;
    }

    public void setPdfUrlView(String pdfUrlView) {
        this.pdfUrlView = pdfUrlView;
    }
}
