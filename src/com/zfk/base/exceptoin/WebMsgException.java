package com.zfk.base.exceptoin;

import java.io.Serializable;

public class WebMsgException extends RuntimeException {
	private static final long serialVersionUID = -231256611059539946L;
	private int code;
	private String message;
	private String redirectUrl;
	private Serializable data;

	public WebMsgException(int code, String message, String redirectUrl) {
		this.code = code;
		this.message = message;
		this.redirectUrl = redirectUrl;
	}

	public WebMsgException(int code, String message, String redirectUrl, Serializable data) {
		this.code = code;
		this.message = message;
		this.redirectUrl = redirectUrl;
		this.data = data;
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRedirectUrl() {
		return this.redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public Serializable getData() {
		return this.data;
	}

	public void setData(Serializable data) {
		this.data = data;
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.exceptoin.WebMsgException JD-Core
 * Version: 0.7.0-SNAPSHOT-20130630
 */