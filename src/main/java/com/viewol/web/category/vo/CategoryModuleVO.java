package com.viewol.web.category.vo;

import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lenovo on 2018/7/18.
 */
public class CategoryModuleVO extends Response {

    @ApiModelProperty(value = "分类列表")
    private CategoryVO result;

    public CategoryVO getResult() {
        return result;
    }

    public void setResult(CategoryVO result) {
        this.result = result;
    }
}
