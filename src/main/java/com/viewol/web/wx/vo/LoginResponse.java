package com.viewol.web.wx.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class LoginResponse extends Response {

    @ApiModelProperty(value = "登录成功返回的信息")
    private UserInfo result;

    public LoginResponse() {
    }

    public LoginResponse(String status, String message) {
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

        @ApiModelProperty(value = "公司名称")
        private String company;

        @ApiModelProperty(value = "职位")
        private String position;

        @ApiModelProperty(value = "邮箱")
        private String email;

        @ApiModelProperty(value = "年龄")
        private int age;

        @ApiModelProperty(value = "头像")
        private String headImgUrl;

        @ApiModelProperty(value = "邀请展商ID")
        private int companyId;

        @ApiModelProperty(value = "会话session，登录成功之后，调用其他接口，需要在请求头传入该session")
        private String sessionId;

        @ApiModelProperty(value = "是否报名 0 没有报名 1已报名")
        private int userJoin;

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

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
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

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public int getUserJoin() {
            return userJoin;
        }

        public void setUserJoin(int userJoin) {
            this.userJoin = userJoin;
        }
    }
}
