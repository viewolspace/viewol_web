package com.viewol.web.fuser.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel
public class UserDownloadResponse extends Response {

    @ApiModelProperty(value = "下载记录")
    private List<UserDownloadVO> result;

    public UserDownloadResponse() {
    }

    public UserDownloadResponse(String status, String message) {
        super(status, message);
    }

    public List<UserDownloadVO> getResult() {
        return result;
    }

    public void setResult(List<UserDownloadVO> result) {
        this.result = result;
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public class UserDownloadVO {
        @ApiModelProperty(value = "下载记录ID")
        private int id;

        @ApiModelProperty(value = "客户ID")
        private int userId;

        @ApiModelProperty(value = "产品ID")
        private int productId;

        @ApiModelProperty(value = "产品名称")
        private String productName;

        @ApiModelProperty(value = "下载地址")
        private String pdfUrl;

        @ApiModelProperty(value = "创建时间")
        private Date createTime;

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

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getPdfUrl() {
            return pdfUrl;
        }

        public void setPdfUrl(String pdfUrl) {
            this.pdfUrl = pdfUrl;
        }

        public String getImageView() {
            return imageView;
        }

        public void setImageView(String imageView) {
            this.imageView = imageView;
        }
    }
}
