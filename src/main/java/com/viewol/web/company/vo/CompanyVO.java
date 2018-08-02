package com.viewol.web.company.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lenovo on 2018/7/18.
 */
public class CompanyVO {
    @ApiModelProperty(value = "展商id")
    private Integer id;

    @ApiModelProperty(value = "展商名称")
    private String name;

    @ApiModelProperty(value = "展商简称")
    private String shortName; //简称
    private String logo;
    private String banner; //形象图
    private String image;  //列表图

    @ApiModelProperty(value = "展商位置")
    private String place;  //展馆的位置
    private String placeSvg;
    private Integer productNum;
    private Integer canApply; //1 允许申请活动  0 不允许
    private Integer isRecommend; //是否推荐展商 1 推荐 0 非推荐
    private Integer recommendNum; //推荐位置
    private String content;
    private long cTime;
    private long mTime;
    private long seq;

    @ApiModelProperty(value = "展商logo")
    private String logoView;

    @ApiModelProperty(value = "展商banner")
    private String bannerView;

    private String imageView;

    @ApiModelProperty(value = "展商内容")
    private String contentView;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPlaceSvg() {
        return placeSvg;
    }

    public void setPlaceSvg(String placeSvg) {
        this.placeSvg = placeSvg;
    }

    public Integer getProductNum() {
        return productNum;
    }

    public void setProductNum(Integer productNum) {
        this.productNum = productNum;
    }

    public Integer getCanApply() {
        return canApply;
    }

    public void setCanApply(Integer canApply) {
        this.canApply = canApply;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getcTime() {
        return cTime;
    }

    public void setcTime(long cTime) {
        this.cTime = cTime;
    }

    public long getmTime() {
        return mTime;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getLogoView() {
        return logoView;
    }

    public void setLogoView(String logoView) {
        this.logoView = logoView;
    }

    public String getBannerView() {
        return bannerView;
    }

    public void setBannerView(String bannerView) {
        this.bannerView = bannerView;
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
}
