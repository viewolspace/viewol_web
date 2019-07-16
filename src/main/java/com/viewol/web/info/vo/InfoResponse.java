package com.viewol.web.info.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class InfoResponse extends Response {

    private List<InfoVO> result;

    public InfoResponse() {}

    public InfoResponse(String status, String message) {
        super(status, message);
    }


    public List<InfoVO> getResult() {
        return result;
    }

    public void setResult(List<InfoVO> result) {
        this.result = result;
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public class InfoVO{
        @ApiModelProperty(value = "新闻ID")
        private int id;

        @ApiModelProperty(value = "标题")
        private String title;

        @ApiModelProperty(value = "摘要")
        private String summary;

        @ApiModelProperty(value = "发布日期")
        private String pubTime;

        @ApiModelProperty(value = "图片地址")
        private String picUrl;

        @ApiModelProperty(value = "正文地址，正文为空有效")
        private String contentUrl;

        @ApiModelProperty(value = "正文内容")
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getPubTime() {
            return pubTime;
        }

        public void setPubTime(String pubTime) {
            this.pubTime = pubTime;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public String getContentUrl() {
            return contentUrl;
        }

        public void setContentUrl(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
