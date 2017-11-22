package com.zfk.base.exceptoin;
/*  2:   */
/*  3:   */import java.io.Serializable;
/*  4:   */
/* 16:   */public class JsonMsgException
/* 17:   */  extends RuntimeException
/* 18:   */{
/* 19:   */  private static final long serialVersionUID = 4113399553351843338L;
/* 20:   */  private int code;
/* 21:   */  private String message;
/* 22:   */  private Serializable data;
/* 23:   */  
/* 24:   */  public JsonMsgException(int code, String message)
/* 25:   */  {
/* 26:26 */    this.code = code;
/* 27:27 */    this.message = message;
/* 28:   */  }
/* 29:   */  
/* 30:   */  public JsonMsgException(int code, String message, Serializable data)
/* 31:   */  {
/* 32:32 */    this.code = code;
/* 33:33 */    this.message = message;
/* 34:34 */    this.data = data;
/* 35:   */  }
/* 36:   */  
/* 37:   */  public int getCode() {
/* 38:38 */    return this.code;
/* 39:   */  }
/* 40:   */  
/* 41:   */  public void setCode(int code) {
/* 42:42 */    this.code = code;
/* 43:   */  }
/* 44:   */  
/* 45:   */  public String getMessage() {
/* 46:46 */    return this.message;
/* 47:   */  }
/* 48:   */  
/* 49:   */  public void setMessage(String message) {
/* 50:50 */    this.message = message;
/* 51:   */  }
/* 52:   */  
/* 53:   */  public Serializable getData() {
/* 54:54 */    return this.data;
/* 55:   */  }
/* 56:   */  
/* 57:   */  public void setData(Serializable data) {
/* 58:58 */    this.data = data;
/* 59:   */  }
/* 60:   */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.exceptoin.JsonMsgException
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */