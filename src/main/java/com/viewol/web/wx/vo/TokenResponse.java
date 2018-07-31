package com.viewol.web.wx.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel
public class TokenResponse extends Response {

    @ApiModelProperty(value = "授权返回的信息")
    private Token result;

    public TokenResponse() {
    }

    public TokenResponse(String status, String message) {
        super(status, message);
    }

    public Token getResult() {
        return result;
    }

    public void setResult(Token result) {
        this.result = result;
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public class Token{
        @ApiModelProperty(value = "网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同")
        private String accessToken;

        @ApiModelProperty(value = "access_token接口调用凭证超时时间，单位（秒）")
        private int expiresIn = -1;

        @ApiModelProperty(value = "用户刷新access_token")
        private String refreshToken;

        @ApiModelProperty(value = "用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID")
        private String openId;

        @ApiModelProperty(value = "用户授权的作用域，使用逗号（,）分隔")
        private String scope;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }
    }
}
