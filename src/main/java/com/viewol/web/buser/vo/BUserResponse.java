package com.viewol.web.buser.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel
public class BUserResponse extends Response {

    @ApiModelProperty(value = "个人详情")
    private BUserVO result;

    public BUserResponse() {
    }

    public BUserResponse(String status, String message) {
        super(status, message);
    }

    public BUserVO getResult() {
        return result;
    }

    public void setResult(BUserVO result) {
        this.result = result;
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    @ApiModel
    public class BUserVO {
        @ApiModelProperty(value = "用户ID")
        private  int userId;

        @ApiModelProperty(value = "用户姓名")
        private String userName;

        @ApiModelProperty(value = "用户电话")
        private String phone;

        private int companyId;

        @ApiModelProperty(value = "状态 0 审核中 1 通过 -1待审")
        private int status;

        @ApiModelProperty(value = "职位")
        private String position;

        private Date cTime;

        private Date mTime;

        private String openId;

        private String uuid;

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

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public Date getcTime() {
            return cTime;
        }

        public void setcTime(Date cTime) {
            this.cTime = cTime;
        }

        public Date getmTime() {
            return mTime;
        }

        public void setmTime(Date mTime) {
            this.mTime = mTime;
        }

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getHeadImgUrl() {
            return headImgUrl;
        }

        public void setHeadImgUrl(String headImgUrl) {
            this.headImgUrl = headImgUrl;
        }
    }
}
