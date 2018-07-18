package com.viewol.web.product.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lenovo on 2018/7/18.
 */
public class ProductRootVO {
    @ApiModelProperty(value = "产品")
    private ProductVO result;

    public ProductVO getResult() {
        return result;
    }

    public void setResult(ProductVO result) {
        this.result = result;
    }
}
