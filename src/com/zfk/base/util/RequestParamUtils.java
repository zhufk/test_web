package com.zfk.base.util;
/*   2:    */
/*   3:    */import java.io.IOException;
/*   4:    */import java.lang.annotation.Annotation;
/*   5:    */import java.lang.reflect.Method;
/*   6:    */import java.util.Enumeration;
/*   7:    */import java.util.HashMap;
/*   8:    */import java.util.Map;
/*   9:    */import javax.servlet.ServletInputStream;
/*  10:    */import javax.servlet.http.HttpServletRequest;
/*  11:    */import org.slf4j.Logger;
/*  12:    */import org.slf4j.LoggerFactory;
/*  13:    */import org.springframework.web.bind.annotation.RequestBody;
/*  14:    */
/*  20:    */public class RequestParamUtils
/*  21:    */{
/*  22: 22 */  private static final Logger logger = LoggerFactory.getLogger(RequestParamUtils.class);
/*  23:    */  
/*  25:    */  public static Map<String, Object> requestParamsConvertor(HttpServletRequest request, Method method, Object[] params)
/*  26:    */    throws Exception
/*  27:    */  {
/*  28: 28 */    Map<String, Object> paramsMap = new HashMap();
/*  29:    */    
/*  30: 30 */    Enumeration<String> dispParamNames = request.getParameterNames();
/*  31:    */    
/*  32: 32 */    if (dispParamNames.hasMoreElements()) {
/*  33: 33 */      while (dispParamNames.hasMoreElements()) {
/*  34: 34 */        String dispParamName = (String)dispParamNames.nextElement();
/*  35: 35 */        paramsMap.put(dispParamName, request.getParameter(dispParamName));
/*  36:    */      }
/*  37: 37 */      return paramsMap;
/*  38:    */    }
/*  39: 39 */    Annotation[][] annotations = method.getParameterAnnotations();
/*  40: 40 */    if (annotations != null) {
/*  41: 41 */      for (int i = 0; i < annotations.length; i++) {
/*  42: 42 */        for (int j = 0; j < annotations[i].length; j++) {
/*  43: 43 */          Annotation annotation = annotations[i][j];
/*  44: 44 */          if ((annotation != null) && 
/*  45: 45 */            ((annotation instanceof RequestBody))) {
/*  46: 46 */            if ((params[i] instanceof Map)) {
/*  47: 47 */              paramsMap = (Map)params[i];break;
/*  48:    */            }
/*  49:    */            try {
/*  50: 50 */              ConvertUtils.objectToMap(params[i], paramsMap);
/*  51:    */            } catch (Exception e) {
/*  52: 52 */              logger.error(" RequestParamUtils 转换异常 params[i]=" + params[i]);
/*  53: 53 */              return null;
/*  54:    */            }
/*  55:    */          }
/*  56:    */        }
/*  57:    */      }
/*  58:    */    }
/*  59:    */    
/*  62: 62 */    return paramsMap;
/*  63:    */  }
/*  64:    */  
/*  72:    */  private static String requestParamStrHandler(HttpServletRequest request)
/*  73:    */    throws IOException
/*  74:    */  {
/*  75: 75 */    String submitMehtod = request.getMethod();
/*  76:    */    
/*  77: 77 */    if (submitMehtod.equals("GET"))
/*  78: 78 */      return new String(request.getQueryString().getBytes("iso-8859-1"), "utf-8").replaceAll("%22", "\"");
/*  79: 79 */    if (submitMehtod.equals("POST")) {
/*  80: 80 */      return getRequestPostStr(request);
/*  81:    */    }
/*  82:    */    
/*  84: 84 */    return null;
/*  85:    */  }
/*  86:    */  
/*  96:    */  private static byte[] getRequestPostBytes(HttpServletRequest request)
/*  97:    */    throws IOException
/*  98:    */  {
/*  99: 99 */    int contentLength = request.getContentLength();
/* 100:100 */    if (contentLength < 0) {
/* 101:101 */      return null;
/* 102:    */    }
/* 103:103 */    byte[] buffer = new byte[contentLength];
/* 104:104 */    for (int i = 0; i < contentLength;)
/* 105:    */    {
/* 106:106 */      int readlen = request.getInputStream().read(buffer, i, contentLength - i);
/* 107:107 */      if (readlen == -1) {
/* 108:    */        break;
/* 109:    */      }
/* 110:110 */      i += readlen;
/* 111:    */    }
/* 112:112 */    request.getInputStream().close();
/* 113:113 */    return buffer;
/* 114:    */  }
/* 115:    */  
/* 125:    */  private static String getRequestPostStr(HttpServletRequest request)
/* 126:    */    throws IOException
/* 127:    */  {
/* 128:128 */    byte[] buffer = getRequestPostBytes(request);
/* 129:129 */    String charEncoding = request.getCharacterEncoding();
/* 130:130 */    if (charEncoding == null) {
/* 131:131 */      charEncoding = "UTF-8";
/* 132:    */    }
/* 133:133 */    return new String(buffer, charEncoding);
/* 134:    */  }
/* 135:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.RequestParamUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */