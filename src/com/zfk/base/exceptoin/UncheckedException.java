package com.zfk.base.exceptoin;

public class UncheckedException extends RuntimeException {
	public UncheckedException(String message) {
		super(message);
	}

	public UncheckedException(Throwable cause) {
		super(cause);
	}

	public UncheckedException(String message, Throwable cause) {
		super(message, cause);
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.exceptoin.UncheckedException
 * JD-Core Version: 0.7.0-SNAPSHOT-20130630
 */