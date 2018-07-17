package com.viewol.common;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@ApiModel(description="服务端响应基类")
public class Response implements IResponse {
	private String status;
	private String message;

	public Response() {
	}

	public Response(String status, String message) {
		this.status = status;
		this.message = message;
	}

	@ApiModelProperty(value = "状态码", example = "0000")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@ApiModelProperty(value = "提示内容", example = "请求成功")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toJSONString(){
		return JSON.toJSONString(this);
	}
}
