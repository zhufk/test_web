/*   1:    */package com.zfk.base.util;
/*   2:    */
/*   4:    */import java.io.UnsupportedEncodingException;
/*   5:    */import java.util.ArrayList;
/*   6:    */import java.util.Iterator;
/*   7:    */import java.util.List;
/*   8:    */import java.util.Map;
/*   9:    */import java.util.Map.Entry;
/*  10:    */import java.util.Random;
/*  11:    */import java.util.Set;
/*  12:    */import java.util.regex.Matcher;
/*  13:    */import java.util.regex.Pattern;

import com.zfk.base.exceptoin.UncheckedException;
/*  14:    */
/*  23:    */public abstract class StringUtils
/*  24:    */{
/*  25:    */  public static Boolean isEmpty(String str)
/*  26:    */  {
/*  27: 27 */    return Boolean.valueOf((str == null) || (str.isEmpty()));
/*  28:    */  }
/*  29:    */  
/*  36:    */  public static Boolean isEmpty(Object object)
/*  37:    */  {
/*  38: 38 */    if ((null != object) && ((object instanceof String))) {
/*  39: 39 */      String s = (String)object;
/*  40: 40 */      return isEmpty(s);
/*  41:    */    }
/*  42: 42 */    return Boolean.valueOf(null == object);
/*  43:    */  }
/*  44:    */  
/*  51:    */  public static Boolean isNotEmpty(String str)
/*  52:    */  {
/*  53: 53 */    return Boolean.valueOf(!isEmpty(str).booleanValue());
/*  54:    */  }
/*  55:    */  
/*  62:    */  public static Boolean isBlank(String str)
/*  63:    */  {
/*  64: 64 */    if (isEmpty(str).booleanValue()) {
/*  65: 65 */      return Boolean.valueOf(true);
/*  66:    */    }
/*  67: 67 */    for (char c : str.toCharArray()) {
/*  68: 68 */      if (!Character.isWhitespace(c)) {
/*  69: 69 */        return Boolean.valueOf(false);
/*  70:    */      }
/*  71:    */    }
/*  72: 72 */    return Boolean.valueOf(true);
/*  73:    */  }
/*  74:    */  
/*  81:    */  public static Boolean isNotBlank(String str)
/*  82:    */  {
/*  83: 83 */    return Boolean.valueOf(!isBlank(str).booleanValue());
/*  84:    */  }
/*  85:    */  
/*  94:    */  public static String substringBefore(String str, String separator)
/*  95:    */  {
/*  96: 96 */    AssertUtils.notEmpty(str);
/*  97: 97 */    AssertUtils.notEmpty(separator);
/*  98:    */    
/*  99: 99 */    int pos = str.indexOf(separator);
/* 100:100 */    if (pos == -1) {
/* 101:101 */      return str;
/* 102:    */    }
/* 103:103 */    return str.substring(0, pos);
/* 104:    */  }
/* 105:    */  
/* 114:    */  public static String substringBeforeLast(String str, String separator)
/* 115:    */  {
/* 116:116 */    AssertUtils.notNull(str);
/* 117:117 */    AssertUtils.notEmpty(separator);
/* 118:    */    
/* 119:119 */    int pos = str.lastIndexOf(separator);
/* 120:120 */    if (pos == -1) {
/* 121:121 */      return str;
/* 122:    */    }
/* 123:123 */    return str.substring(0, pos);
/* 124:    */  }
/* 125:    */  
/* 134:    */  public static String substringAfter(String str, String separator)
/* 135:    */  {
/* 136:136 */    AssertUtils.notEmpty(str);
/* 137:137 */    AssertUtils.notEmpty(separator);
/* 138:    */    
/* 139:139 */    int pos = str.indexOf(separator);
/* 140:140 */    if (pos == -1) {
/* 141:141 */      return "";
/* 142:    */    }
/* 143:143 */    return str.substring(pos + separator.length());
/* 144:    */  }
/* 145:    */  
/* 154:    */  public static String substringAfterLast(String str, String separator)
/* 155:    */  {
/* 156:156 */    AssertUtils.notEmpty(str);
/* 157:157 */    AssertUtils.notEmpty(separator);
/* 158:    */    
/* 159:159 */    int pos = str.lastIndexOf(separator);
/* 160:160 */    if ((pos == -1) || (pos == str.length() - separator.length())) {
/* 161:161 */      return "";
/* 162:    */    }
/* 163:163 */    return str.substring(pos + separator.length());
/* 164:    */  }
/* 165:    */  
/* 176:    */  public static String substringBetween(String str, String startSeparator, String endSeparator)
/* 177:    */  {
/* 178:178 */    if ((str == null) || (startSeparator == null) || (endSeparator == null)) {
/* 179:179 */      return null;
/* 180:    */    }
/* 181:181 */    int start = str.indexOf(startSeparator);
/* 182:182 */    if (start != -1) {
/* 183:183 */      int end = str.indexOf(endSeparator, start + startSeparator.length());
/* 184:184 */      if (end != -1) {
/* 185:185 */        return str.substring(start + startSeparator.length(), end);
/* 186:    */      }
/* 187:    */    }
/* 188:188 */    return null;
/* 189:    */  }
/* 190:    */  
/* 201:    */  public static String mid(String str, int pos, int len)
/* 202:    */  {
/* 203:203 */    AssertUtils.notEmpty(str);
/* 204:204 */    AssertUtils.isTrue(Boolean.valueOf((pos >= 0) && (pos <= str.length())));
/* 205:205 */    AssertUtils.isTrue(Boolean.valueOf(len >= 0));
/* 206:    */    
/* 207:207 */    if (str.length() <= pos + len) {
/* 208:208 */      return str.substring(pos);
/* 209:    */    }
/* 210:210 */    return str.substring(pos, pos + len);
/* 211:    */  }
/* 212:    */  
/* 221:    */  public static String join(String[] strs, String separator)
/* 222:    */  {
/* 223:223 */    AssertUtils.notNull(strs);
/* 224:224 */    AssertUtils.notNull(separator);
/* 225:    */    
/* 226:226 */    StringBuilder builder = new StringBuilder();
/* 227:227 */    for (String str : strs) {
/* 228:228 */      builder.append(new StringBuilder().append(str).append(separator).toString());
/* 229:    */    }
/* 230:    */    
/* 231:231 */    String result = builder.toString();
/* 232:232 */    if (!separator.isEmpty()) {
/* 233:233 */      result = substringBeforeLast(result, separator);
/* 234:    */    }
/* 235:235 */    return result;
/* 236:    */  }
/* 237:    */  
/* 246:    */  public static String join(List<String> strs, String separator)
/* 247:    */  {
/* 248:248 */    return join((String[])strs.toArray(new String[0]), separator);
/* 249:    */  }
/* 250:    */  
/* 260:    */  public static String encode(String str, String origEncoding, String destEncoding)
/* 261:    */  {
/* 262:    */    try
/* 263:    */    {
/* 264:264 */      return new String(str.getBytes(origEncoding), destEncoding);
/* 265:    */    } catch (UnsupportedEncodingException e) {
/* 266:266 */      throw new UncheckedException("对字符串进行字符集转换时发生异常", e);
/* 267:    */    }
/* 268:    */  }
/* 269:    */  
/* 281:    */  public static String join(Map<String, Object> map, String separator, String space)
/* 282:    */  {
/* 283:283 */    StringBuffer sb = new StringBuffer();
/* 284:    */    
/* 285:285 */    assert (map != null);
/* 286:286 */    if (isNotBlank(separator).booleanValue()) {
/* 287:287 */      for (Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
/* 288:288 */        Map.Entry<String, Object> entry = (Map.Entry)iterator.next();
/* 289:289 */        sb.append(((String)entry.getKey()).toString()).append(separator)
/* 290:290 */          .append(null == entry.getValue() ? "" : entry.getValue().toString())
/* 291:291 */          .append(iterator.hasNext() ? space : "");
/* 292:    */      }
/* 293:293 */      return sb.toString();
/* 294:    */    }
/* 295:295 */    return "";
/* 296:    */  }
/* 297:    */  
/* 304:    */  public static String getLastString(String str, int num)
/* 305:    */  {
/* 306:306 */    if (isNotBlank(str).booleanValue()) {
/* 307:307 */      return str.substring(str.length() - num, str.length());
/* 308:    */    }
/* 309:309 */    return str;
/* 310:    */  }
/* 311:    */  
/* 317:    */  public static final String randomString(int length)
/* 318:    */  {
/* 319:319 */    if (length < 1) {
/* 320:320 */      return null;
/* 321:    */    }
/* 322:    */    
/* 323:323 */    Random randGen = new Random();
/* 324:    */    
/* 325:325 */    char[] numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
/* 326:    */    
/* 327:327 */    char[] randBuffer = new char[length];
/* 328:328 */    for (int i = 0; i < randBuffer.length; i++) {
/* 329:329 */      randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
/* 330:    */    }
/* 331:331 */    return new String(randBuffer);
/* 332:    */  }
/* 333:    */  
/* 340:    */  public static final List<Integer> indexsOf(String str, String target)
/* 341:    */  {
/* 342:342 */    if ((isNotBlank(str).booleanValue()) && (isNotBlank(target).booleanValue())) {
/* 343:343 */      List<Integer> list = new ArrayList();
/* 344:    */      
/* 345:345 */      Pattern pattern = Pattern.compile(target, 2);
/* 346:346 */      Matcher matcher = pattern.matcher(str);
/* 347:    */      
/* 348:348 */      while (matcher.find()) {
/* 349:349 */        list.add(Integer.valueOf(matcher.start()));
/* 350:    */      }
/* 351:    */      
/* 352:352 */      return list;
/* 353:    */    }
/* 354:    */    
/* 355:355 */    return null;
/* 356:    */  }
/* 357:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.StringUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */