package com.zfk.base.util;
/*   2:    */
/*   3:    */import java.io.UnsupportedEncodingException;
/*   4:    */import java.util.Enumeration;
/*   5:    */import java.util.Iterator;
/*   6:    */import java.util.Map;
/*   7:    */import java.util.Map.Entry;
/*   8:    */import java.util.Set;
/*   9:    */import java.util.StringTokenizer;
/*  10:    */import java.util.TreeMap;
/*  11:    */import javax.servlet.ServletRequest;
/*  12:    */import javax.servlet.http.HttpServletRequest;
/*  13:    */import javax.servlet.http.HttpServletResponse;
/*  14:    */import org.apache.commons.lang3.StringUtils;
/*  15:    */import org.apache.commons.lang3.Validate;
/*  16:    */import org.slf4j.Logger;
/*  17:    */import org.slf4j.LoggerFactory;
/*  18:    */import org.springframework.web.context.request.RequestContextHolder;
/*  19:    */import org.springframework.web.context.request.ServletRequestAttributes;
/*  20:    */
/*  34:    */public class ServletUtils
/*  35:    */{
/*  36: 36 */  private static final Logger logger = LoggerFactory.getLogger(ServletUtils.class);
/*  37:    */  
/*  39:    */  public static final long ONE_YEAR_SECONDS = 31536000L;
/*  40:    */  
/*  42:    */  public static final String KEY_STATIC_FILE = "static.file";
/*  43:    */  
/*  45:    */  public static final String KEY_VIEW_PREFIX = "view.prefix";
/*  46:    */  
/*  47:    */  public static final String ACTION_PAGE = "/page";
/*  48:    */  
/*  49: 49 */  private static final String[] STATIC_FILES = StringUtils.split(PropertyUtils.getString("static.file"), ",");
/*  50:    */  
/*  54:    */  public static void setExpiresHeader(HttpServletResponse response, long expiresSeconds)
/*  55:    */  {
/*  56: 56 */    response.setDateHeader("Expires", System.currentTimeMillis() + expiresSeconds * 1000L);
/*  57:    */    
/*  58: 58 */    response.setHeader("Cache-Control", new StringBuilder().append("private, max-age=").append(expiresSeconds).toString());
/*  59:    */  }
/*  60:    */  
/*  64:    */  public static void setNoCacheHeader(HttpServletResponse response)
/*  65:    */  {
/*  66: 66 */    response.setDateHeader("Expires", 1L);
/*  67: 67 */    response.addHeader("Pragma", "no-cache");
/*  68:    */    
/*  69: 69 */    response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
/*  70:    */  }
/*  71:    */  
/*  74:    */  public static void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate)
/*  75:    */  {
/*  76: 76 */    response.setDateHeader("Last-Modified", lastModifiedDate);
/*  77:    */  }
/*  78:    */  
/*  81:    */  public static void setEtag(HttpServletResponse response, String etag)
/*  82:    */  {
/*  83: 83 */    response.setHeader("ETag", etag);
/*  84:    */  }
/*  85:    */  
/*  94:    */  public static boolean checkIfModifiedSince(HttpServletRequest request, HttpServletResponse response, long lastModified)
/*  95:    */  {
/*  96: 96 */    long ifModifiedSince = request.getDateHeader("If-Modified-Since");
/*  97: 97 */    if ((ifModifiedSince != -1L) && (lastModified < ifModifiedSince + 1000L)) {
/*  98: 98 */      response.setStatus(304);
/*  99: 99 */      return false;
/* 100:    */    }
/* 101:101 */    return true;
/* 102:    */  }
/* 103:    */  
/* 111:    */  public static boolean checkIfNoneMatchEtag(HttpServletRequest request, HttpServletResponse response, String etag)
/* 112:    */  {
/* 113:113 */    String headerValue = request.getHeader("If-None-Match");
/* 114:114 */    if (headerValue != null) {
/* 115:115 */      boolean conditionSatisfied = false;
/* 116:116 */      if (!"*".equals(headerValue)) {
/* 117:117 */        StringTokenizer commaTokenizer = new StringTokenizer(headerValue, ",");
/* 118:    */        
/* 119:119 */        while ((!conditionSatisfied) && (commaTokenizer.hasMoreTokens())) {
/* 120:120 */          String currentToken = commaTokenizer.nextToken();
/* 121:121 */          if (currentToken.trim().equals(etag)) {
/* 122:122 */            conditionSatisfied = true;
/* 123:    */          }
/* 124:    */        }
/* 125:    */      } else {
/* 126:126 */        conditionSatisfied = true;
/* 127:    */      }
/* 128:    */      
/* 129:129 */      if (conditionSatisfied) {
/* 130:130 */        response.setStatus(304);
/* 131:131 */        response.setHeader("ETag", etag);
/* 132:132 */        return false;
/* 133:    */      }
/* 134:    */    }
/* 135:135 */    return true;
/* 136:    */  }
/* 137:    */  
/* 143:    */  public static void setFileDownloadHeader(HttpServletResponse response, String fileName)
/* 144:    */  {
/* 145:    */    try
/* 146:    */    {
/* 147:147 */      String encodedfileName = new String(fileName.getBytes(), "ISO8859-1");
/* 148:148 */      response.setHeader("Content-Disposition", new StringBuilder().append("attachment; filename=\"").append(encodedfileName).append("\"").toString());
/* 149:    */    } catch (UnsupportedEncodingException e) {
/* 150:150 */      e.getMessage();
/* 151:    */    }
/* 152:    */  }
/* 153:    */  
/* 159:    */  public static Map<String, Object> getParametersStartingWith(ServletRequest request, String prefix)
/* 160:    */  {
/* 161:161 */    Validate.notNull(request, "Request must not be null", new Object[0]);
/* 162:162 */    Enumeration paramNames = request.getParameterNames();
/* 163:163 */    Map<String, Object> params = new TreeMap();
/* 164:164 */    String pre = prefix;
/* 165:165 */    if (pre == null) {
/* 166:166 */      pre = "";
/* 167:    */    }
/* 168:168 */    while ((paramNames != null) && (paramNames.hasMoreElements())) {
/* 169:169 */      String paramName = (String)paramNames.nextElement();
/* 170:170 */      if (("".equals(pre)) || (paramName.startsWith(pre))) {
/* 171:171 */        String unprefixed = paramName.substring(pre.length());
/* 172:172 */        String[] values = request.getParameterValues(paramName);
/* 173:173 */        if ((values == null) || (values.length == 0)) {
/* 174:174 */          values = new String[0];
/* 175:    */        }
/* 176:176 */        else if (values.length > 1) {
/* 177:177 */          params.put(unprefixed, values);
/* 178:    */        } else {
/* 179:179 */          params.put(unprefixed, values[0]);
/* 180:    */        }
/* 181:    */      }
/* 182:    */    }
/* 183:183 */    return params;
/* 184:    */  }
/* 185:    */  
/* 189:    */  public static String encodeParameterStringWithPrefix(Map<String, Object> params, String prefix)
/* 190:    */  {
/* 191:191 */    StringBuilder queryStringBuilder = new StringBuilder();
/* 192:    */    
/* 193:193 */    String pre = prefix;
/* 194:194 */    if (pre == null) {
/* 195:195 */      pre = "";
/* 196:    */    }
/* 197:197 */    Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator();
/* 198:198 */    while (it.hasNext()) {
/* 199:199 */      Map.Entry<String, Object> entry = (Map.Entry)it.next();
/* 200:200 */      queryStringBuilder.append(pre).append((String)entry.getKey()).append("=").append(entry.getValue());
/* 201:201 */      if (it.hasNext()) {
/* 202:202 */        queryStringBuilder.append("&");
/* 203:    */      }
/* 204:    */    }
/* 205:205 */    return queryStringBuilder.toString();
/* 206:    */  }
/* 207:    */  
/* 211:    */  public static HttpServletRequest getRequest()
/* 212:    */  {
/* 213:    */    try
/* 214:    */    {
/* 215:215 */      return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
/* 216:    */    } catch (Exception e) {}
/* 217:217 */    return null;
/* 218:    */  }
/* 219:    */  
/* 225:    */  public static boolean isStaticFile(String uri)
/* 226:    */  {
/* 227:227 */    if (STATIC_FILES == null) {
/* 228:228 */      logger.error("config.static.file.empty");
/* 229:    */    }
/* 230:230 */    if (StringUtils.endsWithAny(uri, STATIC_FILES)) {
/* 231:231 */      return true;
/* 232:    */    }
/* 233:233 */    return false;
/* 234:    */  }
/* 235:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.ServletUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */