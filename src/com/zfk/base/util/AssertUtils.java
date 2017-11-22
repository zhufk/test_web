package com.zfk.base.util;
/*   2:    */
/*   4:    */import java.util.Collection;
/*   5:    */import java.util.Map;

import com.zfk.base.exceptoin.UncheckedException;
/*   6:    */
/*  17:    */public abstract class AssertUtils
/*  18:    */{
/*  19:    */  public static void isTrue(Boolean expression, String message)
/*  20:    */  {
/*  21: 21 */    if (!expression.booleanValue()) {
/*  22: 22 */      throw new UncheckedException(message);
/*  23:    */    }
/*  24:    */  }
/*  25:    */  
/*  31:    */  public static void isTrue(Boolean expression)
/*  32:    */  {
/*  33: 33 */    isTrue(expression, "断言失败: 表达式必须是true");
/*  34:    */  }
/*  35:    */  
/*  43:    */  public static void isNull(Object object, String message)
/*  44:    */  {
/*  45: 45 */    if (object != null) {
/*  46: 46 */      throw new UncheckedException(message);
/*  47:    */    }
/*  48:    */  }
/*  49:    */  
/*  55:    */  public static void isNull(Object object)
/*  56:    */  {
/*  57: 57 */    isNull(object, "断言失败: 对象必须是null");
/*  58:    */  }
/*  59:    */  
/*  67:    */  public static void notNull(Object object, String message)
/*  68:    */  {
/*  69: 69 */    if (object == null) {
/*  70: 70 */      throw new UncheckedException(message);
/*  71:    */    }
/*  72:    */  }
/*  73:    */  
/*  79:    */  public static void notNull(Object object)
/*  80:    */  {
/*  81: 81 */    notNull(object, "断言失败: 对象不能是null");
/*  82:    */  }
/*  83:    */  
/*  91:    */  public static void isEmpty(String text, String message)
/*  92:    */  {
/*  93: 93 */    if (StringUtils.isNotEmpty(text).booleanValue()) {
/*  94: 94 */      throw new UncheckedException(message);
/*  95:    */    }
/*  96:    */  }
/*  97:    */  
/* 103:    */  public static void isEmpty(String text)
/* 104:    */  {
/* 105:105 */    isEmpty(text, "断言失败: 字符串必须是空");
/* 106:    */  }
/* 107:    */  
/* 115:    */  public static void notEmpty(String text, String message)
/* 116:    */  {
/* 117:117 */    if (StringUtils.isEmpty(text).booleanValue()) {
/* 118:118 */      throw new UncheckedException(message);
/* 119:    */    }
/* 120:    */  }
/* 121:    */  
/* 127:    */  public static void notEmpty(String text)
/* 128:    */  {
/* 129:129 */    notEmpty(text, "断言失败: 字符串不能是空");
/* 130:    */  }
/* 131:    */  
/* 139:    */  public static void isBlank(String text, String message)
/* 140:    */  {
/* 141:141 */    if (StringUtils.isNotBlank(text).booleanValue()) {
/* 142:142 */      throw new UncheckedException(message);
/* 143:    */    }
/* 144:    */  }
/* 145:    */  
/* 151:    */  public static void isBlank(String text)
/* 152:    */  {
/* 153:153 */    isBlank(text, "断言失败: 字符串必须是空字符串");
/* 154:    */  }
/* 155:    */  
/* 163:    */  public static void notBlank(String text, String message)
/* 164:    */  {
/* 165:165 */    if (StringUtils.isBlank(text).booleanValue()) {
/* 166:166 */      throw new UncheckedException(message);
/* 167:    */    }
/* 168:    */  }
/* 169:    */  
/* 175:    */  public static void notBlank(String text)
/* 176:    */  {
/* 177:177 */    notBlank(text, "断言失败: 字符串不能是空字符串");
/* 178:    */  }
/* 179:    */  
/* 187:    */  public static void notEmpty(Collection<?> collection, String message)
/* 188:    */  {
/* 189:189 */    if (CollectionUtils.isEmpty(collection).booleanValue()) {
/* 190:190 */      throw new UncheckedException(message);
/* 191:    */    }
/* 192:    */  }
/* 193:    */  
/* 199:    */  public static void notEmpty(Collection<?> collection)
/* 200:    */  {
/* 201:201 */    notEmpty(collection, "断言失败: 集合不能是空");
/* 202:    */  }
/* 203:    */  
/* 211:    */  public static void notEmpty(Object[] array, String message)
/* 212:    */  {
/* 213:213 */    if (CollectionUtils.isEmpty(array).booleanValue()) {
/* 214:214 */      throw new UncheckedException(message);
/* 215:    */    }
/* 216:    */  }
/* 217:    */  
/* 223:    */  public static void notEmpty(Object[] array)
/* 224:    */  {
/* 225:225 */    notEmpty(array, "断言失败: 数组不能是空");
/* 226:    */  }
/* 227:    */  
/* 235:    */  public static void notEmpty(Map<?, ?> map, String message)
/* 236:    */  {
/* 237:237 */    if (CollectionUtils.isEmpty(map).booleanValue()) {
/* 238:238 */      throw new UncheckedException(message);
/* 239:    */    }
/* 240:    */  }
/* 241:    */  
/* 247:    */  public static void notEmpty(Map<?, ?> map)
/* 248:    */  {
/* 249:249 */    notEmpty(map, "断言失败: Map不能是空");
/* 250:    */  }
/* 251:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.AssertUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */