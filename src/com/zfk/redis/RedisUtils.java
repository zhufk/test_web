package com.zfk.redis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import com.zfk.base.util.SpringUtils;
import com.zfk.base.util.StringUtils;

public class RedisUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtils.class);

	public static RedisTemplate<Serializable, Serializable> getRedisTemplate() {
		RedisTemplate<Serializable, Serializable> redisTemplate = null;
		if (redisTemplate == null) {
			redisTemplate = (RedisTemplate) SpringUtils.getBean("redisTemplate");
		}
		return redisTemplate;
	}

	public static RedisTemplate<Serializable, Serializable> getRedisTemplate(String name) {
		RedisTemplate<Serializable, Serializable> redisTemplate = null;
		if (StringUtils.isNotBlank(name)) {
			redisTemplate = (RedisTemplate) SpringUtils.getBean(name);
		} else {
			redisTemplate = (RedisTemplate) SpringUtils.getBean("redisTemplate");
		}
		return redisTemplate;
	}

	// ###########################String###############################
	public static Long incrBy(String key, Long incr) {
		return incrByFromDB(null, null, key, incr, 0L);
	}

	public static Long incrBy(String key, Long incr, long timeout) {
		return incrByFromDB(null, null, key, incr, timeout);
	}

	public static Long incrByFromDB(String templateName, Integer db, final String key, final Long incr,
			final long timeout) {
		if (StringUtils.isBlank(key).booleanValue()) {
			return Long.valueOf(0L);
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}

					long result = connection.incrBy(key.getBytes(), incr.longValue()).longValue();

					if (timeout > 0L) {
						connection.expire(key.getBytes(), timeout);
					}
					return Long.valueOf(result);
				}
			});
		} catch (Exception e) {
			LOGGER.error("incrByFromDB error", e);
		}

		return Long.valueOf(0L);
	}

	public static Object setObject(String key, Object value) {
		return setObjectToDB(null, key, value, 0L);
	}

	public static Object setObject(String key, Object value, long timeout) {
		return setObjectToDB(null, key, value, timeout);
	}

	public static Object setObjectToDB(Integer db, String key, Object value, long timeout) {
		return setObjectToDB(null, db, key, value, timeout);
	}

	public static Object setObjectToDB(String templateName, Integer db, String key, final Object value,
			final long timeout) {
		if (key == null || value == null) {
			return null;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					if (timeout > 0L) {
						connection.setEx(key.getBytes(), timeout, SerializeUtil.serialize(value));
					} else {
						connection.set(key.getBytes(), SerializeUtil.serialize(value));
					}
					return value;
				}
			});
		} catch (Exception e) {
			LOGGER.error("setObjectToDB error", e);
		}
		return null;
	}

	public static String setString(String key, String value) {
		return setStringToDB(null, key, value, 0L);
	}

	public static String setString(String key, String value, long timeout) {
		return setStringToDB(null, key, value, timeout);
	}

	public static String setStringToDB(Integer db, String key, String value, long timeout) {
		return setStringToDB(null, db, key, value, timeout);
	}

	public static String setStringToDB(String templateName, Integer db, String key, final String value,
			final long timeout) {
		if (key == null || value == null) {
			return null;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<String>() {
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					if (timeout > 0L) {
						connection.setEx(key.getBytes(), timeout, value.getBytes());
					} else {
						connection.set(key.getBytes(), value.getBytes());
					}
					return value;
				}
			});
		} catch (Exception e) {
			LOGGER.error("setStringToDB error", e);
		}
		return null;
	}

	public static Object getObject(String key) {
		return getObjectFromDB(null, key);
	}

	public static Object getObjectFromDB(Integer db, String key) {
		return getObjectFromDB("redisTemplate", db, key);
	}

	public static Object getObjectFromDB(String templateName, Integer db, String key) {
		if (key == null) {
			return null;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					Object result = null;
					byte[] vbyte = connection.get(SerializeUtil.serialize(key));
					result = SerializeUtil.unserialize(vbyte);
					return result;
				}
			});
		} catch (Exception e) {
			LOGGER.error("getObjectFromDB error", e);
		}
		return null;
	}

	public static String getString(String key) {
		return getStringFromDB(null, key);
	}

	public static String getStringFromDB(Integer db, String key) {
		return getStringFromDB("redisTemplate", db, key);
	}

	public static String getStringFromDB(String templateName, Integer db, String key) {
		if (key == null) {
			return null;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<String>() {
				public String doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					String result = null;
					byte[] vbyte = connection.get(key.getBytes());
					result = new String(vbyte);
					return result;
				}
			});
		} catch (Exception e) {
			LOGGER.error("getStringFromDB error", e);
		}
		return null;
	}

	public static Long deleteObject(String key) {
		return deleteObjectFromDB(null, key);
	}

	public static Long deleteObjectFromDB(Integer db, String key) {
		return deleteObjectFromDB(null, db, key);
	}

	public static Long deleteObjectFromDB(String templateName, Integer db, final String key) {
		if (key == null) {
			return null;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					return connection.del(new byte[][] { key.getBytes() });
				}
			});
		} catch (Exception e) {
			LOGGER.error("deleteObjectFromDB error", e);
		}

		return null;
	}

	public static Long deleteString(String key) {
		return deleteStringFromDB(null, key);
	}

	public static Long deleteStringFromDB(Integer db, String key) {
		return deleteStringFromDB(null, db, key);
	}

	public static Long deleteStringFromDB(String templateName, Integer db, final String key) {
		if (StringUtils.isBlank(key).booleanValue()) {
			return null;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					return connection.del(new byte[][] { key.getBytes() });
				}
			});
		} catch (Exception e) {
			LOGGER.error("deleteStringFromDB error", e);
		}
		return null;
	}

	// ####################Hash##########################
	public static Object hsetObject(String key, String field, Object value) {
		return hsetObjectToDB(null, key, field, value, 0L);
	}

	public static Object hsetObject(String key, String field, Object value, long timeout) {
		return hsetObjectToDB(null, key, field, value, timeout);
	}

	public static Object hsetObjectToDB(Integer db, String key, String field, Object value, long timeout) {
		return hsetObjectToDB(null, db, key, field, value, timeout);
	}

	public static Object hsetObjectToDB(String templateName, Integer db, final String key, final String field,
			final Object value, final long timeout) {
		if ((StringUtils.isBlank(key).booleanValue()) || (StringUtils.isBlank(field).booleanValue())
				|| (value == null)) {
			return null;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					connection.hSet(key.getBytes(), field.getBytes(), SerializeUtil.serialize(value));
					if (timeout > 0L) {
						connection.expire(key.getBytes(), timeout);
					}
					return value;
				}
			});
		} catch (Exception e) {
			LOGGER.error("hsetObjectToDB error", e);
		}

		return null;
	}

	public static boolean hsetAllObject(String key, Map<String, Object> m) {
		return hsetAllObjectToDB(null, key, m, 0L);
	}

	public static boolean hsetAllObject(String key, Map<String, Object> m, long timeout) {
		return hsetAllObjectToDB(null, key, m, timeout);
	}

	public static boolean hsetAllObjectToDB(Integer db, String key, Map<String, Object> m, long timeout) {
		return hsetAllObjectToDB(null, db, key, m, timeout);
	}

	public static boolean hsetAllObjectToDB(String templateName, Integer db, final String key,
			final Map<String, Object> m, final long timeout) {
		if ((StringUtils.isBlank(key).booleanValue()) || (m == null)) {
			return false;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}

					Map<byte[], byte[]> hashes = new LinkedHashMap<byte[], byte[]>(m.size());

					for (Map.Entry<String, Object> entry : m.entrySet()) {
						hashes.put(((String) entry.getKey()).getBytes(), SerializeUtil.serialize(entry.getValue()));
					}

					connection.hMSet(key.getBytes(), hashes);
					if (timeout > 0L) {
						connection.expire(key.getBytes(), timeout);
					}
					return true;
				}
			});
		} catch (Exception e) {
			LOGGER.error("hsetAllObjectToDB error", e);
		}
		return false;
	}

	public static Object hgetObject(String key, String field) {
		return hgetObjectFromDB(null, key, field);
	}

	public static Object hgetObjectFromDB(Integer db, String key, String field) {
		return hgetObjectFromDB(null, db, key, field);
	}

	public static Object hgetObjectFromDB(String templateName, Integer db, final String key, final String field) {
		if ((StringUtils.isBlank(key).booleanValue()) || (StringUtils.isBlank(field).booleanValue())) {
			return null;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					return SerializeUtil.unserialize(connection.hGet(key.getBytes(), field.getBytes()));
				}
			});
		} catch (Exception e) {
			LOGGER.error("hgetObjectFromDB error", e);
		}

		return null;
	}

	public static Map<String, Object> hgetAllObject(String key) {
		return hgetAllObjectFromDB(null, key);
	}

	public static Map<String, Object> hgetAllObjectFromDB(Integer db, String key) {
		return hgetAllObjectFromDB(null, db, key);
	}

	public static Map<String, Object> hgetAllObjectFromDB(String templateName, Integer db, final String key) {
		if (StringUtils.isBlank(key).booleanValue()) {
			return null;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<Map<String, Object>>() {
				public Map<String, Object> doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}

					Map<byte[], byte[]> result = connection.hGetAll(key.getBytes());
					Map<String, Object> tagResult = new HashMap<String, Object>();
					for (Map.Entry<byte[], byte[]> entry : result.entrySet()) {
						String hk = new String((byte[]) entry.getKey());
						Object hv = SerializeUtil.unserialize((byte[]) entry.getValue());
						tagResult.put(hk, hv);
					}
					return tagResult;
				}
			});
		} catch (Exception e) {
			LOGGER.error("hgetAllObjectFromDB error", e);
		}

		return null;
	}

	public static int hgetSize(String key) {
		if (StringUtils.isBlank(key).booleanValue()) {
			return 0;
		}
		try {
			getRedisTemplate(null).execute(new RedisCallback<Integer>() {
				public Integer doInRedis(RedisConnection connection) throws DataAccessException {
					Long size = connection.hLen(key.getBytes());
					return Integer.valueOf(size.intValue());
				}
			});
		} catch (Exception e) {
			LOGGER.error("getSize error", e);
		}

		return 0;
	}

	public static Set<String> hgetFields(String key) {
		if (StringUtils.isBlank(key).booleanValue()) {
			return null;
		}
		try {
			getRedisTemplate(null).execute(new RedisCallback<Set<String>>() {
				public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
					Set<byte[]> set = connection.hKeys(key.getBytes());
					if (set != null) {
						Set<String> keys = Collections.emptySet();
						for (byte[] ke : set) {
							keys.add(new String(ke));
						}
						return keys;
					}
					return null;
				}
			});
		} catch (Exception e) {
			LOGGER.error("hgetFields error", e);
		}
		return null;
	}

	public static List<Object> hgetValues(String key) {
		if (StringUtils.isBlank(key).booleanValue()) {
			return null;
		}
		try {
			getRedisTemplate(null).execute(new RedisCallback<Collection<Object>>() {
				public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
					List<byte[]> list = connection.hVals(key.getBytes());
					if (list != null) {
						List<Object> values = Collections.emptyList();
						for (byte[] value : list) {
							values.add(SerializeUtil.unserialize(value));
						}
						return values;
					}
					return null;
				}
			});
		} catch (Exception e) {
			LOGGER.error("hgetValues error", e);
		}
		return null;
	}

	public static Long hDeleteObject(String key, String... field) {
		return hDeleteObjectFromDB(null, key, field);
	}

	public static Long hDeleteObjectFromDB(Integer db, String key, String... field) {
		return hDeleteObjectFromDB(null, db, key, field);
	}

	public static Long hDeleteObjectFromDB(String templateName, Integer db, final String key, final String... field) {
		if ((StringUtils.isBlank(key).booleanValue()) || (field.length == 0)) {
			return null;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<Long>() {
				public Long doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					byte[][] fileByte = new byte[field.length][];
					for (int i = 0; i < field.length; i++) {
						fileByte[i] = field[i].getBytes();
					}
					return connection.hDel(key.getBytes(), fileByte);
				}
			});
		} catch (Exception e) {
			LOGGER.error("hDeleteObjectFromDB error", e);
		}
		return null;
	}

	// #########################################List###############################################
	public List<Object> lRange(String key, final long start, final long end) {
		getRedisTemplate(null).execute(new RedisCallback<List<Object>>() {
			public List<Object> doInRedis(RedisConnection connection) {
				List<Object> relist = Collections.emptyList();
				List<byte[]> list = connection.lRange(key.getBytes(), start, end);
				for (byte[] bs : list) {
					relist.add(SerializeUtil.unserialize(bs));
				}
				return relist;
			}
		});
		return null;
	}

	public static boolean lPush(String key, Object... value) {
		return lPushToDB(null, key, value);
	}

	public static boolean lPushToDB(Integer db, String key, Object... value) {
		return lPushToDB(null, db, key, value);
	}

	public static boolean lPushToDB(String templateName, Integer db, final String key, final Object... value) {
		boolean flag = false;
		if ((StringUtils.isBlank(key).booleanValue()) || (value == null) || (value.length == 0)) {
			return flag;
		}
		try {
			flag = getRedisTemplate(templateName).execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					byte[][] vByte = new byte[value.length][];
					for (int i = 0; i < value.length; i++) {
						vByte[i] = SerializeUtil.serialize(value[i]);
					}
					connection.lPush(key.getBytes(), vByte);
					return Boolean.valueOf(true);
				}
			}).booleanValue();
		} catch (Exception e) {
			LOGGER.error("lPushToDB error", e);
		}

		return flag;
	}

	public static Object lPop(String key) {
		return lPopFromDB(null, key);
	}

	public static Object lPopFromDB(Integer db, String key) {
		return lPopFromDB(null, db, key);
	}

	public static Object lPopFromDB(String templateName, Integer db, final String key) {
		if (StringUtils.isBlank(key).booleanValue()) {
			return null;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					Object result = null;
					byte[] vbyte = connection.lPop(key.getBytes());

					result = SerializeUtil.unserialize(vbyte);

					return result;
				}
			});
		} catch (Exception e) {
			LOGGER.error("lPopFromDB error", e);
		}
		return null;
	}

	public static boolean rPush(String key, Object... value) {
		return rPushToDB(null, key, value);
	}

	public static boolean rPushToDB(Integer db, String key, Object... value) {
		return rPushToDB(null, db, key, value);
	}

	public static boolean rPushToDB(String templateName, Integer db, final String key, final Object... value) {
		boolean flag = false;
		if ((StringUtils.isBlank(key).booleanValue()) || (value == null) || (value.length == 0)) {
			return flag;
		}
		try {
			flag = getRedisTemplate(templateName).execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					byte[][] vByte = new byte[value.length][];
					for (int i = 0; i < value.length; i++) {
						vByte[i] = SerializeUtil.serialize(value[i]);
					}
					connection.rPush(key.getBytes(), vByte);
					return Boolean.valueOf(true);
				}
			}).booleanValue();
		} catch (Exception e) {
			LOGGER.error("rPushToDB error", e);
		}

		return flag;
	}

	public static Object rPop(String key) {
		return rPopFromDB(null, key);
	}

	public static Object rPopFromDB(Integer db, String key) {
		return rPopFromDB(null, db, key);
	}

	public static Object rPopFromDB(String templateName, Integer db, final String key) {
		if (StringUtils.isBlank(key).booleanValue()) {
			return null;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<Object>() {
				public Object doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					Object result = null;
					byte[] vbyte = connection.rPop(key.getBytes());

					result = SerializeUtil.unserialize(vbyte);

					return result;
				}
			});
		} catch (Exception e) {
			LOGGER.error("rPopFromDB error", e);
		}
		return null;
	}

	public static int getLength(String key) {
		return getLength(null, key);
	}

	public static int getLength(String templateName, String key) {
		if (StringUtils.isBlank(key).booleanValue()) {
			return 0;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<Integer>() {
				public Integer doInRedis(RedisConnection connection) throws DataAccessException {
					Long size = connection.lLen(key.getBytes());
					return Integer.valueOf(size.intValue());
				}
			});
		} catch (Exception e) {
			LOGGER.error("getLength error", e);
		}
		return 0;
	}

	public static boolean pipelinedLPushFromDB(String templateName, Integer db, final String key,
			final List<Object> value) {
		boolean flag = false;

		if (StringUtils.isBlank(key).booleanValue()) {
			return flag;
		}

		try {
			flag = getRedisTemplate(templateName).execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}

					connection.openPipeline();

					for (Iterator localIterator = value.iterator(); localIterator.hasNext();) {
						Object v = localIterator.next();
						byte[] vbyte = SerializeUtil.serialize(v);

						connection.lPush(key.getBytes(), new byte[][] { vbyte });
					}

					connection.closePipeline();

					return Boolean.valueOf(true);
				}
			});
		} catch (Exception e) {
			LOGGER.error("pipelinedLPushFromDB error", e);
		}

		return flag;
	}

	public static boolean pipelinedRPushFromDB(String templateName, Integer db, final String key,
			final List<Object> value) {
		boolean flag = false;

		if (StringUtils.isBlank(key).booleanValue()) {
			return flag;
		}

		try {
			flag = getRedisTemplate(templateName).execute(new RedisCallback<Boolean>() {
				public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}

					connection.openPipeline();

					for (Iterator localIterator = value.iterator(); localIterator.hasNext();) {
						Object v = localIterator.next();
						byte[] vbyte = SerializeUtil.serialize(v);
						connection.rPush(key.getBytes(), new byte[][] { vbyte });
					}
					connection.closePipeline();
					return Boolean.valueOf(true);
				}
			});
		} catch (Exception e) {
			LOGGER.error("pipelinedRPushFromDB error", e);
		}

		return flag;
	}

	public static List<Object> pipelinedLPopFromDB(String templateName, Integer db, final int size, final String key) {
		if ((StringUtils.isBlank(key).booleanValue()) || (size <= 0)) {
			return null;
		}
		try {
			getRedisTemplate(templateName).execute(new RedisCallback<List<Object>>() {
				public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					List<Object> resultList = new ArrayList<Object>(size);
					connection.openPipeline();
					for (int i = 0; i < size; i++) {
						connection.lPop(key.getBytes());
					}
					List<Object> pipelineList = connection.closePipeline();
					Iterator localIterator;
					if (pipelineList != null) {
						for (localIterator = pipelineList.iterator(); localIterator.hasNext();) {
							Object obj = localIterator.next();
							Object result = null;

							if ((obj instanceof byte[])) {
								byte[] vbyte = (byte[]) obj;
								result = SerializeUtil.unserialize(vbyte);
							} else {
								result = obj;
							}
							resultList.add(result);
						}
					}
					return resultList;
				}
			});
		} catch (Exception e) {
			LOGGER.error("pipelinedLPopFromDB error", e);
		}

		return null;
	}

	public static List<Object> pipelinedRPopFromDB(String templateName, Integer db, final int size, final String key) {
		if ((StringUtils.isBlank(key).booleanValue()) || (size <= 0)) {
			return null;
		}

		try {
			getRedisTemplate(templateName).execute(new RedisCallback<List<Object>>() {
				public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
					if (db != null) {
						connection.select(db.intValue());
					}
					List<Object> resultList = new ArrayList<Object>(size);

					connection.openPipeline();

					for (int i = 0; i < size; i++) {
						connection.rPop(key.getBytes());
					}

					List<Object> pipelineList = connection.closePipeline();
					Iterator localIterator;
					if (pipelineList != null) {
						for (localIterator = pipelineList.iterator(); localIterator.hasNext();) {
							Object obj = localIterator.next();
							Object result = null;

							if ((obj instanceof byte[])) {
								byte[] vbyte = (byte[]) obj;
								result = SerializeUtil.unserialize(vbyte);
							} else {
								result = obj;
							}
							resultList.add(result);
						}
					}
					return resultList;
				}
			});
		} catch (Exception e) {
			LOGGER.error("pipelinedRPopFromDB error", e);
		}

		return null;
	}

	// ##############################################################
	/*
	 * 
	 * public static Cursor<String> scan(ScanOptions options) { return
	 * scanFromDB(null, options); }
	 * 
	 * public static Cursor<String> scanFromDB(Integer db, ScanOptions options)
	 * { return scanFromDB(null, db, options); }
	 * 
	 * public static Cursor<String> scanFromDB(String templateName, Integer db,
	 * final ScanOptions options) { try {
	 * getRedisTemplate(templateName).execute(new
	 * RedisCallback<Cursor<String>>() { public Cursor<String>
	 * doInRedis(RedisConnection connection) throws DataAccessException { if (db
	 * != null) { connection.select(db.intValue()); } connection.scan(options,
	 * new Converter() {
	 * 
	 * @Override public String convert(Object source) { return new
	 * String((byte[]) source); }
	 * 
	 * }); } }, true); } catch (Exception e) { LOGGER.error("scanFromDB error",
	 * e); }
	 * 
	 * return null; }
	 * 
	 * public static int countScanFromDB(Integer db, ScanOptions options) {
	 * return countScanFromDB(null, db, options); }
	 * 
	 * public static int countScanFromDB(String templateName, Integer db, final
	 * ScanOptions options) { try { Cursor<String> cursor =
	 * (Cursor)getRedisTemplate(templateName).execute(new
	 * RedisCallback<Cursor<String>>() { public Cursor<String>
	 * doInRedis(RedisConnection connection) throws DataAccessException { if (db
	 * != null) { connection.select(db.intValue()); } connection.scan(options),
	 * new Converter() { public String convert(byte[] source) { return new
	 * String(source); } }; } }, true);
	 * 
	 * int count = 0; while (cursor.hasNext()) { cursor.next(); count++; }
	 * return count; } catch (Exception e) { LOGGER.error(
	 * "countScanFromDB error", e); } return 0; }
	 * 
	 */
}
