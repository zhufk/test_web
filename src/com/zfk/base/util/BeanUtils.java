/*   1:    */package com.zfk.base.util;
/*   2:    */
/*   4:    */import java.lang.annotation.Annotation;
/*   5:    */import java.lang.reflect.Field;
/*   6:    */import java.lang.reflect.Method;
/*   7:    */import java.lang.reflect.Modifier;
/*   8:    */import java.lang.reflect.ParameterizedType;
/*   9:    */import java.lang.reflect.Type;
/*  10:    */import java.util.ArrayList;
/*  11:    */import java.util.Collection;
/*  12:    */import java.util.List;
/*  13:    */import java.util.Map;
/*  14:    */import java.util.Map.Entry;
/*  15:    */import org.apache.commons.lang3.StringUtils;
/*  16:    */import org.springframework.util.Assert;
			import com.zfk.base.exceptoin.UncheckedException;
			
/*  17:    */
/*  30:    */public abstract class BeanUtils
/*  31:    */{
/*  32:    */  public static Field findField(Class<?> targetClass, String fieldName)
/*  33:    */  {
/*  34: 34 */    if ((StringUtils.isNotBlank(fieldName)) && (fieldName.contains("."))) {
/*  35: 35 */      return findNestedField(targetClass, fieldName);
/*  36:    */    }
/*  37: 37 */    return findDirectField(targetClass, fieldName);
/*  38:    */  }
/*  39:    */  
/*  49:    */  public static List<Field> findField(Class<?> targetClass, Class<? extends Annotation> annotationClassOnField)
/*  50:    */  {
/*  51: 51 */    List<Field> fields = new ArrayList();
/*  52: 52 */    for (Field field : getAllDeclaredField(targetClass, new String[0])) {
/*  53: 53 */      if (field.isAnnotationPresent(annotationClassOnField)) {
/*  54: 54 */        fields.add(field);
/*  55:    */      }
/*  56:    */    }
/*  57: 57 */    return fields;
/*  58:    */  }
/*  59:    */  
/*  68:    */  public static Object getField(Object target, Field field)
/*  69:    */  {
/*  70: 70 */    if ((field != null) && (target != null)) {
/*  71:    */      try {
/*  72: 72 */        boolean accessible = field.isAccessible();
/*  73: 73 */        field.setAccessible(true);
/*  74: 74 */        Object result = field.get(target);
/*  75: 75 */        field.setAccessible(accessible);
/*  76: 76 */        return processHibernateLazyField(result);
/*  77:    */      } catch (Exception e) {
/*  78: 78 */        throw new IllegalStateException("获取对象的属性[" + field.getName() + "]值失败", e);
/*  79:    */      }
/*  80:    */    }
/*  81:    */    
/*  82: 82 */    return null;
/*  83:    */  }
/*  84:    */  
/*  93:    */  public static Object getField(Object target, String fieldName)
/*  94:    */  {
/*  95: 95 */    Assert.notNull(target);
/*  96: 96 */    Assert.notNull(fieldName);
/*  97: 97 */    if (fieldName.contains(".")) {
/*  98: 98 */      return getNestedField(target, fieldName);
/*  99:    */    }
/* 100:100 */    return getDirectField(target, fieldName);
/* 101:    */  }
/* 102:    */  
/* 113:    */  public static void setField(Object target, Field field, Object value)
/* 114:    */  {
/* 115:115 */    Assert.notNull(target);
/* 116:116 */    if (field != null) {
/* 117:    */      try {
/* 118:118 */        boolean accessible = field.isAccessible();
/* 119:119 */        field.setAccessible(true);
/* 120:120 */        field.set(target, value);
/* 121:121 */        field.setAccessible(accessible);
/* 122:    */      } catch (Exception e) {
/* 123:123 */        throw new IllegalStateException("设置对象的属性[" + field.getName() + "]值失败", e);
/* 124:    */      }
/* 125:    */    }
/* 126:    */  }
/* 127:    */  
/* 137:    */  public static void setField(Object target, String fieldName, Object value)
/* 138:    */  {
/* 139:139 */    if (fieldName.contains(".")) {
/* 140:140 */      setNestedField(target, fieldName, value);
/* 141:    */    } else {
/* 142:142 */      setDirectField(target, fieldName, value);
/* 143:    */    }
/* 144:    */  }
/* 145:    */  
/* 154:    */  public static List<Field> getAllDeclaredField(Class<?> targetClass, String... excludeFieldNames)
/* 155:    */  {
/* 156:156 */    List<Field> fields = new ArrayList();
/* 157:157 */    if (targetClass != null) {
/* 158:158 */      for (Field field : targetClass.getDeclaredFields()) {
/* 159:159 */        if (!CollectionUtils.contains(excludeFieldNames, field.getName()).booleanValue())
/* 160:    */        {
/* 162:162 */          fields.add(field); }
/* 163:    */      }
/* 164:164 */      Object parentClass = targetClass.getSuperclass();
/* 165:165 */      if (parentClass != Object.class) {
/* 166:166 */        fields.addAll(getAllDeclaredField((Class)parentClass, excludeFieldNames));
/* 167:    */      }
/* 168:    */    }
/* 169:169 */    return fields;
/* 170:    */  }
/* 171:    */  
/* 181:    */  public static <T> void copyFields(T source, T target)
/* 182:    */  {
/* 183:183 */    copyFields(source, target, null, null);
/* 184:    */  }
/* 185:    */  
/* 197:    */  public static <T> void copyFields(T source, T target, String excludeFields)
/* 198:    */  {
/* 199:199 */    copyFields(source, target, null, excludeFields);
/* 200:    */  }
/* 201:    */  
/* 208:    */  public static <T> void copyFields(T source, T target, String includeFields, String excludeFields)
/* 209:    */  {
/* 210:    */    String[] includeFieldNames;
/* 211:    */    
/* 217:217 */    if (source != null) {
/* 218:218 */      includeFieldNames = new String[0];
/* 219:219 */      if (!StringUtils.isBlank(includeFields)) {
/* 220:220 */        includeFieldNames = includeFields.split(",");
/* 221:    */      }
/* 222:222 */      String[] excludeFieldNames = new String[0];
/* 223:223 */      if (!StringUtils.isBlank(excludeFields)) {
/* 224:224 */        excludeFieldNames = excludeFields.split(",");
/* 225:    */      }
/* 226:226 */      for (Field field : getAllDeclaredField(source.getClass(), excludeFieldNames)) {
/* 227:227 */        if (CollectionUtils.contains(includeFieldNames, field.getName()).booleanValue()) {
/* 228:228 */          copyField(source, target, field.getName(), Boolean.valueOf(true));
/* 229:    */        } else {
/* 230:230 */          copyField(source, target, field.getName(), Boolean.valueOf(false));
/* 231:    */        }
/* 232:    */      }
/* 233:    */    }
/* 234:    */  }
/* 235:    */  
/* 250:    */  public static <T> void copyField(T source, T target, String fieldName, Boolean containedNull)
/* 251:    */  {
/* 252:252 */    Object sourceFieldValue = getField(source, fieldName);
/* 253:253 */    Field targetField = findField(target.getClass(), fieldName);
/* 254:254 */    Boolean needCopy = Boolean.valueOf((targetField != null) && (!Modifier.isFinal(targetField.getModifiers())) && 
/* 255:255 */      (!Modifier.isStatic(targetField.getModifiers())));
/* 256:256 */    if ((!containedNull.booleanValue()) && (sourceFieldValue == null)) {
/* 257:257 */      needCopy = Boolean.valueOf(false);
/* 258:    */    }
/* 259:259 */    if (needCopy.booleanValue())
/* 260:    */    {
/* 261:261 */      if ((sourceFieldValue != null) && (Collection.class.isAssignableFrom(sourceFieldValue.getClass()))) {
/* 262:262 */        if ((!((Collection)sourceFieldValue).isEmpty()) || (containedNull.booleanValue())) {
/* 263:263 */          CollectionUtils.copy((Collection)sourceFieldValue, 
/* 264:264 */            (Collection)getField(target, fieldName));
/* 265:    */        }
/* 266:    */      } else {
/* 267:267 */        setField(target, fieldName, sourceFieldValue);
/* 268:    */      }
/* 269:    */    }
/* 270:    */  }
/* 271:    */  
/* 278:    */  public static Class<?> getGenericFieldType(Field field)
/* 279:    */  {
/* 280:280 */    Type type = ((ParameterizedType)field.getGenericType()).getActualTypeArguments()[0];
/* 281:281 */    if ((type instanceof ParameterizedType)) {
/* 282:282 */      return (Class)((ParameterizedType)type).getRawType();
/* 283:    */    }
/* 284:284 */    return (Class)type;
/* 285:    */  }
/* 286:    */  
/* 295:    */  public static void copyProperties(Object bean, Map<String, Object> map)
/* 296:    */  {
/* 297:297 */    copyProperties(bean, map, null);
/* 298:    */  }
/* 299:    */  
/* 309:    */  public static void copyProperties(Object bean, Map<String, Object> map, String excludeFields)
/* 310:    */  {
				 
/* 311:311 */    if ((bean != null) && (map != null)) {
/* 312:    */      try {
/* 313:313 */         String[] excludeFieldNames = new String[0];
/* 314:314 */        if (!StringUtils.isBlank(excludeFields)) {
/* 315:315 */          excludeFieldNames = excludeFields.split(",");
/* 316:    */        }
/* 317:317 */        for (Field field : getAllDeclaredField(bean.getClass(), excludeFieldNames)) {
/* 318:318 */          if (!CollectionUtils.contains(excludeFieldNames, field.getName()).booleanValue())
/* 319:319 */            map.put(field.getName(), getField(bean, field));
/* 320:    */        }
/* 321:    */      } catch (Exception e) {
/* 322:    */        String[] excludeFieldNames;
/* 323:323 */        throw new UncheckedException("复制Bean对象属性到Map对象时发生异常。", e);
/* 324:    */      }
/* 325:    */    }
/* 326:    */  }
/* 327:    */  
/* 335:    */  public static void copyProperties(Map<String, Object> map, Object bean)
/* 336:    */  {
/* 337:337 */    copyProperties(map, bean, null);
/* 338:    */  }
/* 339:    */  
/* 349:    */  public static void copyProperties(Map<String, Object> map, Object bean, String excludeFields)
/* 350:    */  {
/* 351:    */    try
/* 352:    */    {
/* 353:353 */      if ((map != null) && (bean != null) && (StringUtils.isNotBlank(excludeFields))) {
/* 354:354 */        String[] excludeFieldNames = excludeFields.split(",");
/* 355:355 */        for (Map.Entry<String, Object> entity : map.entrySet()) {
/* 356:356 */          Field field = findField(bean.getClass(), (String)entity.getKey());
/* 357:357 */          if ((field != null) && (!CollectionUtils.contains(excludeFieldNames, entity.getKey()).booleanValue()))
/* 358:358 */            setField(bean, field, entity.getValue());
/* 359:    */        }
/* 360:    */      }
/* 361:    */    } catch (Exception e) {
/* 362:    */      String[] excludeFieldNames;
/* 363:363 */      throw new UncheckedException("复制Map对象属性到Bean对象时发生异常。", e);
/* 364:    */    }
/* 365:    */  }
/* 366:    */  
/* 375:    */  private static Field findDirectField(Class<?> targetClass, String fieldName)
/* 376:    */  {
/* 377:377 */    for (Field field : getAllDeclaredField(targetClass, new String[0])) {
/* 378:378 */      if ((field != null) && (fieldName != null) && (fieldName.equals(field.getName()))) {
/* 379:379 */        return field;
/* 380:    */      }
/* 381:    */    }
/* 382:382 */    return null;
/* 383:    */  }
/* 384:    */  
/* 393:    */  private static Field findNestedField(Class<?> targetClass, String fieldName)
/* 394:    */  {
/* 395:395 */    Field field = null;
/* 396:396 */    if (StringUtils.isNotBlank(fieldName)) {
/* 397:397 */      String[] nestedFieldNames = fieldName.split("\\.");
/* 398:398 */      for (String nestedFieldName : nestedFieldNames) {
/* 399:399 */        field = findDirectField(targetClass, nestedFieldName);
/* 400:    */      }
/* 401:    */    }
/* 402:    */    
/* 403:403 */    return field;
/* 404:    */  }
/* 405:    */  
/* 414:    */  private static Object getDirectField(Object target, String fieldName)
/* 415:    */  {
/* 416:416 */    if ((target != null) && (StringUtils.isNotBlank(fieldName))) {
/* 417:417 */      return getField(target, findDirectField(target.getClass(), fieldName));
/* 418:    */    }
/* 419:    */    
/* 420:420 */    return null;
/* 421:    */  }
/* 422:    */  
/* 431:    */  private static Object getNestedField(Object target, String fieldName)
/* 432:    */  {
/* 433:433 */    Assert.notNull(target);
/* 434:434 */    Assert.notNull(fieldName);
/* 435:435 */    String[] nestedFieldNames = fieldName.split("\\.");
/* 436:436 */    for (String nestedFieldName : nestedFieldNames) {
/* 437:437 */      target = getDirectField(target, nestedFieldName);
/* 438:    */    }
/* 439:439 */    return target;
/* 440:    */  }
/* 441:    */  
/* 451:    */  private static void setDirectField(Object target, String fieldName, Object value)
/* 452:    */  {
/* 453:453 */    Assert.notNull(target);
/* 454:454 */    Assert.notNull(fieldName);
/* 455:455 */    setField(target, findDirectField(target.getClass(), fieldName), value);
/* 456:    */  }
/* 457:    */  
/* 467:    */  private static void setNestedField(Object target, String fieldName, Object value)
/* 468:    */  {
/* 469:469 */    String[] nestedFieldNames = StringUtils.substringBeforeLast(fieldName, ".").split("\\.");
/* 470:470 */    for (String nestedFieldName : nestedFieldNames) {
/* 471:471 */      target = getDirectField(target, nestedFieldName);
/* 472:    */    }
/* 473:473 */    setDirectField(target, StringUtils.substringAfterLast(fieldName, "."), value);
/* 474:    */  }
/* 475:    */  
/* 481:    */  private static Object processHibernateLazyField(Object fieldValue)
/* 482:    */  {
/* 483:    */    try
/* 484:    */    {
/* 485:485 */      Class<?> hibernateProxyClass = Class.forName("org.hibernate.proxy.HibernateProxy");
/* 486:486 */      if (hibernateProxyClass.isAssignableFrom(fieldValue.getClass())) {
/* 487:487 */        Method method = fieldValue.getClass().getMethod("getHibernateLazyInitializer", new Class[0]);
/* 488:488 */        Object lazyInitializer = method.invoke(fieldValue, new Object[0]);
/* 489:489 */        method = lazyInitializer.getClass().getMethod("getImplementation", new Class[0]);
/* 490:490 */        return method.invoke(lazyInitializer, new Object[0]);
/* 491:    */      }
/* 492:492 */      return fieldValue;
/* 493:    */    }
/* 494:    */    catch (Exception e) {}
/* 495:495 */    return fieldValue;
/* 496:    */  }
/* 497:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.BeanUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */