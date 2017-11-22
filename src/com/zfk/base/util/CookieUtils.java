package com.zfk.base.util;
/*  2:   */
/*  3:   */import javax.servlet.http.Cookie;
/*  4:   */import javax.servlet.http.HttpServletRequest;
/*  5:   */import javax.servlet.http.HttpServletResponse;
/*  6:   */
/* 17:   */public class CookieUtils
/* 18:   */{
/* 19:   */  public static String getCookie(String key, HttpServletRequest request)
/* 20:   */  {
/* 21:21 */    String value = null;
/* 22:22 */    if (request != null) {
/* 23:23 */      Cookie[] cookies = request.getCookies();
/* 24:24 */      if (cookies != null) {
/* 25:25 */        for (Cookie c : cookies) {
/* 26:26 */          if (c.getName().equalsIgnoreCase(key)) {
/* 27:27 */            value = c.getValue();
/* 28:28 */            break;
/* 29:   */          }
/* 30:   */        }
/* 31:   */      }
/* 32:   */    }
/* 33:33 */    return value;
/* 34:   */  }
/* 35:   */  
/* 43:   */  public static void setCookie(String key, String value, int timeOut, HttpServletResponse response)
/* 44:   */  {
/* 45:45 */    if (response != null) {
/* 46:46 */      Cookie cookie = new Cookie(key, value);
/* 47:47 */      cookie.setPath("/");
/* 48:48 */      cookie.setMaxAge(timeOut);
/* 49:49 */      response.addCookie(cookie);
/* 50:   */    }
/* 51:   */  }
/* 52:   */  
/* 59:   */  public static void clearCookie(String key, HttpServletRequest request, HttpServletResponse response)
/* 60:   */  {
/* 61:61 */    if ((request == null) || (response == null)) {
/* 62:62 */      return;
/* 63:   */    }
/* 64:64 */    Cookie[] cookies = request.getCookies();
/* 65:65 */    if (cookies == null) {
/* 66:66 */      return;
/* 67:   */    }
/* 68:68 */    for (Cookie c : cookies) {
/* 69:69 */      if (key.equalsIgnoreCase(c.getName())) {
/* 70:70 */        c.setMaxAge(0);
/* 71:71 */        response.addCookie(c);
/* 72:72 */        break;
/* 73:   */      }
/* 74:   */    }
/* 75:   */  }
/* 76:   */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.CookieUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */