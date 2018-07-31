package com.viewol.web.company.vo;

import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lenovo on 2018/7/18.
 */
@ApiModel
public class CompanyModuleVO extends Response {

    @ApiModelProperty(value = "展商详细")
    private CompanyVO result;

    @ApiModelProperty(value = "分类id")
    private String categoryId;

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
}
