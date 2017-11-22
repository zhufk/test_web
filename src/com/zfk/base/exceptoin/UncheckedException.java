/*  1:   */package com.zfk.base.exceptoin;
/*  2:   */
/* 10:   */public class UncheckedException
/* 11:   */  extends RuntimeException
/* 12:   */{
/* 13:   */  public UncheckedException(String message)
/* 14:   */  {
/* 15:15 */    super(message);
/* 16:   */  }
/* 17:   */  
/* 23:   */  public UncheckedException(Throwable cause)
/* 24:   */  {
/* 25:25 */    super(cause);
/* 26:   */  }
/* 27:   */  
/* 35:   */  public UncheckedException(String message, Throwable cause)
/* 36:   */  {
/* 37:37 */    super(message, cause);
/* 38:   */  }
/* 39:   */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.exceptoin.UncheckedException
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */