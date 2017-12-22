package com.zfk.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class CollectionUtils {
	public static Boolean isEmpty(Collection<?> collection) {
		return Boolean.valueOf((collection == null) || (collection.isEmpty()));
	}

	public static Boolean isNotEmpty(Collection<?> collection) {
		return Boolean.valueOf(!isEmpty(collection).booleanValue());
	}

	public static Boolean isEmpty(Object[] array) {
		return Boolean.valueOf((array == null) || (array.length == 0));
	}

	public static Boolean isNotEmpty(Object[] array) {
		return Boolean.valueOf(!isEmpty(array).booleanValue());
	}

	public static Boolean isEmpty(Map<?, ?> map) {
		return Boolean.valueOf((map == null) || (map.isEmpty()));
	}

	public static Boolean isNotEmpty(Map<?, ?> map) {
		return Boolean.valueOf(!isEmpty(map).booleanValue());
	}

	public static <T> void removeDuplicate(List<T> list) {
		Set<T> set = new HashSet<T>(list);
		list.clear();
		list.addAll(set);
	}

	public static <T> Boolean contains(T[] elements, T elementToFind) {
		if (isEmpty(elements).booleanValue()) {
			return Boolean.valueOf(false);
		}
		List<T> elementsList = Arrays.asList(elements);
		return Boolean.valueOf(elementsList.contains(elementToFind));
	}

	public static <T> void copy(Collection<T> source, Collection<T> target) {
		assert (source != null);
		assert (source != null);
		target.clear();
		Iterator<T> localIterator;
		if (!source.isEmpty()) {
			for (localIterator = source.iterator(); localIterator.hasNext();) {
				T o = (T) localIterator.next();
				target.add(o);
			}
		}
	}

	public static <T> List<List<T>> subList(List<T> targe, int size) {
		List<List<T>> listArr = new ArrayList<List<T>>();

		int arrSize = targe.size() % size == 0 ? targe.size() / size : targe.size() / size + 1;
		for (int i = 0; i < arrSize; i++) {
			List<T> sub = new ArrayList<T>();

			for (int j = i * size; j <= size * (i + 1) - 1; j++) {
				if (j <= targe.size() - 1) {
					sub.add(targe.get(j));
				}
			}
			listArr.add(sub);
		}
		return listArr;
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.CollectionUtils JD-Core
 * Version: 0.7.0-SNAPSHOT-20130630
 */