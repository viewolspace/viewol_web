package com.viewol.schedule.vo;

import com.alibaba.fastjson.JSON;
import com.viewol.common.Response;
import com.viewol.pojo.Schedule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel
public class RecommendScheduleResponse extends Response {

	@ApiModelProperty(value = "日程列表")
	private List<Schedule> result;


	public RecommendScheduleResponse() {}

	public RecommendScheduleResponse(String status, String message) {
		super(status, message);
	}

	public List<Schedule> getResult() {
		return result;
	}

	public void setResult(List<Schedule> result) {
		this.result = result;
	}

	@Override
	public String toJSONString() {
		return JSON.toJSONString(this);
	}
}
