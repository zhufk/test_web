package com.zfk.base.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public final class ConvertUtils {
	public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
		if (map == null) {
			return null;
		}
		Object obj = beanClass.newInstance();
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			Method setter = property.getWriteMethod();
			if (setter != null) {
				setter.invoke(obj, new Object[] { map.get(property.getName()) });
			}
		}
		return obj;
	}

	public static Map<String, Object> objectToMap(Object obj, Map<String, Object> map) throws Exception {
		if (obj == null) {
			return null;
		}
		if (map == null) {
			map = new HashMap();
		}
		BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (key.compareToIgnoreCase("class") != 0) {
				Method getter = property.getReadMethod();
				Object value = getter != null ? getter.invoke(obj, new Object[0]) : null;
				map.put(key, value);
			}
		}
		return map;
	}

	public static boolean parseBoolean(Object data, boolean def) {
		if (null != data) {
			try {
				return ((data instanceof Boolean) ? (Boolean) data : Boolean.valueOf(data.toString())).booleanValue();
			} catch (Exception e) {
				return def;
			}
		}

		return def;
	}

	public static boolean parseBoolean(Object data) {
		return parseBoolean(data, false);
	}

	public static int parseInt(Object data, int def) {
		if (null != data) {
			try {
				return (data instanceof Integer) ? ((Integer) data).intValue()
						: Integer.valueOf(String.valueOf(data)).intValue();
			} catch (Exception e) {
				return def;
			}
		}
		return def;
	}

	public static int parseInt(Object data) {
		return parseInt(data, 0);
	}

	public static long parseLong(Object data, long def) {
		if (null != data) {
			try {
				return (data instanceof Long) ? ((Long) data).longValue()
						: Long.valueOf(String.valueOf(data)).longValue();
			} catch (Exception e) {
				return def;
			}
		}
		return def;
	}

	public static long parseLong(Object data) {
		return parseLong(data, 0L);
	}

	public static double parseDouble(Object data, double def) {
		if (null != data) {
			try {
				double value = def;
				if ((data instanceof BigDecimal)) {
					value = ((BigDecimal) data).doubleValue();
				} else if ((data instanceof Double)) {
					value = ((Double) data).doubleValue();
				} else {
					value = Double.valueOf(String.valueOf(data)).doubleValue();
				}
				return MathUtils.roundHalfUp(value);
			} catch (Exception e) {
				return def;
			}
		}
		return def;
	}

	public static double parseDouble(Object data) {
		return parseDouble(data, 0.0D);
	}

	public static String emptyIfNull(String input) {
		return input == null ? "" : input.trim();
	}

	public static String emptyIfNull(Object input) {
		return input == null ? "" : input.toString().trim();
	}

	public static <T> T emptyIfNull(T obj, T t) {
		if (obj == null) {
			return t;
		}
		return obj;
	}

	public static String emptyIfNull(String input, String def) {
		input = emptyIfNull(input);
		return input.isEmpty() ? def : input;
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.ConvertUtils JD-Core Version:
 * 0.7.0-SNAPSHOT-20130630
 */