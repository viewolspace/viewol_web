package com.viewol.web.ucard.vo;

import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lenovo on 2018/8/14.
 */
public class PhoneRandVO extends Response {

    @ApiModelProperty(value = "验证码")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
