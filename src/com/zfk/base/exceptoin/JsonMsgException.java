package com.zfk.base.exceptoin;

import java.io.Serializable;

public class JsonMsgException extends RuntimeException {
	private static final long serialVersionUID = 4113399553351843338L;
	private int code;
	private String message;
	private Serializable data;

	public JsonMsgException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public JsonMsgException(int code, String message, Serializable data) {
		this.code = code;
		this.message = message;
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
 * SNAPSHOT.jar Qualified Name: com.gep.core.exceptoin.JsonMsgException JD-Core
 * Version: 0.7.0-SNAPSHOT-20130630
 */