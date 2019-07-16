package com.viewol.web.company.vo;

import com.viewol.pojo.CompanyShow;
import com.viewol.pojo.UserInteract;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by lenovo on 2018/7/18.
 */
@ApiModel
public class CompanyModuleVO extends Response {

    @ApiModelProperty(value = "展商详细")
    private CompanyVO result;

    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "0 未收藏  1 已收藏")
    private int collection;

    @ApiModelProperty(value = "0 未点赞  1 已点赞")
    private int isPraise;

    @ApiModelProperty(value = "展商秀，如果为空或者空字符串说明还没有展商秀")
    private CompanyShow showInfo;

    @ApiModelProperty(value = "围观列表")
    private List<UserInteract>  see;

    @ApiModelProperty(value = "点赞列表")
    private List<UserInteract>  praise;

    @ApiModelProperty(value = "评论列表")
    private List<UserInteract>  comment;

    public CompanyVO getResult() {
        return result;
    }

    public void setResult(CompanyVO result) {
        this.result = result;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public CompanyShow getShowInfo() {
        return showInfo;
    }

    public void setShowInfo(CompanyShow showInfo) {
        this.showInfo = showInfo;
    }
}
