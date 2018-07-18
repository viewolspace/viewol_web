package com.viewol.web.company.vo;

import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by lenovo on 2018/7/18.
 */
public class CompanyListVO extends Response {

    @ApiModelProperty(value = "展商列表")
    private List<CompanyVO> result;

    public List<CompanyVO> getResult() {
        return result;
    }

    public void setResult(List<CompanyVO> result) {
        this.result = result;
    }
}
