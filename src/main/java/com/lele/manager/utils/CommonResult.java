package com.lele.manager.utils;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

public class CommonResult implements Serializable {
	
	private static final long serialVersionUID = -5250242575598415813L;
	
	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";
	
	private String result;
	
	private String errCode;

	public CommonResult() {
		this.result = SUCCESS;
		this.errCode = "0";
	}
	
	public CommonResult(String result, String errCode) {
		this.result = result;
		this.errCode = errCode;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	
	public boolean isSuccess() {
		return this.result.equals(SUCCESS) ? true:false;
	}
	
	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
