package com.viewol.web.product.vo;

import com.viewol.web.company.vo.CompanyVO;
import io.swagger.annotations.ApiModelProperty;

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
}
