package com.viewol.web.company.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ErCodeResponse extends Response {

    @ApiModelProperty(value = "小程序码，客户端需转成图片显示")
    private String ercode;

    public String getErcode() {
        return ercode;
    }

    public void setErcode(String ercode) {
        this.ercode = ercode;
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

}
