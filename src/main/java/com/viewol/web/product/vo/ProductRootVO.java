package com.viewol.web.product.vo;

import com.viewol.pojo.UserInteract;
import com.viewol.web.company.vo.CompanyVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by lenovo on 2018/7/18.
 */
public class ProductRootVO {
    @ApiModelProperty(value = "产品")
    private ProductVO result;

    @ApiModelProperty(value="展商信息")
    private CompanyVO company;

    @ApiModelProperty(value = "0 未收藏  1 已收藏")
    private int collection;

    @ApiModelProperty(value = "围观列表")
    private List<UserInteract> see;

    @ApiModelProperty(value = "点赞列表")
    private List<UserInteract>  praise;

    @ApiModelProperty(value = "评论列表")
    private List<UserInteract>  comment;

    @ApiModelProperty(value = "0 未点赞  1 已点赞")
    private int isPraise;

    public int getCollection() {
        return collection;
    }

    public void setCollection(int collection) {
        this.collection = collection;
    }

    public ProductVO getResult() {
        return result;
    }

    public void setResult(ProductVO result) {
        this.result = result;
    }

    public CompanyVO getCompany() {
        return company;
    }

    public void setCompany(CompanyVO company) {
        this.company = company;
    }

    public List<UserInteract> getSee() {
        return see;
    }

    public void setSee(List<UserInteract> see) {
        this.see = see;
    }

    public List<UserInteract> getPraise() {
        return praise;
    }

    public void setPraise(List<UserInteract> praise) {
        this.praise = praise;
    }

    public List<UserInteract> getComment() {
        return comment;
    }

    public void setComment(List<UserInteract> comment) {
        this.comment = comment;
    }

    public int getIsPraise() {
        return isPraise;
    }

    public void setIsPraise(int isPraise) {
        this.isPraise = isPraise;
    }
}
