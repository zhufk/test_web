package com.zfk.base.util;
/*  2:   */
/*  7:   */import java.util.MissingResourceException;
/*  8:   */import java.util.ResourceBundle;
/*  9:   */
/* 24:   */public class PropertyUtils
/* 25:   */{
/* 26:   */  private static final String PRO_APPLICATION = "application";
/* 27:   */  
/* 28:   */  public static String getString(String key)
/* 29:   */  {
/* 30:   */    try
/* 31:   */    {
/* 32:32 */      return ResourceBundle.getBundle("application").getString(key);
/* 33:   */    } catch (MissingResourceException e) {
/* 34:34 */      throw new RuntimeException("! config : " + key + '!');
/* 35:   */    }
/* 36:   */  }
/* 37:   */  
/* 42:   */  public static String getConfig(String key)
/* 43:   */  {
/* 44:   */    try
/* 45:   */    {
/* 46:46 */      return ResourceBundle.getBundle("application").getString(key);
/* 47:   */    } catch (MissingResourceException e) {}
/* 48:48 */    return "";
/* 49:   */  }
/* 50:   */  
/* 57:   */  public static String getConfig(String resourceBundle, String key)
/* 58:   */  {
/* 59:   */    try
/* 60:   */    {
/* 61:61 */      return ResourceBundle.getBundle(resourceBundle).getString(key);
/* 62:   */    } catch (MissingResourceException e) {}
/* 63:63 */    return "";
/* 64:   */  }
/* 65:   */  

/* 87:   */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.PropertyUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */