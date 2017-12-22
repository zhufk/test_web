package com.zfk.base.exceptoin;

import java.io.Serializable;

public class ErrorCode implements Serializable {
	private static final long serialVersionUID = 1982607259137204522L;
	private final int code;
	private String message;

	public ErrorCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String toString() {
		return "ErrorCode [code=" + this.code + ", message=" + this.message + "]";
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.exceptoin.ErrorCode JD-Core
 * Version: 0.7.0-SNAPSHOT-20130630
 */