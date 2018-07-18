package com.viewol.web.product.vo;

import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by lenovo on 2018/7/17.
 */
@ApiModel
public class ProductModuleVO extends Response{


    @ApiModelProperty(value = "产品的列表")
    private List<ProductVO> result;



    public List<ProductVO> getList() {
        return result;
    }

    public void setList(List<ProductVO> result) {
        this.result = result;
    }
}
