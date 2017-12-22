package com.zfk.base.util;

import java.util.Collection;
import java.util.Map;

import com.zfk.base.exceptoin.UncheckedException;

public abstract class AssertUtils {
	public static void isTrue(Boolean expression, String message) {
		if (!expression.booleanValue()) {
			throw new UncheckedException(message);
		}
	}

	public static void isTrue(Boolean expression) {
		isTrue(expression, "断言失败: 表达式必须是true");
	}

	public static void isNull(Object object, String message) {
		if (object != null) {
			throw new UncheckedException(message);
		}
	}

	public static void isNull(Object object) {
		isNull(object, "断言失败: 对象必须是null");
	}

	public static void notNull(Object object, String message) {
		if (object == null) {
			throw new UncheckedException(message);
		}
	}

	public static void notNull(Object object) {
		notNull(object, "断言失败: 对象不能是null");
	}

	public static void isEmpty(String text, String message) {
		if (StringUtils.isNotEmpty(text).booleanValue()) {
			throw new UncheckedException(message);
		}
	}

	public static void isEmpty(String text) {
		isEmpty(text, "断言失败: 字符串必须是空");
	}

	public static void notEmpty(String text, String message) {
		if (StringUtils.isEmpty(text).booleanValue()) {
			throw new UncheckedException(message);
		}
	}

	public static void notEmpty(String text) {
		notEmpty(text, "断言失败: 字符串不能是空");
	}

	public static void isBlank(String text, String message) {
		if (StringUtils.isNotBlank(text).booleanValue()) {
			throw new UncheckedException(message);
		}
	}

	public static void isBlank(String text) {
		isBlank(text, "断言失败: 字符串必须是空字符串");
	}

	public static void notBlank(String text, String message) {
		if (StringUtils.isBlank(text).booleanValue()) {
			throw new UncheckedException(message);
		}
	}

	public static void notBlank(String text) {
		notBlank(text, "断言失败: 字符串不能是空字符串");
	}

	public static void notEmpty(Collection<?> collection, String message) {
		if (CollectionUtils.isEmpty(collection).booleanValue()) {
			throw new UncheckedException(message);
		}
	}

	public static void notEmpty(Collection<?> collection) {
		notEmpty(collection, "断言失败: 集合不能是空");
	}

	public static void notEmpty(Object[] array, String message) {
		if (CollectionUtils.isEmpty(array).booleanValue()) {
			throw new UncheckedException(message);
		}
	}

	public static void notEmpty(Object[] array) {
		notEmpty(array, "断言失败: 数组不能是空");
	}

	public static void notEmpty(Map<?, ?> map, String message) {
		if (CollectionUtils.isEmpty(map).booleanValue()) {
			throw new UncheckedException(message);
		}
	}

	public static void notEmpty(Map<?, ?> map) {
		notEmpty(map, "断言失败: Map不能是空");
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.AssertUtils JD-Core Version:
 * 0.7.0-SNAPSHOT-20130630
 */