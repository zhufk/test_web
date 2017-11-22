package com.zfk.base.util;
/*  2:   */
/*  3:   */import java.net.InetAddress;
/*  4:   */import java.net.UnknownHostException;
/*  5:   */import javax.servlet.http.HttpServletRequest;
/*  6:   */import org.slf4j.Logger;
/*  7:   */import org.slf4j.LoggerFactory;
/*  8:   */
/* 16:   */public class NetWorkUtils
/* 17:   */{
/* 18:18 */  private static final Logger logger = LoggerFactory.getLogger(NetWorkUtils.class);
/* 19:   */  
/* 24:   */  public static String getIpAddr(HttpServletRequest request)
/* 25:   */  {
/* 26:26 */    String ipAddress = null;
/* 27:27 */    ipAddress = request.getHeader("x-forwarded-for");
/* 28:28 */    if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
/* 29:29 */      ipAddress = request.getHeader("Proxy-Client-IP");
/* 30:   */    }
/* 31:31 */    if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
/* 32:32 */      ipAddress = request.getHeader("WL-Proxy-Client-IP");
/* 33:   */    }
/* 34:34 */    if ((ipAddress == null) || (ipAddress.length() == 0) || ("unknown".equalsIgnoreCase(ipAddress))) {
/* 35:35 */      ipAddress = request.getRemoteAddr();
/* 36:36 */      if (ipAddress.equals(getLocalIp()))
/* 37:   */      {
/* 38:38 */        InetAddress inet = null;
/* 39:   */        try {
/* 40:40 */          inet = InetAddress.getLocalHost();
/* 41:41 */          ipAddress = inet.getHostAddress();
/* 42:   */        } catch (UnknownHostException e) {
/* 43:43 */          logger.error("get host address error", e);
/* 44:   */        }
/* 45:   */      }
/* 46:   */    }
/* 47:   */    
/* 50:50 */    if ((ipAddress != null) && (ipAddress.length() > 15))
/* 51:   */    {
/* 52:52 */      if (ipAddress.indexOf(",") > 0) {
/* 53:53 */        ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
/* 54:   */      }
/* 55:   */    }
/* 56:56 */    return ipAddress;
/* 57:   */  }
/* 58:   */  
/* 59:   */  public static String getLocalIp() {
/* 60:   */    try {
/* 61:61 */      return InetAddress.getLoopbackAddress().getHostAddress();
/* 62:   */    } catch (Exception e) {
/* 63:63 */      logger.error("get local host address error", e);
/* 64:   */    }
/* 65:   */    
/* 66:66 */    return "";
/* 67:   */  }
/* 68:   */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.NetWorkUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */