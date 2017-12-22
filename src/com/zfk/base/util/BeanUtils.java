package com.zfk.base.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import com.zfk.base.exceptoin.UncheckedException;

public abstract class BeanUtils {
	public static Field findField(Class<?> targetClass, String fieldName) {
		if ((StringUtils.isNotBlank(fieldName)) && (fieldName.contains("."))) {
			return findNestedField(targetClass, fieldName);
		}
		return findDirectField(targetClass, fieldName);
	}

	public static List<Field> findField(Class<?> targetClass, Class<? extends Annotation> annotationClassOnField) {
		List<Field> fields = new ArrayList();
		for (Field field : getAllDeclaredField(targetClass, new String[0])) {
			if (field.isAnnotationPresent(annotationClassOnField)) {
				fields.add(field);
			}
		}
		return fields;
	}

	public static Object getField(Object target, Field field) {
		if ((field != null) && (target != null)) {
			try {
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				Object result = field.get(target);
				field.setAccessible(accessible);
				return processHibernateLazyField(result);
			} catch (Exception e) {
				throw new IllegalStateException("获取对象的属性[" + field.getName() + "]值失败", e);
			}
		}

		return null;
	}

	public static Object getField(Object target, String fieldName) {
		Assert.notNull(target);
		Assert.notNull(fieldName);
		if (fieldName.contains(".")) {
			return getNestedField(target, fieldName);
		}
		return getDirectField(target, fieldName);
	}

	public static void setField(Object target, Field field, Object value) {
		Assert.notNull(target);
		if (field != null) {
			try {
				boolean accessible = field.isAccessible();
				field.setAccessible(true);
				field.set(target, value);
				field.setAccessible(accessible);
			} catch (Exception e) {
				throw new IllegalStateException("设置对象的属性[" + field.getName() + "]值失败", e);
			}
		}
	}

	public static void setField(Object target, String fieldName, Object value) {
		if (fieldName.contains(".")) {
			setNestedField(target, fieldName, value);
		} else {
			setDirectField(target, fieldName, value);
		}
	}

	public static List<Field> getAllDeclaredField(Class<?> targetClass, String... excludeFieldNames) {
		List<Field> fields = new ArrayList();
		if (targetClass != null) {
			for (Field field : targetClass.getDeclaredFields()) {
				if (!CollectionUtils.contains(excludeFieldNames, field.getName()).booleanValue()) {
					fields.add(field);
				}
			}
			Object parentClass = targetClass.getSuperclass();
			if (parentClass != Object.class) {
				fields.addAll(getAllDeclaredField((Class) parentClass, excludeFieldNames));
			}
		}
		return fields;
	}

	public static <T> void copyFields(T source, T target) {
		copyFields(source, target, null, null);
	}

	public static <T> void copyFields(T source, T target, String excludeFields) {
		copyFields(source, target, null, excludeFields);
	}

	public static <T> void copyFields(T source, T target, String includeFields, String excludeFields) {
		String[] includeFieldNames;

		if (source != null) {
			includeFieldNames = new String[0];
			if (!StringUtils.isBlank(includeFields)) {
				includeFieldNames = includeFields.split(",");
			}
			String[] excludeFieldNames = new String[0];
			if (!StringUtils.isBlank(excludeFields)) {
				excludeFieldNames = excludeFields.split(",");
			}
			for (Field field : getAllDeclaredField(source.getClass(), excludeFieldNames)) {
				if (CollectionUtils.contains(includeFieldNames, field.getName()).booleanValue()) {
					copyField(source, target, field.getName(), Boolean.valueOf(true));
				} else {
					copyField(source, target, field.getName(), Boolean.valueOf(false));
				}
			}
		}
	}

	public static <T> void copyField(T source, T target, String fieldName, Boolean containedNull) {
		Object sourceFieldValue = getField(source, fieldName);
		Field targetField = findField(target.getClass(), fieldName);
		Boolean needCopy = Boolean.valueOf((targetField != null) && (!Modifier.isFinal(targetField.getModifiers()))
				&& (!Modifier.isStatic(targetField.getModifiers())));
		if ((!containedNull.booleanValue()) && (sourceFieldValue == null)) {
			needCopy = Boolean.valueOf(false);
		}
		if (needCopy.booleanValue()) {
			if ((sourceFieldValue != null) && (Collection.class.isAssignableFrom(sourceFieldValue.getClass()))) {
				if ((!((Collection) sourceFieldValue).isEmpty()) || (containedNull.booleanValue())) {
					CollectionUtils.copy((Collection) sourceFieldValue, (Collection) getField(target, fieldName));
				}
			} else {
				setField(target, fieldName, sourceFieldValue);
			}
		}
	}

	public static Class<?> getGenericFieldType(Field field) {
		Type type = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
		if ((type instanceof ParameterizedType)) {
			return (Class) ((ParameterizedType) type).getRawType();
		}
		return (Class) type;
	}

	public static void copyProperties(Object bean, Map<String, Object> map) {
		copyProperties(bean, map, null);
	}

	public static void copyProperties(Object bean, Map<String, Object> map, String excludeFields) {

		if ((bean != null) && (map != null)) {
			try {
				String[] excludeFieldNames = new String[0];
				if (!StringUtils.isBlank(excludeFields)) {
					excludeFieldNames = excludeFields.split(",");
				}
				for (Field field : getAllDeclaredField(bean.getClass(), excludeFieldNames)) {
					if (!CollectionUtils.contains(excludeFieldNames, field.getName()).booleanValue())
						map.put(field.getName(), getField(bean, field));
				}
			} catch (Exception e) {
				String[] excludeFieldNames;
				throw new UncheckedException("复制Bean对象属性到Map对象时发生异常。", e);
			}
		}
	}

	public static void copyProperties(Map<String, Object> map, Object bean) {
		copyProperties(map, bean, null);
	}

	public static void copyProperties(Map<String, Object> map, Object bean, String excludeFields) {
		try {
			if ((map != null) && (bean != null) && (StringUtils.isNotBlank(excludeFields))) {
				String[] excludeFieldNames = excludeFields.split(",");
				for (Map.Entry<String, Object> entity : map.entrySet()) {
					Field field = findField(bean.getClass(), (String) entity.getKey());
					if ((field != null)
							&& (!CollectionUtils.contains(excludeFieldNames, entity.getKey()).booleanValue()))
						setField(bean, field, entity.getValue());
				}
			}
		} catch (Exception e) {
			String[] excludeFieldNames;
			throw new UncheckedException("复制Map对象属性到Bean对象时发生异常。", e);
		}
	}

	private static Field findDirectField(Class<?> targetClass, String fieldName) {
		for (Field field : getAllDeclaredField(targetClass, new String[0])) {
			if ((field != null) && (fieldName != null) && (fieldName.equals(field.getName()))) {
				return field;
			}
		}
		return null;
	}

	private static Field findNestedField(Class<?> targetClass, String fieldName) {
		Field field = null;
		if (StringUtils.isNotBlank(fieldName)) {
			String[] nestedFieldNames = fieldName.split("\\.");
			for (String nestedFieldName : nestedFieldNames) {
				field = findDirectField(targetClass, nestedFieldName);
			}
		}

		return field;
	}

	private static Object getDirectField(Object target, String fieldName) {
		if ((target != null) && (StringUtils.isNotBlank(fieldName))) {
			return getField(target, findDirectField(target.getClass(), fieldName));
		}

		return null;
	}

	private static Object getNestedField(Object target, String fieldName) {
		Assert.notNull(target);
		Assert.notNull(fieldName);
		String[] nestedFieldNames = fieldName.split("\\.");
		for (String nestedFieldName : nestedFieldNames) {
			target = getDirectField(target, nestedFieldName);
		}
		return target;
	}

	private static void setDirectField(Object target, String fieldName, Object value) {
		Assert.notNull(target);
		Assert.notNull(fieldName);
		setField(target, findDirectField(target.getClass(), fieldName), value);
	}

	private static void setNestedField(Object target, String fieldName, Object value) {
		String[] nestedFieldNames = StringUtils.substringBeforeLast(fieldName, ".").split("\\.");
		for (String nestedFieldName : nestedFieldNames) {
			target = getDirectField(target, nestedFieldName);
		}
		setDirectField(target, StringUtils.substringAfterLast(fieldName, "."), value);
	}

	private static Object processHibernateLazyField(Object fieldValue) {
		try {
			Class<?> hibernateProxyClass = Class.forName("org.hibernate.proxy.HibernateProxy");
			if (hibernateProxyClass.isAssignableFrom(fieldValue.getClass())) {
				Method method = fieldValue.getClass().getMethod("getHibernateLazyInitializer", new Class[0]);
				Object lazyInitializer = method.invoke(fieldValue, new Object[0]);
				method = lazyInitializer.getClass().getMethod("getImplementation", new Class[0]);
				return method.invoke(lazyInitializer, new Object[0]);
			}
			return fieldValue;
		} catch (Exception e) {
		}
		return fieldValue;
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.BeanUtils JD-Core Version:
 * 0.7.0-SNAPSHOT-20130630
 */