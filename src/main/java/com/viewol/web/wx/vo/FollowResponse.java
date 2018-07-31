package com.viewol.web.wx.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class FollowResponse extends Response {

    @ApiModelProperty(value = "用户是否已关注公众号，true-已关注；false-未关注")
    private boolean result;

    public FollowResponse() {
    }

    public FollowResponse(String status, String message) {
        super(status, message);
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this);
    }
}
