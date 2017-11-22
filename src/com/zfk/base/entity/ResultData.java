package com.zfk.base.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class ResultData<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 9014596529881964486L;
	private boolean success;
	private String message;
	private String code;
	private T data;

	public ResultData() {
	}

	public ResultData(boolean success) {
		this.success = success;
	}

	public ResultData(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public ResultData(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public ResultData(boolean success, String message, String code) {
		this.success = success;
		this.message = message;
		this.code = code;
	}

	public ResultData(boolean success, String message, String code, T data) {
		this.success = success;
		this.message = message;
		this.code = code;
		this.data = data;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getData() {
		return this.data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
