package com.zfk.base.exceptoin;

public class MobileException extends RuntimeException {
	private static final long serialVersionUID = -7887057238055620806L;
	private final ErrorCode errorCode;

	public MobileException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public ErrorCode getCode() {
		return this.errorCode;
	}

	public String getMessage() {
		return String.format("code : %s ; msg : %s",
				new Object[] { Integer.valueOf(this.errorCode.getCode()), this.errorCode.getMessage() });
	}

	public synchronized Throwable fillInStackTrace() {
		return super.fillInStackTrace();
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.exceptoin.MobileException JD-Core
 * Version: 0.7.0-SNAPSHOT-20130630
 */