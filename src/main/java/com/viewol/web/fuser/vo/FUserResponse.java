package com.viewol.web.fuser.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel
public class FUserResponse extends Response {

    @ApiModelProperty(value = "个人详情")
    private FUserVO result;

    public FUserResponse() {
    }

    public FUserResponse(String status, String message) {
        super(status, message);
    }

    public FUserVO getResult() {
        return result;
    }

    public void setResult(FUserVO result) {
        this.result = result;
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    @ApiModel
    public class FUserVO {
        @ApiModelProperty(value = "用户ID")
        private int userId;

        @ApiModelProperty(value = "姓名")
        private String userName;

        @ApiModelProperty(value = "手机号")
        private String phone;

        @ApiModelProperty(value = "公司")
        private String company;

        @ApiModelProperty(value = "职位")
        private String position;

        @ApiModelProperty(value = "邮箱")
        private String email;

        @ApiModelProperty(value = "年龄")
        private int age;

        @ApiModelProperty(value = "创建时间")
        private Date createTime;

        @ApiModelProperty(value = "展商ID")
        private int companyId;

        @ApiModelProperty(value = "用户头像")
        private String headImgUrl;

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

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }
    }
}
