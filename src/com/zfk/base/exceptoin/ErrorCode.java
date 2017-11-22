package com.zfk.base.exceptoin;
/*  2:   */
/*  3:   */import java.io.Serializable;
/*  4:   */
/* 10:   */public class ErrorCode
/* 11:   */  implements Serializable
/* 12:   */{
/* 13:   */  private static final long serialVersionUID = 1982607259137204522L;
/* 14:   */  private final int code;
/* 15:   */  private String message;
/* 16:   */  
/* 17:   */  public ErrorCode(int code, String message)
/* 18:   */  {
/* 19:19 */    this.code = code;
/* 20:20 */    this.message = message;
/* 21:   */  }
/* 22:   */  
/* 23:   */  public int getCode()
/* 24:   */  {
/* 25:25 */    return this.code;
/* 26:   */  }
/* 27:   */  
/* 28:   */  public String getMessage()
/* 29:   */  {
/* 30:30 */    return this.message;
/* 31:   */  }
/* 32:   */  
/* 33:   */  public void setMessage(String message)
/* 34:   */  {
/* 35:35 */    this.message = message;
/* 36:   */  }
/* 37:   */  
/* 39:   */  public String toString()
/* 40:   */  {
/* 41:41 */    return "ErrorCode [code=" + this.code + ", message=" + this.message + "]";
/* 42:   */  }
/* 43:   */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.exceptoin.ErrorCode
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */