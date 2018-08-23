package com.viewol.web.wx.vo;

import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by lenovo on 2018/8/22.
 */
@ApiModel
public class WxJsapiSignatureVO extends Response {


    @ApiModelProperty(value = "授权信息")
    private WxJsapiSignatureMo result;

    public WxJsapiSignatureMo getResult() {
        return result;
    }

    public void setResult(WxJsapiSignatureMo result) {
        this.result = result;
    }

    class WxJsapiSignatureMo{
        @ApiModelProperty(value = "appId")
        private String appId;

        @ApiModelProperty(value = "nonceStr")
        private String nonceStr;

        @ApiModelProperty(value = "timestamp")
        private long timestamp;

        @ApiModelProperty(value = "url")
        private String url;

        @ApiModelProperty(value = "signature")
        private String signature;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }
    }

}
