package com.zfk.base.util;
/*  2:   */
/*  3:   */import java.util.HashMap;
/*  4:   */import java.util.Map;
/*  5:   */import java.util.concurrent.locks.Lock;
/*  6:   */import java.util.concurrent.locks.ReentrantLock;
/*  7:   */
/* 14:   */public class LocalLockUtils
/* 15:   */{
/* 16:16 */  private static final Map<String, Lock> pool = new HashMap();
/* 17:   */  
/* 22:   */  public static Lock getLock(String key)
/* 23:   */  {
/* 24:24 */    Lock lock = (Lock)pool.get(key);
/* 25:25 */    if (lock == null) {
/* 26:26 */      lock = new ReentrantLock();
/* 27:27 */      pool.put(key, lock);
/* 28:   */    }
/* 29:   */    
/* 30:30 */    return lock;
/* 31:   */  }
/* 32:   */  
/* 36:   */  public static void clearLock(String key)
/* 37:   */  {
/* 38:38 */    Lock lock = (Lock)pool.get(key);
/* 39:39 */    if (lock != null) {
/* 40:40 */      pool.remove(key);
/* 41:   */    }
/* 42:   */  }
/* 43:   */}


/* Location:           D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-SNAPSHOT.jar
 * Qualified Name:     com.gep.core.util.LocalLockUtils
 * JD-Core Version:    0.7.0-SNAPSHOT-20130630
 */