package com.zfk.base.exceptoin;
/*   2:    */
/*  16:    */public class MissingOrderResourceException
/*  17:    */  extends RuntimeException
/*  18:    */{
/*  19:    */  private static final long serialVersionUID = -4876345176062000401L;
/*  20:    */  
/*  32:    */  private String className;
/*  33:    */  
/*  45:    */  private String key;
/*  46:    */  
/*  59:    */  public MissingOrderResourceException(String s, String className, String key)
/*  60:    */  {
/*  61: 61 */    super(s);
/*  62: 62 */    this.className = className;
/*  63: 63 */    this.key = key;
/*  64:    */  }
/*  65:    */  
/*  83:    */  public MissingOrderResourceException(String message, String className, String key, Throwable cause)
/*  84:    */  {
/*  85: 85 */    super(message, cause);
/*  86: 86 */    this.className = className;
/*  87: 87 */    this.key = key;
/*  88:    */  }
/*  89:    */  
/*  94:    */  public String getClassName()
/*  95:    */  {
/*  96: 96 */    return this.className;
/*  97:    */  }
/*  98:    */  
/* 103:    */  public String getKey()
/* 104:    */  {
/* 105:105 */    return this.key;
/* 106:    */  }
/* 107:    */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.exceptoin.MissingOrderResourceException
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */