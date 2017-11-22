package com.zfk.base.util;
/*   2:    */
/*   4:    */import java.io.File;
/*   5:    */import java.lang.annotation.Annotation;
/*   6:    */import java.lang.reflect.Modifier;
/*   7:    */import java.net.URL;
/*   8:    */import java.net.URLDecoder;
/*   9:    */import java.util.ArrayList;
/*  10:    */import java.util.Enumeration;
/*  11:    */import java.util.List;
/*  12:    */import java.util.jar.JarEntry;
/*  13:    */import java.util.jar.JarFile;
/*  14:    */import org.apache.log4j.Logger;

import com.zfk.base.exceptoin.UncheckedException;
/*  15:    */
/*  28:    */public class ClassUtils
/*  29:    */{
/*  30: 30 */  private static Logger log = Logger.getLogger(ClassUtils.class);
/*  31:    */  
/*  40:    */  public static List<Class<?>> findClassesByParentClass(Class<?> parentClass, String... packageNames)
/*  41:    */  {
/*  42: 42 */    List<Class<?>> classes = new ArrayList();
/*  43: 43 */    for (Class<?> targetClass : findClasses(packageNames)) {
/*  44: 44 */      if ((!targetClass.equals(parentClass)) && (parentClass.isAssignableFrom(targetClass)) && 
/*  45: 45 */        (!Modifier.isAbstract(targetClass.getModifiers()))) {
/*  46: 46 */        classes.add(targetClass);
/*  47:    */      }
/*  48:    */    }
/*  49: 49 */    return classes;
/*  50:    */  }
/*  51:    */  
/*  61:    */  public static List<Class<?>> findClassesByAnnotationClass(Class<? extends Annotation> annotationClass, String... packageNames)
/*  62:    */  {
/*  63: 63 */    List<Class<?>> classes = new ArrayList();
/*  64: 64 */    for (Class<?> targetClass : findClasses(packageNames)) {
/*  65: 65 */      if (targetClass.isAnnotationPresent(annotationClass)) {
/*  66: 66 */        classes.add(targetClass);
/*  67:    */      }
/*  68:    */    }
/*  69: 69 */    return classes;
/*  70:    */  }
/*  71:    */  
/*  78:    */  public static List<Class<?>> findClasses(String... packageNames)
/*  79:    */  {
/*  80: 80 */    List<Class<?>> classes = new ArrayList();
/*  81: 81 */    for (String className : findClassNames(packageNames)) {
/*  82:    */      try {
/*  83: 83 */        classes.add(Class.forName(className));
/*  84:    */      } catch (Exception e) {
/*  85: 85 */        log.warn("加载[" + className + "]类时发生异常。", e);
/*  86:    */      }
/*  87:    */    }
/*  88: 88 */    return classes;
/*  89:    */  }
/*  90:    */  
/*  97:    */  private static List<String> findClassNames(String... packageNames)
/*  98:    */  {
/*  99: 99 */    List<String> classNames = new ArrayList();
/* 100:    */    try {
/* 101:101 */      for (String packageName : packageNames) {
/* 102:102 */        String packagePath = packageName.replace(".", "/");
/* 103:103 */        Enumeration<URL> packageUrls = Thread.currentThread().getContextClassLoader().getResources(packagePath);
/* 104:104 */        while (packageUrls.hasMoreElements()) {
/* 105:105 */          URL packageUrl = (URL)packageUrls.nextElement();
/* 106:106 */          if ("jar".equals(packageUrl.getProtocol())) {
/* 107:107 */            classNames.addAll(getClassNamesFromJar(packageUrl, packageName));
/* 108:    */          } else {
/* 109:109 */            classNames.addAll(getClassNamesFromDir(packageUrl, packageName));
/* 110:    */          }
/* 111:    */        }
/* 112:    */      }
/* 113:    */    } catch (Exception e) {
/* 114:114 */      throw new UncheckedException("获取指定包下类名集合时发生异常。", e);
/* 115:    */    }
/* 116:116 */    return classNames;
/* 117:    */  }
/* 118:    */  
/* 127:    */  private static List<String> getClassNamesFromJar(URL url, String packageName)
/* 128:    */  {
/* 129:129 */    List<String> classNames = new ArrayList();
/* 130:    */    try {
/* 131:131 */      String jarPath = URLDecoder.decode(url.toExternalForm(), "UTF-8");
/* 132:132 */      if (log.isDebugEnabled()) {
/* 133:133 */        log.debug("开始获取[" + jarPath + "]中的类名...");
/* 134:    */      }
/* 135:135 */      jarPath = StringUtils.substringAfter(jarPath, "jar:file:");
/* 136:136 */      jarPath = StringUtils.substringBeforeLast(jarPath, "!");
/* 137:137 */      JarFile jarInputStream = new JarFile(jarPath);
/* 138:    */      try {
/* 139:139 */        Enumeration<JarEntry> jarEntries = jarInputStream.entries();
/* 140:140 */        while (jarEntries.hasMoreElements()) {
/* 141:141 */          JarEntry jarEntry = (JarEntry)jarEntries.nextElement();
/* 142:142 */          classNames.addAll(getClassNamesFromJar(jarEntry, packageName));
/* 143:    */        }
/* 144:    */      } finally {
/* 145:145 */        jarInputStream.close();
/* 146:    */      }
/* 147:    */    } catch (Exception e) {
/* 148:148 */      log.warn("获取jar包中的类名时发生异常。", e);
/* 149:    */    }
/* 150:150 */    return classNames;
/* 151:    */  }
/* 152:    */  
/* 161:    */  private static List<String> getClassNamesFromJar(JarEntry jarEntry, String packageName)
/* 162:    */  {
/* 163:163 */    List<String> classNames = new ArrayList();
/* 164:164 */    if ((!jarEntry.isDirectory()) && (jarEntry.getName().endsWith(".class"))) {
/* 165:165 */      String className = jarEntry.getName();
/* 166:166 */      className = className.replaceFirst(".class$", "");
/* 167:167 */      className = className.replace('/', '.');
/* 168:168 */      if (className.contains(packageName)) {
/* 169:169 */        classNames.add(className);
/* 170:    */      }
/* 171:    */    }
/* 172:172 */    return classNames;
/* 173:    */  }
/* 174:    */  
/* 182:    */  private static List<String> getClassNamesFromDir(URL url, String packageName)
/* 183:    */  {
/* 184:    */    try
/* 185:    */    {
/* 186:186 */      String dirPath = URLDecoder.decode(url.getFile(), "UTF-8");
/* 187:187 */      if (log.isDebugEnabled()) {
/* 188:188 */        log.debug("开始获取[" + dirPath + "]中的类名...");
/* 189:    */      }
/* 190:190 */      return getClassNamesFromDir(new File(dirPath), packageName);
/* 191:    */    } catch (Exception e) {
/* 192:192 */      throw new UncheckedException("从目录中获取类名时发生异常。", e);
/* 193:    */    }
/* 194:    */  }
/* 195:    */  
/* 204:    */  private static List<String> getClassNamesFromDir(File dir, String packageName)
/* 205:    */  {
/* 206:206 */    List<String> classNames = new ArrayList();
/* 207:    */    try {
/* 208:208 */      File[] files = dir.listFiles();
/* 209:209 */      String separator = System.getProperty("file.separator");
/* 210:210 */      for (File file : files) {
/* 211:211 */        if (file.isDirectory()) {
/* 212:212 */          classNames.addAll(getClassNamesFromDir(file, packageName + "." + file.getName()));
/* 213:213 */        } else if (file.getName().endsWith(".class")) {
/* 214:214 */          String className = file.getPath();
/* 215:215 */          className = className.replace(separator, ".");
/* 216:216 */          className = packageName + StringUtils.substringAfterLast(className, packageName);
/* 217:217 */          className = className.replaceFirst(".class$", "");
/* 218:218 */          classNames.add(className);
/* 219:    */        }
/* 220:    */      }
/* 221:    */    } catch (Exception e) {
/* 222:222 */      log.warn("获取目录中的类名时发生异常。", e);
/* 223:    */    }
/* 224:224 */    return classNames;
/* 225:    */  }
/* 226:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.ClassUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */