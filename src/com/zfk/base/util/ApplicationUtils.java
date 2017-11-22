package com.zfk.base.util;
/*  2:   */
/*  3:   */import java.util.UUID;
/*  4:   */import org.apache.commons.codec.digest.DigestUtils;
/*  5:   */
/* 17:   */public class ApplicationUtils
/* 18:   */{
/* 19:   */  public static String randomUUID()
/* 20:   */  {
/* 21:21 */    return UUID.randomUUID().toString();
/* 22:   */  }
/* 23:   */  
/* 29:   */  public static String md5Hex(String value)
/* 30:   */  {
/* 31:31 */    return DigestUtils.md5Hex(value);
/* 32:   */  }
/* 33:   */  
/* 39:   */  public static String sha1Hex(String value)
/* 40:   */  {
/* 41:41 */    return DigestUtils.sha1Hex(value);
/* 42:   */  }
/* 43:   */  
/* 49:   */  public static String sha256Hex(String value)
/* 50:   */  {
/* 51:51 */    return DigestUtils.sha256Hex(value);
/* 52:   */  }
/* 53:   */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.ApplicationUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */