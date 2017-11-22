package com.zfk.base.util;
/*   2:    */
/*   3:    */import java.lang.reflect.Method;
/*   4:    */import java.net.URLDecoder;
/*   5:    */import java.net.URLEncoder;
/*   6:    */import java.util.ArrayList;
/*   7:    */import java.util.List;
/*   8:    */import javax.servlet.http.HttpServletRequest;
/*   9:    */import org.apache.commons.lang3.StringUtils;
/*  10:    */import org.springframework.web.bind.annotation.RequestMapping;
/*  11:    */import org.springframework.web.context.request.RequestContextHolder;
/*  12:    */import org.springframework.web.context.request.ServletRequestAttributes;
/*  13:    */
/*  28:    */public class URIUtils
/*  29:    */{
/*  30:    */  public static final String HTTP = "http://";
/*  31:    */  public static final String HTTPS = "https://";
/*  32:    */  
/*  33:    */  public static String getCurrentURL(String currentUrl, String contextName)
/*  34:    */  {
/*  35: 35 */    if ((StringUtils.isNotBlank(currentUrl)) && (currentUrl.startsWith(contextName + "/"))) {
/*  36: 36 */      currentUrl = currentUrl.substring(currentUrl.indexOf(contextName) + contextName.length());
/*  37:    */    }
/*  38:    */    
/*  39: 39 */    return currentUrl;
/*  40:    */  }
/*  41:    */  
/*  47:    */  public static String getCurrentURI(String currentUrl, String contextName)
/*  48:    */  {
/*  49: 49 */    currentUrl = getCurrentURL(currentUrl, contextName);
/*  50: 50 */    currentUrl = getTagURI(currentUrl);
/*  51:    */    
/*  52: 52 */    return currentUrl;
/*  53:    */  }
/*  54:    */  
/*  59:    */  public static String getTagURI(String url)
/*  60:    */  {
/*  61: 61 */    if ((StringUtils.isNotBlank(url)) && (url.startsWith("/"))) {
/*  62: 62 */      url = url.replaceFirst("/", "");
/*  63:    */    }
/*  64: 64 */    url = url.replaceAll("/", ":");
/*  65: 65 */    return url;
/*  66:    */  }
/*  67:    */  
/*  72:    */  public static String removeFirstAndLastSlash(String url)
/*  73:    */  {
/*  74: 74 */    if ((StringUtils.isNotBlank(url)) && (url.startsWith("/"))) {
/*  75: 75 */      url = url.replaceFirst("/", "");
/*  76:    */    }
/*  77:    */    
/*  78: 78 */    if (url.endsWith("/")) {
/*  79: 79 */      url = url.substring(0, url.length() - 2);
/*  80:    */    }
/*  81:    */    
/*  82: 82 */    return url;
/*  83:    */  }
/*  84:    */  
/*  90:    */  public static List<String> getUrlMappings(Method method)
/*  91:    */  {
/*  92: 92 */    List<String> cUrls = new ArrayList();
/*  93: 93 */    RequestMapping cMapping = (RequestMapping)method.getDeclaringClass().getAnnotation(RequestMapping.class);
/*  94: 94 */    RequestMapping mMapping = (RequestMapping)method.getAnnotation(RequestMapping.class);
/*  95:    */    
/*  96: 96 */    String[] cValues = null;
/*  97: 97 */    if (cMapping != null) {
/*  98: 98 */      cValues = cMapping.value();
/*  99:    */    }
/* 100:100 */    String[] mValues = mMapping.value();
/* 101:101 */    for (String mv : mValues)
/* 102:    */    {
/* 103:103 */      mv = removeFirstAndLastSlash(mv);
/* 104:104 */      String url = "/";
/* 105:105 */      if (cValues != null) {
/* 106:106 */        for (String cv : cValues) {
/* 107:107 */          cv = removeFirstAndLastSlash(cv);
/* 108:108 */          url = url + cv + "/" + mv;
/* 109:109 */          cUrls.add(url);
/* 110:110 */          url = "/";
/* 111:    */        }
/* 112:    */      } else {
/* 113:113 */        url = url + mv;
/* 114:114 */        cUrls.add(url);
/* 115:    */      }
/* 116:    */    }
/* 117:    */    
/* 118:118 */    return cUrls;
/* 119:    */  }
/* 120:    */  
/* 124:    */  public static String urlDecodeUTF8(String input)
/* 125:    */  {
/* 126:    */    try
/* 127:    */    {
/* 128:128 */      return input == null ? null : URLDecoder.decode(input, "UTF-8");
/* 129:    */    } catch (Exception e) {
/* 130:130 */      throw new RuntimeException(e);
/* 131:    */    }
/* 132:    */  }
/* 133:    */  
/* 137:    */  public static String urlEncodeUTF8(String input)
/* 138:    */  {
/* 139:    */    try
/* 140:    */    {
/* 141:141 */      return input == null ? null : URLEncoder.encode(input, "UTF-8");
/* 142:    */    } catch (Exception e) {
/* 143:143 */      throw new RuntimeException(e);
/* 144:    */    }
/* 145:    */  }
/* 146:    */  
/* 151:    */  public static String getDomainName(String url)
/* 152:    */  {
/* 153:153 */    if (StringUtils.isNotBlank(url)) {
/* 154:154 */      if (url.contains("https://")) {
/* 155:155 */        url = url.replaceFirst("https://", "");
/* 156:156 */      } else if (url.contains("http://")) {
/* 157:157 */        url = url.replaceFirst("http://", "");
/* 158:    */      }
/* 159:    */      
/* 160:160 */      url = url.substring(0, url.indexOf("/"));
/* 161:    */    }
/* 162:    */    
/* 163:163 */    return url;
/* 164:    */  }
/* 165:    */  
/* 169:    */  public static String getUrl()
/* 170:    */  {
/* 171:171 */    HttpServletRequest hreq = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
/* 172:172 */    String url = hreq.getRequestURI();
/* 173:173 */    return url;
/* 174:    */  }
/* 175:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.URIUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */