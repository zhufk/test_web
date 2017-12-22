package com.zfk.base.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zfk.base.exceptoin.UncheckedException;

public abstract class StringUtils {
	public static Boolean isEmpty(String str) {
		return Boolean.valueOf((str == null) || (str.isEmpty()));
	}

	public static Boolean isEmpty(Object object) {
		if ((null != object) && ((object instanceof String))) {
			String s = (String) object;
			return isEmpty(s);
		}
		return Boolean.valueOf(null == object);
	}

	public static Boolean isNotEmpty(String str) {
		return Boolean.valueOf(!isEmpty(str).booleanValue());
	}

	public static Boolean isBlank(String str) {
		if (isEmpty(str).booleanValue()) {
			return Boolean.valueOf(true);
		}
		for (char c : str.toCharArray()) {
			if (!Character.isWhitespace(c)) {
				return Boolean.valueOf(false);
			}
		}
		return Boolean.valueOf(true);
	}

	public static Boolean isNotBlank(String str) {
		return Boolean.valueOf(!isBlank(str).booleanValue());
	}

	public static String substringBefore(String str, String separator) {
		AssertUtils.notEmpty(str);
		AssertUtils.notEmpty(separator);

		int pos = str.indexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringBeforeLast(String str, String separator) {
		AssertUtils.notNull(str);
		AssertUtils.notEmpty(separator);

		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	public static String substringAfter(String str, String separator) {
		AssertUtils.notEmpty(str);
		AssertUtils.notEmpty(separator);

		int pos = str.indexOf(separator);
		if (pos == -1) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	public static String substringAfterLast(String str, String separator) {
		AssertUtils.notEmpty(str);
		AssertUtils.notEmpty(separator);

		int pos = str.lastIndexOf(separator);
		if ((pos == -1) || (pos == str.length() - separator.length())) {
			return "";
		}
		return str.substring(pos + separator.length());
	}

	public static String substringBetween(String str, String startSeparator, String endSeparator) {
		if ((str == null) || (startSeparator == null) || (endSeparator == null)) {
			return null;
		}
		int start = str.indexOf(startSeparator);
		if (start != -1) {
			int end = str.indexOf(endSeparator, start + startSeparator.length());
			if (end != -1) {
				return str.substring(start + startSeparator.length(), end);
			}
		}
		return null;
	}

	public static String mid(String str, int pos, int len) {
		AssertUtils.notEmpty(str);
		AssertUtils.isTrue(Boolean.valueOf((pos >= 0) && (pos <= str.length())));
		AssertUtils.isTrue(Boolean.valueOf(len >= 0));

		if (str.length() <= pos + len) {
			return str.substring(pos);
		}
		return str.substring(pos, pos + len);
	}

	public static String join(String[] strs, String separator) {
		AssertUtils.notNull(strs);
		AssertUtils.notNull(separator);

		StringBuilder builder = new StringBuilder();
		for (String str : strs) {
			builder.append(new StringBuilder().append(str).append(separator).toString());
		}

		String result = builder.toString();
		if (!separator.isEmpty()) {
			result = substringBeforeLast(result, separator);
		}
		return result;
	}

	public static String join(List<String> strs, String separator) {
		return join((String[]) strs.toArray(new String[0]), separator);
	}

	public static String encode(String str, String origEncoding, String destEncoding) {
		try {
			return new String(str.getBytes(origEncoding), destEncoding);
		} catch (UnsupportedEncodingException e) {
			throw new UncheckedException("对字符串进行字符集转换时发生异常", e);
		}
	}

	public static String join(Map<String, Object> map, String separator, String space) {
		StringBuffer sb = new StringBuffer();

		assert (map != null);
		if (isNotBlank(separator).booleanValue()) {
			for (Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
				Map.Entry<String, Object> entry = (Map.Entry) iterator.next();
				sb.append(((String) entry.getKey()).toString()).append(separator)
						.append(null == entry.getValue() ? "" : entry.getValue().toString())
						.append(iterator.hasNext() ? space : "");
			}
			return sb.toString();
		}
		return "";
	}

	public static String getLastString(String str, int num) {
		if (isNotBlank(str).booleanValue()) {
			return str.substring(str.length() - num, str.length());
		}
		return str;
	}

	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}

		Random randGen = new Random();

		char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
				.toCharArray();

		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static final List<Integer> indexsOf(String str, String target) {
		if ((isNotBlank(str).booleanValue()) && (isNotBlank(target).booleanValue())) {
			List<Integer> list = new ArrayList<Integer>();

			Pattern pattern = Pattern.compile(target, 2);
			Matcher matcher = pattern.matcher(str);

			while (matcher.find()) {
				list.add(Integer.valueOf(matcher.start()));
			}

			return list;
		}

		return null;
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.StringUtils JD-Core Version:
 * 0.7.0-SNAPSHOT-20130630
 */