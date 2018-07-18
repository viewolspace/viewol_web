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
}
