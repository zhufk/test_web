package com.zfk.base.util;
/*   2:    */
/*   3:    */import java.util.ArrayList;
/*   4:    */import java.util.Arrays;
/*   5:    */import java.util.Collection;
/*   6:    */import java.util.HashSet;
/*   7:    */import java.util.Iterator;
/*   8:    */import java.util.List;
/*   9:    */import java.util.Map;
/*  10:    */import java.util.Set;
/*  11:    */
/*  19:    */public abstract class CollectionUtils
/*  20:    */{
/*  21:    */  public static Boolean isEmpty(Collection<?> collection)
/*  22:    */  {
/*  23: 23 */    return Boolean.valueOf((collection == null) || (collection.isEmpty()));
/*  24:    */  }
/*  25:    */  
/*  32:    */  public static Boolean isNotEmpty(Collection<?> collection)
/*  33:    */  {
/*  34: 34 */    return Boolean.valueOf(!isEmpty(collection).booleanValue());
/*  35:    */  }
/*  36:    */  
/*  43:    */  public static Boolean isEmpty(Object[] array)
/*  44:    */  {
/*  45: 45 */    return Boolean.valueOf((array == null) || (array.length == 0));
/*  46:    */  }
/*  47:    */  
/*  54:    */  public static Boolean isNotEmpty(Object[] array)
/*  55:    */  {
/*  56: 56 */    return Boolean.valueOf(!isEmpty(array).booleanValue());
/*  57:    */  }
/*  58:    */  
/*  65:    */  public static Boolean isEmpty(Map<?, ?> map)
/*  66:    */  {
/*  67: 67 */    return Boolean.valueOf((map == null) || (map.isEmpty()));
/*  68:    */  }
/*  69:    */  
/*  76:    */  public static Boolean isNotEmpty(Map<?, ?> map)
/*  77:    */  {
/*  78: 78 */    return Boolean.valueOf(!isEmpty(map).booleanValue());
/*  79:    */  }
/*  80:    */  
/*  89:    */  public static <T> void removeDuplicate(List<T> list)
/*  90:    */  {
/*  91: 91 */    Set<T> set = new HashSet<T>(list);
/*  92: 92 */    list.clear();
/*  93: 93 */    list.addAll(set);
/*  94:    */  }
/*  95:    */  
/* 106:    */  public static <T> Boolean contains(T[] elements, T elementToFind)
/* 107:    */  {
/* 108:108 */    if (isEmpty(elements).booleanValue()) {
/* 109:109 */      return Boolean.valueOf(false);
/* 110:    */    }
/* 111:111 */    List<T> elementsList = Arrays.asList(elements);
/* 112:112 */    return Boolean.valueOf(elementsList.contains(elementToFind));
/* 113:    */  }
/* 114:    */  
/* 124:    */  public static <T> void copy(Collection<T> source, Collection<T> target)
/* 125:    */  {
/* 126:126 */    assert (source != null);
/* 127:127 */    assert (source != null);
/* 128:128 */    target.clear();
/* 129:129 */    Iterator<T> localIterator; if (!source.isEmpty()) {
/* 130:130 */      for (localIterator = source.iterator(); localIterator.hasNext();) { T o = (T) localIterator.next();
/* 131:131 */        target.add(o);
/* 132:    */      }
/* 133:    */    }
/* 134:    */  }
/* 135:    */  
/* 141:    */  public static <T> List<List<T>> subList(List<T> targe, int size)
/* 142:    */  {
/* 143:143 */    List<List<T>> listArr = new ArrayList<List<T>>();
/* 144:    */    
/* 145:145 */    int arrSize = targe.size() % size == 0 ? targe.size() / size : targe.size() / size + 1;
/* 146:146 */    for (int i = 0; i < arrSize; i++) {
/* 147:147 */      List<T> sub = new ArrayList<T>();
/* 148:    */      
/* 149:149 */      for (int j = i * size; j <= size * (i + 1) - 1; j++) {
/* 150:150 */        if (j <= targe.size() - 1) {
/* 151:151 */          sub.add(targe.get(j));
/* 152:    */        }
/* 153:    */      }
/* 154:154 */      listArr.add(sub);
/* 155:    */    }
/* 156:156 */    return listArr;
/* 157:    */  }
/* 158:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.CollectionUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */