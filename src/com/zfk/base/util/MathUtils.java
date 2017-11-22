package com.zfk.base.util;
/*  2:   */
/*  3:   */import java.math.BigDecimal;
/*  4:   */
/* 11:   */public class MathUtils
/* 12:   */{
/* 13:13 */  public static final BigDecimal ZERO_DECIMAL = new BigDecimal(0);
/* 14:   */  
/* 19:   */  public static double roundHalfUp(BigDecimal decimal)
/* 20:   */  {
/* 21:21 */    return roundHalfUp(decimal, 2);
/* 22:   */  }
/* 23:   */  
/* 29:   */  public static double roundHalfUp(BigDecimal decimal, int scale)
/* 30:   */  {
/* 31:31 */    return decimal.setScale(scale, 4).doubleValue();
/* 32:   */  }
/* 33:   */  
/* 40:   */  public static double roundHalfUp(double value, int scale)
/* 41:   */  {
/* 42:42 */    BigDecimal decimal = BigDecimal.valueOf(value);
/* 43:43 */    return decimal.setScale(scale, 4).doubleValue();
/* 44:   */  }
/* 45:   */  
/* 51:   */  public static double roundHalfUp(double value)
/* 52:   */  {
/* 53:53 */    return roundHalfUp(value, 2);
/* 54:   */  }
/* 55:   */  
/* 56:   */  public static double doubleAdd(double... value) {
/* 57:57 */    double sum = 0.0D;
/* 58:58 */    if (value != null) {
/* 59:59 */      for (int i = 0; i < value.length; i++) {
/* 60:60 */        sum += value[i];
/* 61:   */      }
/* 62:   */    }
/* 63:63 */    return roundHalfUp(sum);
/* 64:   */  }
/* 65:   */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.MathUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */