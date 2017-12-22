package com.zfk.base.exceptoin;

public class MissingOrderResourceException extends RuntimeException {
	private static final long serialVersionUID = -4876345176062000401L;

	private String className;

	private String key;

	public MissingOrderResourceException(String s, String className, String key) {
		super(s);
		this.className = className;
		this.key = key;
	}

	public MissingOrderResourceException(String message, String className, String key, Throwable cause) {
		super(message, cause);
		this.className = className;
		this.key = key;
	}

	public String getClassName() {
		return this.className;
	}

	public String getKey() {
		return this.key;
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name:
 * com.gep.core.exceptoin.MissingOrderResourceException JD-Core Version:
 * 0.7.0-SNAPSHOT-20130630
 */