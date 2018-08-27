package com.viewol.web.wx.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class BuserLoginResponse extends Response {

    @ApiModelProperty(value = "登录成功返回的信息")
    private UserInfo result;

    public BuserLoginResponse() {
    }

    public BuserLoginResponse(String status, String message) {
        super(status, message);
    }

    public UserInfo getResult() {
        return result;
    }

    public void setResult(UserInfo result) {
        this.result = result;
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public class UserInfo {
        @ApiModelProperty(value = "用户ID，调用其他接口，需要在请求头传入userId")
        private int userId;

        @ApiModelProperty(value = "用户姓名")
        private String userName;

        @ApiModelProperty(value = "手机号")
        private String phone;

        @ApiModelProperty(value = "职位")
        private String position;

        @ApiModelProperty(value = "头像")
        private String headImgUrl;

        @ApiModelProperty(value = "展商ID")
        private int companyId;

        @ApiModelProperty(value = "展商Logo")
        private String companyLogo;

        @ApiModelProperty(value = "展商名称")
        private String companyName;

        @ApiModelProperty(value = "业务员状态，0:待审；1:可使用；-1:打回")
        private int status;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getCompanyLogo() {
            return companyLogo;
        }

        public void setCompanyLogo(String companyLogo) {
            this.companyLogo = companyLogo;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
