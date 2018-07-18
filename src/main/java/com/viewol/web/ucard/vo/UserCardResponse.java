package com.viewol.web.ucard.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel
public class UserCardResponse extends Response {

    @ApiModelProperty(value = "名片夹列表")
    private List<UserCardVO> result;

    public UserCardResponse() {
    }

    public UserCardResponse(String status, String message) {
        super(status, message);
    }

    public List<UserCardVO> getResult() {
        return result;
    }

    public void setResult(List<UserCardVO> result) {
        this.result = result;
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public class UserCardVO {
        @ApiModelProperty(value = "名片记录ID")
        private int id;

        @ApiModelProperty(value = "业务员ID")
        private int bUserId;

        @ApiModelProperty(value = "展商ID")
        private int companyId;

        @ApiModelProperty(value = "客户ID")
        private int fUserId;

        @ApiModelProperty(value = "创建时间")
        private Date createTime;

        //客户基本信息
        @ApiModelProperty(value = "客户姓名")
        private String fUserName;

        @ApiModelProperty(value = "客户手机号")
        private String fPhone;

        @ApiModelProperty(value = "客户公司")
        private String FCompany;

        @ApiModelProperty(value = "客户职位")
        private String fPosition;

        @ApiModelProperty(value = "客户邮箱")
        private String fEmail;

        @ApiModelProperty(value = "客户年龄")
        private int    FAge;

        @ApiModelProperty(value = "客户公司ID")
        private int FCompanyId;

        //展商业务员基本信息
        @ApiModelProperty(value = "业务员姓名")
        private String BUserName;

        @ApiModelProperty(value = "业务员手机号")
        private String BPhone;

        @ApiModelProperty(value = "业务员职位")
        private String BPosition;

        //展商基本信息
        @ApiModelProperty(value = "展商Logo")
        private String  logo;

        @ApiModelProperty(value = "展商全称")
        private String  name;

        @ApiModelProperty(value = "展商简称")
        private String  shortName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getbUserId() {
            return bUserId;
        }

        public void setbUserId(int bUserId) {
            this.bUserId = bUserId;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getfUserId() {
            return fUserId;
        }

        public void setfUserId(int fUserId) {
            this.fUserId = fUserId;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public String getfUserName() {
            return fUserName;
        }

        public void setfUserName(String fUserName) {
            this.fUserName = fUserName;
        }

        public String getfPhone() {
            return fPhone;
        }

        public void setfPhone(String fPhone) {
            this.fPhone = fPhone;
        }

        public String getFCompany() {
            return FCompany;
        }

        public void setFCompany(String FCompany) {
            this.FCompany = FCompany;
        }

        public String getfPosition() {
            return fPosition;
        }

        public void setfPosition(String fPosition) {
            this.fPosition = fPosition;
        }

        public String getfEmail() {
            return fEmail;
        }

        public void setfEmail(String fEmail) {
            this.fEmail = fEmail;
        }

        public int getFAge() {
            return FAge;
        }

        public void setFAge(int FAge) {
            this.FAge = FAge;
        }

        public int getFCompanyId() {
            return FCompanyId;
        }

        public void setFCompanyId(int FCompanyId) {
            this.FCompanyId = FCompanyId;
        }

        public String getBUserName() {
            return BUserName;
        }

        public void setBUserName(String BUserName) {
            this.BUserName = BUserName;
        }

        public String getBPhone() {
            return BPhone;
        }

        public void setBPhone(String BPhone) {
            this.BPhone = BPhone;
        }

        public String getBPosition() {
            return BPosition;
        }

        public void setBPosition(String BPosition) {
            this.BPosition = BPosition;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShortName() {
            return shortName;
        }

        public void setShortName(String shortName) {
            this.shortName = shortName;
        }
    }
}
