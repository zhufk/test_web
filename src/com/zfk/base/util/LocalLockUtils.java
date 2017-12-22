package com.zfk.base.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LocalLockUtils {
	private static final Map<String, Lock> pool = new HashMap();

	public static Lock getLock(String key) {
		Lock lock = (Lock) pool.get(key);
		if (lock == null) {
			lock = new ReentrantLock();
			pool.put(key, lock);
		}

		return lock;
	}

	public static void clearLock(String key) {
		Lock lock = (Lock) pool.get(key);
		if (lock != null) {
			pool.remove(key);
		}
	}
}

/*
 * Location:
 * D:\maven_warehouse\com\gep\gep-commons\1.0.4-SNAPSHOT\gep-commons-1.0.4-
 * SNAPSHOT.jar Qualified Name: com.gep.core.util.LocalLockUtils JD-Core
 * Version: 0.7.0-SNAPSHOT-20130630
 */