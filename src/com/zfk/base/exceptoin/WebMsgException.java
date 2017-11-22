package com.zfk.base.exceptoin;
/*  2:   */
/*  3:   */import java.io.Serializable;
/*  4:   */
/* 17:   */public class WebMsgException
/* 18:   */  extends RuntimeException
/* 19:   */{
/* 20:   */  private static final long serialVersionUID = -231256611059539946L;
/* 21:   */  private int code;
/* 22:   */  private String message;
/* 23:   */  private String redirectUrl;
/* 24:   */  private Serializable data;
/* 25:   */  
/* 26:   */  public WebMsgException(int code, String message, String redirectUrl)
/* 27:   */  {
/* 28:28 */    this.code = code;
/* 29:29 */    this.message = message;
/* 30:30 */    this.redirectUrl = redirectUrl;
/* 31:   */  }
/* 32:   */  
/* 33:   */  public WebMsgException(int code, String message, String redirectUrl, Serializable data)
/* 34:   */  {
/* 35:35 */    this.code = code;
/* 36:36 */    this.message = message;
/* 37:37 */    this.redirectUrl = redirectUrl;
/* 38:38 */    this.data = data;
/* 39:   */  }
/* 40:   */  
/* 41:   */  public int getCode() {
/* 42:42 */    return this.code;
/* 43:   */  }
/* 44:   */  
/* 45:   */  public void setCode(int code) {
/* 46:46 */    this.code = code;
/* 47:   */  }
/* 48:   */  
/* 49:   */  public String getMessage() {
/* 50:50 */    return this.message;
/* 51:   */  }
/* 52:   */  
/* 53:   */  public void setMessage(String message) {
/* 54:54 */    this.message = message;
/* 55:   */  }
/* 56:   */  
/* 57:   */  public String getRedirectUrl() {
/* 58:58 */    return this.redirectUrl;
/* 59:   */  }
/* 60:   */  
/* 61:   */  public void setRedirectUrl(String redirectUrl) {
/* 62:62 */    this.redirectUrl = redirectUrl;
/* 63:   */  }
/* 64:   */  
/* 65:   */  public Serializable getData() {
/* 66:66 */    return this.data;
/* 67:   */  }
/* 68:   */  
/* 69:   */  public void setData(Serializable data) {
/* 70:70 */    this.data = data;
/* 71:   */  }
/* 72:   */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.exceptoin.WebMsgException
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */