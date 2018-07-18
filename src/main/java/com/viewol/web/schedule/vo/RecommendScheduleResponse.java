package com.viewol.web.schedule.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.web.common.Response;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel
public class RecommendScheduleResponse extends Response {

	@ApiModelProperty(value = "日程列表")
	private List<ScheduleVO> result;


	public RecommendScheduleResponse() {}

	public RecommendScheduleResponse(String status, String message) {
		super(status, message);
	}

	public List<ScheduleVO> getResult() {
		return result;
	}

	public void setResult(List<ScheduleVO> result) {
		this.result = result;
	}

	@Override
	public String toJSONString() {
		return JSON.toJSONString(this);
	}

	public class ScheduleVO{
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
	}
}

