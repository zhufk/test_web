package com.zfk.base.exceptoin;
/*  2:   */
/*  4:   */public class MobileException
/*  5:   */  extends RuntimeException
/*  6:   */{
/*  7:   */  private static final long serialVersionUID = -7887057238055620806L;
/*  8:   */  private final ErrorCode errorCode;
/*  9:   */  
/* 10:   */  public MobileException(ErrorCode errorCode)
/* 11:   */  {
/* 12:12 */    this.errorCode = errorCode;
/* 13:   */  }
/* 14:   */  
/* 15:   */  public ErrorCode getCode()
/* 16:   */  {
/* 17:17 */    return this.errorCode;
/* 18:   */  }
/* 19:   */  
/* 21:   */  public String getMessage()
/* 22:   */  {
/* 23:23 */    return String.format("code : %s ; msg : %s", new Object[] { Integer.valueOf(this.errorCode.getCode()), this.errorCode.getMessage() });
/* 24:   */  }
/* 25:   */  
/* 28:   */  public synchronized Throwable fillInStackTrace()
/* 29:   */  {
/* 30:30 */    return super.fillInStackTrace();
/* 31:   */  }
/* 32:   */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.exceptoin.MobileException
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */