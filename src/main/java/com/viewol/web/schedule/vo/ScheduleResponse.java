package com.viewol.web.schedule.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel
public class ScheduleResponse extends Response {

    @ApiModelProperty(value = "活动详情")
    private ScheduleDetailVO result;

    public ScheduleResponse() {
    }

    public ScheduleResponse(String status, String message) {
        super(status, message);
    }

    public ScheduleDetailVO getResult() {
        return result;
    }

    public void setResult(ScheduleDetailVO result) {
        this.result = result;
    }

    @Override
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public class ScheduleDetailVO{
        @ApiModelProperty(value = "活动ID")
        private Integer  id ;

        @ApiModelProperty(value = "展商ID")
        private Integer companyId;

        @ApiModelProperty(value = "展商名称")
        private String companyName;

        @ApiModelProperty(value = "日程标题")
        private String title;

        @ApiModelProperty(value = "创建时间")
        private Date createTime;

        @ApiModelProperty(value = "发布人类型，0-主办方；1-展商")
        private Integer type;//0 主办方 1 展商

        @ApiModelProperty(value = "审核状态，0：待审，-1：打回，1：审核通过")
        private Integer status;

        @ApiModelProperty(value = "活动开始时间")
        private Date startTime;

        @ApiModelProperty(value = "活动结束时间")
        private Date endTime;

        @ApiModelProperty(value = "活动地点")
        private String place;

        @ApiModelProperty(value = "推荐类型：1-置顶活动，2-推荐活动")
        private Integer vtype;

        @ApiModelProperty(value = "序列")
        private Long seq;

        @ApiModelProperty(value = "活动详情")
        private String contentView;

        @ApiModelProperty(value = "推荐位开始时间")
        private Date recommendSTime;

        @ApiModelProperty(value = "推荐位结束时间")
        private Date recommendETime;

        @ApiModelProperty(value = "是否允许报名，0-不允许报名，1-可以报名，2-已经报名 3-活动已经结束")
        private int applyStatus;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getCompanyId() {
            return companyId;
        }

        public void setCompanyId(Integer companyId) {
            this.companyId = companyId;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public void setEndTime(Date endTime) {
            this.endTime = endTime;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public Integer getVtype() {
            return vtype;
        }

        public void setVtype(Integer vtype) {
            this.vtype = vtype;
        }

        public Long getSeq() {
            return seq;
        }

        public void setSeq(Long seq) {
            this.seq = seq;
        }

        public String getContentView() {
            return contentView;
        }

        public void setContentView(String contentView) {
            this.contentView = contentView;
        }

        public Date getRecommendSTime() {
            return recommendSTime;
        }

        public void setRecommendSTime(Date recommendSTime) {
            this.recommendSTime = recommendSTime;
        }

        public Date getRecommendETime() {
            return recommendETime;
        }

        public void setRecommendETime(Date recommendETime) {
            this.recommendETime = recommendETime;
        }

        public int getApplyStatus() {
            return applyStatus;
        }

        public void setApplyStatus(int applyStatus) {
            this.applyStatus = applyStatus;
        }
    }

}
