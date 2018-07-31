package com.viewol.web.wx.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class WxUserResponse extends Response {

    @ApiModelProperty(value = "微信用户信息")
    private WxUser result;

    public WxUserResponse() {
    }

    public WxUserResponse(String status, String message) {
        super(status, message);
    }

    public WxUser getResult() {
        return result;
    }

    public void setResult(WxUser result) {
        this.result = result;
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public class WxUser{
        @ApiModelProperty(value = "用户的唯一标识")
        private String openId;

        @ApiModelProperty(value = "只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。")
        private String unionId;

        @ApiModelProperty(value = "用户昵称")
        private String nickname;

        @ApiModelProperty(value = "性别描述")
        private String sexDesc;

        @ApiModelProperty(value = "用户的性别，值为1时是男性，值为2时是女性，值为0时是未知")
        private Integer sex;

        @ApiModelProperty(value = "用户的语言，简体中文为zh_CN")
        private String language;

        @ApiModelProperty(value = "普通用户个人资料填写的城市")
        private String city;

        @ApiModelProperty(value = "用户个人资料填写的省份")
        private String province;

        @ApiModelProperty(value = "国家，如中国为CN")
        private String country;

        @ApiModelProperty(value = "用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。")
        private String headImgUrl;

        @ApiModelProperty(value = "用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息。")
        private Boolean subscribe;

        @ApiModelProperty(value = "用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间")
        private Long subscribeTime;

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getUnionId() {
            return unionId;
        }

        public void setUnionId(String unionId) {
            this.unionId = unionId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getSexDesc() {
            return sexDesc;
        }

        public void setSexDesc(String sexDesc) {
            this.sexDesc = sexDesc;
        }

        public Integer getSex() {
            return sex;
        }

        public void setSex(Integer sex) {
            this.sex = sex;
        }

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public Boolean getSubscribe() {
            return subscribe;
        }

        public void setSubscribe(Boolean subscribe) {
            this.subscribe = subscribe;
        }

        public Long getSubscribeTime() {
            return subscribeTime;
        }

        public void setSubscribeTime(Long subscribeTime) {
            this.subscribeTime = subscribeTime;
        }
    }
}
