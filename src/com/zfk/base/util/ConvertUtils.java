package com.zfk.base.util;
/*   2:    */
/*   3:    */import java.beans.BeanInfo;
/*   4:    */import java.beans.Introspector;
/*   5:    */import java.beans.PropertyDescriptor;
/*   6:    */import java.lang.reflect.Method;
/*   7:    */import java.math.BigDecimal;
/*   8:    */import java.util.HashMap;
/*   9:    */import java.util.Map;
/*  10:    */
/*  17:    */public final class ConvertUtils
/*  18:    */{
/*  19:    */  public static Object mapToObject(Map<String, Object> map, Class<?> beanClass)
/*  20:    */    throws Exception
/*  21:    */  {
/*  22: 22 */    if (map == null) {
/*  23: 23 */      return null;
/*  24:    */    }
/*  25: 25 */    Object obj = beanClass.newInstance();
/*  26: 26 */    BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
/*  27: 27 */    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
/*  28: 28 */    for (PropertyDescriptor property : propertyDescriptors) {
/*  29: 29 */      Method setter = property.getWriteMethod();
/*  30: 30 */      if (setter != null) {
/*  31: 31 */        setter.invoke(obj, new Object[] { map.get(property.getName()) });
/*  32:    */      }
/*  33:    */    }
/*  34: 34 */    return obj;
/*  35:    */  }
/*  36:    */  
/*  37:    */  public static Map<String, Object> objectToMap(Object obj, Map<String, Object> map) throws Exception {
/*  38: 38 */    if (obj == null) {
/*  39: 39 */      return null;
/*  40:    */    }
/*  41: 41 */    if (map == null) {
/*  42: 42 */      map = new HashMap();
/*  43:    */    }
/*  44: 44 */    BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
/*  45: 45 */    PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
/*  46: 46 */    for (PropertyDescriptor property : propertyDescriptors) {
/*  47: 47 */      String key = property.getName();
/*  48: 48 */      if (key.compareToIgnoreCase("class") != 0)
/*  49:    */      {
/*  51: 51 */        Method getter = property.getReadMethod();
/*  52: 52 */        Object value = getter != null ? getter.invoke(obj, new Object[0]) : null;
/*  53: 53 */        map.put(key, value);
/*  54:    */      } }
/*  55: 55 */    return map;
/*  56:    */  }
/*  57:    */  
/*  58:    */  public static boolean parseBoolean(Object data, boolean def) {
/*  59: 59 */    if (null != data) {
/*  60:    */      try {
/*  61: 61 */        return ((data instanceof Boolean) ? (Boolean)data : Boolean.valueOf(data.toString())).booleanValue();
/*  62:    */      } catch (Exception e) {
/*  63: 63 */        return def;
/*  64:    */      }
/*  65:    */    }
/*  66:    */    
/*  67: 67 */    return def;
/*  68:    */  }
/*  69:    */  
/*  70:    */  public static boolean parseBoolean(Object data) {
/*  71: 71 */    return parseBoolean(data, false);
/*  72:    */  }
/*  73:    */  
/*  74:    */  public static int parseInt(Object data, int def) {
/*  75: 75 */    if (null != data) {
/*  76:    */      try {
/*  77: 77 */        return (data instanceof Integer) ? ((Integer)data).intValue() : Integer.valueOf(String.valueOf(data)).intValue();
/*  78:    */      } catch (Exception e) {
/*  79: 79 */        return def;
/*  80:    */      }
/*  81:    */    }
/*  82: 82 */    return def;
/*  83:    */  }
/*  84:    */  
/*  85:    */  public static int parseInt(Object data) {
/*  86: 86 */    return parseInt(data, 0);
/*  87:    */  }
/*  88:    */  
/*  89:    */  public static long parseLong(Object data, long def) {
/*  90: 90 */    if (null != data) {
/*  91:    */      try {
/*  92: 92 */        return (data instanceof Long) ? ((Long)data).longValue() : Long.valueOf(String.valueOf(data)).longValue();
/*  93:    */      } catch (Exception e) {
/*  94: 94 */        return def;
/*  95:    */      }
/*  96:    */    }
/*  97: 97 */    return def;
/*  98:    */  }
/*  99:    */  
/* 100:    */  public static long parseLong(Object data) {
/* 101:101 */    return parseLong(data, 0L);
/* 102:    */  }
/* 103:    */  
/* 104:    */  public static double parseDouble(Object data, double def) {
/* 105:105 */    if (null != data) {
/* 106:    */      try {
/* 107:107 */        double value = def;
/* 108:108 */        if ((data instanceof BigDecimal)) {
/* 109:109 */          value = ((BigDecimal)data).doubleValue();
/* 110:110 */        } else if ((data instanceof Double)) {
/* 111:111 */          value = ((Double)data).doubleValue();
/* 112:    */        } else {
/* 113:113 */          value = Double.valueOf(String.valueOf(data)).doubleValue();
/* 114:    */        }
/* 115:115 */        return MathUtils.roundHalfUp(value);
/* 116:    */      } catch (Exception e) {
/* 117:117 */        return def;
/* 118:    */      }
/* 119:    */    }
/* 120:120 */    return def;
/* 121:    */  }
/* 122:    */  
/* 123:    */  public static double parseDouble(Object data) {
/* 124:124 */    return parseDouble(data, 0.0D);
/* 125:    */  }
/* 126:    */  
/* 131:    */  public static String emptyIfNull(String input)
/* 132:    */  {
/* 133:133 */    return input == null ? "" : input.trim();
/* 134:    */  }
/* 135:    */  
/* 136:    */  public static String emptyIfNull(Object input) {
/* 137:137 */    return input == null ? "" : input.toString().trim();
/* 138:    */  }
/* 139:    */  
/* 140:    */  public static <T> T emptyIfNull(T obj, T t) {
/* 141:141 */    if (obj == null) {
/* 142:142 */      return t;
/* 143:    */    }
/* 144:144 */    return obj;
/* 145:    */  }
/* 146:    */  
/* 153:    */  public static String emptyIfNull(String input, String def)
/* 154:    */  {
/* 155:155 */    input = emptyIfNull(input);
/* 156:156 */    return input.isEmpty() ? def : input;
/* 157:    */  }
/* 158:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.ConvertUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */