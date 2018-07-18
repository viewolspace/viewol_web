package com.viewol.web.fuser.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel
public class UserBrowseResponse extends Response {

    @ApiModelProperty(value = "浏览列表")
    private List<UserBrowseVO> result;

    public UserBrowseResponse() {
    }

    public UserBrowseResponse(String status, String message) {
        super(status, message);
    }

    public List<UserBrowseVO> getResult() {
        return result;
    }

    public void setResult(List<UserBrowseVO> result) {
        this.result = result;
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public class UserBrowseVO {
        @ApiModelProperty(value = "浏览ID")
        private int id;

        @ApiModelProperty(value = "客户ID")
        private int userId;

        @ApiModelProperty(value = "类型，1-展商；2-产品")
        private int type;

        @ApiModelProperty(value = "展商ID（产品ID）")
        private int thirdId;

        @ApiModelProperty(value = "展商名称（产品名称）")
        private String name;

        @ApiModelProperty(value = "浏览次数")
        private int num;

        @ApiModelProperty(value = "创建时间")
        private Date createTime;

        @ApiModelProperty(value = "修改时间")
        private Date modifyTime;

        @ApiModelProperty(value = "展商图片(产品图片)")
        private String imageView;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getThirdId() {
            return thirdId;
        }

        public void setThirdId(int thirdId) {
            this.thirdId = thirdId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Date getModifyTime() {
            return modifyTime;
        }

        public void setModifyTime(Date modifyTime) {
            this.modifyTime = modifyTime;
        }

        public String getImageView() {
            return imageView;
        }

        public void setImageView(String imageView) {
            this.imageView = imageView;
        }
    }
}
