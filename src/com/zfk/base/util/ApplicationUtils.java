package com.zfk.base.util;

import java.util.UUID;
import org.apache.commons.codec.digest.DigestUtils;

public class ApplicationUtils {
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	public static String md5Hex(String value) {
		return DigestUtils.md5Hex(value);
	}

	public static String sha1Hex(String value) {
		return DigestUtils.sha1Hex(value);
	}

	public static String sha256Hex(String value) {
		return DigestUtils.sha256Hex(value);
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.ApplicationUtils JD-Core
 * Version: 0.7.0-SNAPSHOT-20130630
 */