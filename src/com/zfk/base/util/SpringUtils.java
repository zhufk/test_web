package com.zfk.base.util;
/*   2:    */
/*   4:    */import java.net.URI;
/*   5:    */import java.util.ArrayList;
/*   6:    */import java.util.List;
/*   7:    */import org.springframework.context.ApplicationContext;
/*   8:    */import org.springframework.context.ApplicationContextAware;
/*   9:    */import org.springframework.context.annotation.Lazy;
/*  10:    */import org.springframework.core.io.FileSystemResource;
/*  11:    */import org.springframework.core.io.Resource;
/*  12:    */import org.springframework.core.io.UrlResource;
/*  13:    */import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
/*  14:    */import org.springframework.core.io.support.ResourcePatternResolver;
/*  15:    */import org.springframework.stereotype.Component;

import com.zfk.base.exceptoin.UncheckedException;
/*  16:    */
/*  23:    */@Component
/*  24:    */@Lazy(false)
/*  25:    */public class SpringUtils
/*  26:    */  implements ApplicationContextAware
/*  27:    */{
/*  28:    */  private static ApplicationContext context;
/*  29:    */  
/*  30:    */  public void setApplicationContext(ApplicationContext context)
/*  31:    */  {
/*  32: 32 */    context = context;
/*  33:    */  }
/*  34:    */  
/*  39:    */  public static ApplicationContext getContext()
/*  40:    */  {
/*  41: 41 */    return context;
/*  42:    */  }
/*  43:    */  
/*  53:    */  public static <T> T getBean(String beanName)
/*  54:    */  {
/*  55: 55 */    return (T) context.getBean(beanName);
/*  56:    */  }
/*  57:    */  
/*  66:    */  public static <T> T getBean(Class<T> beanType)
/*  67:    */  {
/*  68: 68 */    return context.getBean(beanType);
/*  69:    */  }
/*  70:    */  
/*  77:    */  public static List<Resource> getResourcesByWildcard(String... wildcardResourcePaths)
/*  78:    */  {
/*  79: 79 */    List<Resource> resources = new ArrayList();
/*  80:    */    try {
/*  81: 81 */      ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
/*  82: 82 */      for (String basename : wildcardResourcePaths) {
/*  83: 83 */        for (Resource resource : resourcePatternResolver.getResources(basename)) {
/*  84: 84 */          resources.add(resource);
/*  85:    */        }
/*  86:    */      }
/*  87:    */    } catch (Exception e) {
/*  88: 88 */      throw new UncheckedException("获取资源列表时反生异常。", e);
/*  89:    */    }
/*  90: 90 */    return resources;
/*  91:    */  }
/*  92:    */  
/* 101:    */  public static List<String> getResourcePathsByWildcard(String resourceDir, String... wildcardResourcePaths)
/* 102:    */  {
/* 103:103 */    List<String> resourcePaths = new ArrayList();
/* 104:    */    try {
/* 105:105 */      for (Resource resource : getResourcesByWildcard(wildcardResourcePaths)) {
/* 106:106 */        String uri = resource.getURI().toString();
/* 107:107 */        if (((resource instanceof FileSystemResource)) || ((resource instanceof UrlResource))) {
/* 108:108 */          String resourcePath = "classpath:" + resourceDir + StringUtils.substringAfter(uri, resourceDir);
/* 109:109 */          resourcePaths.add(resourcePath);
/* 110:    */        }
/* 111:    */      }
/* 112:    */    } catch (Exception e) {
/* 113:113 */      throw new UncheckedException("获取资源文件时发生异常。", e);
/* 114:    */    }
/* 115:115 */    return resourcePaths;
/* 116:    */  }
/* 117:    */  
/* 126:    */  public static List<String> getResourceBasenamesByWildcard(String resourceDir, String... wildcardResourcePaths)
/* 127:    */  {
/* 128:128 */    List<String> resourceBasenames = new ArrayList();
/* 129:129 */    for (String resourcePath : getResourcePathsByWildcard(resourceDir, wildcardResourcePaths)) {
/* 130:130 */      resourceBasenames.add(StringUtils.substringBeforeLast(resourcePath, "."));
/* 131:    */    }
/* 132:132 */    return resourceBasenames;
/* 133:    */  }
/* 134:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.SpringUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */