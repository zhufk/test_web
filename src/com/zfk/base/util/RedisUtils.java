package com.zfk.base.util;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.ConvertingCursor;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.core.RedisTemplate;

public final class RedisUtils
{
  /*private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtils.class);
  
  public static <T> T setObjectToDB(Integer db, String key, final T value, final long timeout)
  {
    if ((StringUtils.isBlank(key).booleanValue()) || (value == null)) {
      return null;
    }
    try
    {
      JedisPoolHolder.getNamedRedisTemplate("redisTemplate").execute(new RedisCallback()
      {
        public T doInRedis(RedisConnection connection) throws DataAccessException {
          if (db != null) {
            connection.select(db.intValue());
          }
          
          if (timeout > 0L) {
            connection.setEx(key.getBytes(), timeout, value.toString().getBytes());
          } else {
            connection.set(key.getBytes(), value.toString().getBytes());
          }
          return value;
        }
      });
    } catch (Exception e) {
      LOGGER.error("putObjectToDB error", e);
    }
    return null;
  }
  
  public static <T> T putObject(String key, T value)
  {
    return putObjectToDB(null, key, value, 0L);
  }
  
  public static <T> T putObject(String key, T value, long timeout)
  {
    return putObjectToDB(null, key, value, timeout);
  }
  
  public static <T> T putObjectToDB(Integer db, String key, T value, long timeout)
  {
    return putObjectToDB(null, db, key, value, timeout);
  }
  
  public static <T> T putObjectToDB(String templateName, Integer db, String key, final T value, final long timeout)
  {
    if ((StringUtils.isBlank(key).booleanValue()) || (value == null)) {
      return null;
    }
    try
    {
      JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new RedisCallback()
      {
        public T doInRedis(RedisConnection connection) throws DataAccessException {
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
      LOGGER.error("putObjectToDB error", e);
    }
    
    return null;
  }
  
  public static boolean expire(String key, final long timeout)
  {
    boolean flag = false;
    if (StringUtils.isBlank(key).booleanValue()) {
      return flag;
    }
    try
    {
      flag = ((Boolean)JedisPoolHolder.getNamedRedisTemplate(null).execute(new RedisCallback()
      {
        public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
          connection.expire(key.getBytes(), timeout);
          return Boolean.valueOf(true);
        }
      })).booleanValue();
    } catch (Exception e) {
      LOGGER.error("expire error", e);
    }
    
    return flag;
  }
  
  public static <T> T getObject(String key)
  {
    return getObjectFromDB(null, key);
  }
  
  public static <T> T getObjectFromDB(Integer db, String key)
  {
    return getObjectFromDB("redisTemplate", db, key);
  }
  
  public static <T> T getObjectFromDB(String templateName, Integer db, String key)
  {
    RedisTemplate<Serializable, Serializable> template = JedisPoolHolder.getNamedRedisTemplate(templateName);
    return getObjectFromDB(template, db, true, key);
  }
  
  public static <T> T getObjectFromDB(RedisTemplate<Serializable, Serializable> template, Integer db, final boolean serializable, final String key)
  {
    if (StringUtils.isBlank(key).booleanValue()) {
      return null;
    }
    
    if (template == null) {
      template = JedisPoolHolder.getRedisTemplate();
    }
    try
    {
      template.execute(new Object()
      {
        public T doInRedis(RedisConnection connection) throws DataAccessException
        {
          if (db != null) {
            connection.select(db.intValue());
          }
          
          T result = null;
          byte[] vbyte = connection.get(key.getBytes());
          
          if (serializable) {
            result = SerializeUtil.unserialize(vbyte);
          }
          else if (vbyte != null) {
            result = new String(vbyte);
          }
          
          return result;
        }
      });
    } catch (Exception e) {
      LOGGER.error("getObjectFromDB error", e);
    }
    
    return null;
  }
  
  public static <T> T removeObject(String key)
  {
    return removeObjectFromDB(null, key);
  }
  
  public static <T> T removeObjectFromDB(Integer db, String key)
  {
    return removeObjectFromDB(null, db, key);
  }
  
  public static <T> T removeObjectFromDB(String templateName, Integer db, final String key)
  {
    if (StringUtils.isBlank(key).booleanValue()) {
      return null;
    }
    try
    {
      JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new RedisCallback()
      {
        public T doInRedis(RedisConnection connection) throws DataAccessException
        {
          if (db != null) {
            connection.select(db.intValue());
          }
          return connection.del(new byte[][] { key.getBytes() });
        }
      });
    } catch (Exception e) {
      LOGGER.error("removeObjectFromDB error", e);
    }
    
    return null;
  }
  
  public static <T> T putObject(String key, String field, T value)
  {
    return putObjectToDB(null, key, field, value, 0L);
  }
  
  public static <T> T putObject(String key, String field, T value, long timeout)
  {
    return putObjectToDB(null, key, field, value, timeout);
  }
  
  public static <T> T putObjectToDB(Integer db, String key, String field, T value, long timeout)
  {
    return putObjectToDB(null, db, key, field, value, timeout);
  }
  
  public static <T> T putObjectToDB(String templateName, Integer db, final String key, final String field, final T value, final long timeout)
  {
    if ((StringUtils.isBlank(key).booleanValue()) || (StringUtils.isBlank(field).booleanValue()) || (value == null)) {
      return null;
    }
    try
    {
      JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new RedisCallback()
      {
        public T doInRedis(RedisConnection connection) throws DataAccessException {
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
      LOGGER.error("putObjectToDB error", e);
    }
    
    return null;
  }
  
  public static <T> void putAllObject(String key, Map<String, T> m)
  {
    putAllObjectToDB(null, key, m, 0L);
  }
  
  public static <T> void putAllObject(String key, Map<String, T> m, long timeout)
  {
    putAllObjectToDB(null, key, m, timeout);
  }
  
  public static <T> void putAllObjectToDB(Integer db, String key, Map<String, T> m, long timeout)
  {
    putAllObjectToDB(null, db, key, m, timeout);
  }
  
  public static <T> void putAllObjectToDB(String templateName, Integer db, final String key, final Map<String, T> m, final long timeout)
  {
    if ((StringUtils.isBlank(key).booleanValue()) || (m == null)) {
      return;
    }
    try
    {
      JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new RedisCallback()
      {
        public T doInRedis(RedisConnection connection) throws DataAccessException {
          if (db != null) {
            connection.select(db.intValue());
          }
          
          Map<byte[], byte[]> hashes = new LinkedHashMap(m.size());
          
          for (Map.Entry<String, T> entry : m.entrySet()) {
            hashes.put(((String)entry.getKey()).getBytes(), SerializeUtil.serialize(entry.getValue()));
          }
          
          connection.hMSet(key.getBytes(), hashes);
          if (timeout > 0L) {
            connection.expire(key.getBytes(), timeout);
          }
          return null;
        }
      });
    } catch (Exception e) {
      LOGGER.error("putObjectToDB error", e);
    }
  }
  
  public static <T> T getObject(String key, String field)
  {
    return getObjectFromDB(null, key, field);
  }
  
  public static <T> T getObjectFromDB(Integer db, String key, String field)
  {
    return getObjectFromDB(null, db, key, field);
  }
  
  public static <T> T getObjectFromDB(String templateName, Integer db, final String key, final String field)
  {
    if ((StringUtils.isBlank(key).booleanValue()) || (StringUtils.isBlank(field).booleanValue())) {
      return null;
    }
    try
    {
      JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new RedisCallback()
      {
        public T doInRedis(RedisConnection connection) throws DataAccessException
        {
          if (db != null) {
            connection.select(db.intValue());
          }
          return SerializeUtil.unserialize(connection.hGet(key.getBytes(), field.getBytes()));
        }
      });
    } catch (Exception e) {
      LOGGER.error("getObjectFromDB error", e);
    }
    
    return null;
  }
  
  public static <HK, HV> Map<HK, HV> getAllObject(String key)
  {
    return getAllObjectFromDB(null, key);
  }
  
  public static <HK, HV> Map<HK, HV> getAllObjectFromDB(Integer db, String key)
  {
    return getAllObjectFromDB(null, db, key);
  }
  
  public static <HK, HV> Map<HK, HV> getAllObjectFromDB(String templateName, Integer db, final String key)
  {
    if (StringUtils.isBlank(key).booleanValue()) {
      return null;
    }
    try
    {
      (Map)JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new Object()
      {
        public Map<HK, HV> doInRedis(RedisConnection connection) throws DataAccessException
        {
          if (db != null) {
            connection.select(db.intValue());
          }
          
          Map<byte[], byte[]> result = connection.hGetAll(key.getBytes());
          Map<HK, HV> tagResult = new HashMap();
          for (Map.Entry<byte[], byte[]> entry : result.entrySet()) {
            HK hk = null;
            try {
              hk = new String((byte[])entry.getKey());
            } catch (Exception e) {
              hk = SerializeUtil.unserialize((byte[])entry.getKey());
            }
            HV hv = null;
            try {
              hv = SerializeUtil.unserialize((byte[])entry.getValue());
            } catch (Exception e) {
              hv = new String((byte[])entry.getValue());
            }
            tagResult.put(hk, hv);
          }
          
          return tagResult;
        }
      });
    } catch (Exception e) {
      LOGGER.error("getAllObjectFromDB error", e);
    }
    
    return null;
  }
  
  public static int getSize(String key)
  {
    if (StringUtils.isBlank(key).booleanValue()) {
      return 0;
    }
    try
    {
      ((Integer)JedisPoolHolder.getNamedRedisTemplate(null).execute(new RedisCallback()
      {
        public Integer doInRedis(RedisConnection connection) throws DataAccessException {
          Long size = connection.hLen(key.getBytes());
          return Integer.valueOf(size.intValue());
        }
      })).intValue();
    } catch (Exception e) {
      LOGGER.error("getSize error", e);
    }
    
    return 0;
  }
  
  public static <K> Set<K> getFields(String key)
  {
    if (StringUtils.isBlank(key).booleanValue()) {
      return null;
    }
    try
    {
      (Set)JedisPoolHolder.getNamedRedisTemplate(null).execute(new RedisCallback()
      {
        public Set<K> doInRedis(RedisConnection connection) throws DataAccessException
        {
          Set<byte[]> set = connection.hKeys(key.getBytes());
          if (set != null) {
            Set<K> keys = Collections.emptySet();
            for (byte[] key : set) {
              keys.add(key);
            }
            return keys;
          }
          
          return null;
        }
      });
    } catch (Exception e) {
      LOGGER.error("getFields error", e);
    }
    
    return null;
  }
  
  public static <V> Collection<V> getValues(String key)
  {
    if (StringUtils.isBlank(key).booleanValue()) {
      return null;
    }
    try
    {
      (Collection)JedisPoolHolder.getNamedRedisTemplate(null).execute(new RedisCallback()
      {
        public Collection<V> doInRedis(RedisConnection connection) throws DataAccessException
        {
          Collection<byte[]> list = connection.hVals(key.getBytes());
          if (list != null) {
            Collection<V> values = Collections.emptyList();
            for (byte[] value : list) {
              values.add(value);
            }
            return values;
          }
          
          return null;
        }
      });
    } catch (Exception e) {
      LOGGER.error("getValues error", e);
    }
    
    return null;
  }
  
  public static Cursor<String> scan(ScanOptions options)
  {
    return scanFromDB(null, options);
  }
  
  public static Cursor<String> scanFromDB(Integer db, ScanOptions options)
  {
    return scanFromDB(null, db, options);
  }
  
  public static Cursor<String> scanFromDB(String templateName, Integer db, final ScanOptions options)
  {
    try
    {
      (Cursor)JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new RedisCallback()
      {
        public Cursor<String> doInRedis(RedisConnection connection) throws DataAccessException
        {
          if (db != null) {
            connection.select(db.intValue());
          }
          
          (connection.scan(options), new Converter()
          {
            public String convert(byte[] source)
            {
              return new String(source);
            }
          });
        }
      }, true);
    }
    catch (Exception e)
    {
      LOGGER.error("scanFromDB error", e);
    }
    
    return null;
  }
  
  public static int countScanFromDB(Integer db, ScanOptions options) {
    return countScanFromDB(null, db, options);
  }
  
  public static int countScanFromDB(String templateName, Integer db, final ScanOptions options)
  {
    try
    {
      Cursor<String> cursor = (Cursor)JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new RedisCallback()
      {
        public Cursor<String> doInRedis(RedisConnection connection) throws DataAccessException
        {
          if (db != null) {
            connection.select(db.intValue());
          }
          (connection.scan(options), new Converter()
          {
            public String convert(byte[] source)
            {
              return new String(source);
            }
          });
        }
      }, true);
      
      int count = 0;
      while (cursor.hasNext()) {
        cursor.next();
        count++;
      }
      return count;
    } catch (Exception e) {
      LOGGER.error("countScanFromDB error", e);
    }
    
    return 0;
  }
  
  public static <HK, HV> Cursor<Map.Entry<HK, HV>> scanHash(String key, ScanOptions options)
  {
    return scanHashFromDB(null, key, options);
  }
  
  public static <HK, HV> Cursor<Map.Entry<HK, HV>> scanHashFromDB(Integer db, String key, ScanOptions options)
  {
    return scanHashFromDB(null, db, key, options);
  }
  
  public static <HK, HV> Cursor<Map.Entry<HK, HV>> scanHashFromDB(String templateName, Integer db, final String key, final ScanOptions options)
  {
    if (StringUtils.isBlank(key).booleanValue()) {
      return null;
    }
    try
    {
      
        (Cursor)JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new RedisCallback()
        {
          public Cursor<Map.Entry<HK, HV>> doInRedis(RedisConnection connection)
            throws DataAccessException
          {
            if (db != null) {
              connection.select(db.intValue());
            }
            
            (connection
              .hScan(key.getBytes(), options), new Converter()
              {
                public Map.Entry<HK, HV> convert(final Map.Entry<byte[], byte[]> source)
                {
                  new Object()
                  {
                    public HK getKey()
                    {
                      HK keyObj = null;
                      try {
                        keyObj = new String((byte[])source.getKey());
                      } catch (Exception e) {
                        keyObj = SerializeUtil.unserialize((byte[])source.getKey());
                      }
                      return keyObj;
                    }
                    
                    public HV getValue()
                    {
                      HV valueObj = null;
                      try {
                        valueObj = SerializeUtil.unserialize((byte[])source.getValue());
                      } catch (Exception e) {
                        valueObj = new String((byte[])source.getValue());
                      }
                      return valueObj;
                    }
                    
                    public HV setValue(HV value)
                    {
                      throw new UnsupportedOperationException("Values cannot be set when scanning through entries.");
                    }
                  };
                }
              });
            }
          }, true);
      }
      catch (Exception e)
      {
        LOGGER.error("scanHashFromDB error", e);
      }
      
      return null;
    }
    
    public static <HK, HV> List<Map.Entry<HK, HV>> scanHashLimitFromDB(Integer db, String key, ScanOptions options, int limit)
    {
      return scanHashLimitFromDB(null, db, key, options, limit);
    }
    
    public static <HK, HV> List<Map.Entry<HK, HV>> scanHashLimitFromDB(String templateName, Integer db, final String key, final ScanOptions options, int limit)
    {
      if (StringUtils.isBlank(key).booleanValue()) {
        return null;
      }
      try
      {
        List<Map.Entry<HK, HV>> list = new ArrayList();
        
        Cursor<Map.Entry<HK, HV>> cursor = (Cursor)JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new RedisCallback()
        {
          public Cursor<Map.Entry<HK, HV>> doInRedis(RedisConnection connection)
            throws DataAccessException
          {
            if (db != null) {
              connection.select(db.intValue());
            }
            
            (connection
              .hScan(key.getBytes(), options), new Converter()
              {
                public Map.Entry<HK, HV> convert(final Map.Entry<byte[], byte[]> source)
                {
                  new Object()
                  {
                    public HK getKey()
                    {
                      HK keyObj = null;
                      try {
                        keyObj = new String((byte[])source.getKey());
                      } catch (Exception e) {
                        keyObj = SerializeUtil.unserialize((byte[])source.getKey());
                      }
                      return keyObj;
                    }
                    
                    public HV getValue()
                    {
                      HV valueObj = null;
                      try {
                        valueObj = SerializeUtil.unserialize((byte[])source.getValue());
                      } catch (Exception e) {
                        valueObj = new String((byte[])source.getValue());
                      }
                      return valueObj;
                    }
                    
                    public HV setValue(HV value)
                    {
                      throw new UnsupportedOperationException("Values cannot be set when scanning through entries.");
                    }
                  };
                }
              });
            }
          }, true);
        
        int pos = 0;
        while ((cursor.hasNext()) && (pos < limit)) {
          list.add(cursor.next());
          pos++;
        }
        return list;
      } catch (Exception e) {
        LOGGER.error("scanHashLimitFromDB error", e);
      }
      
      return null;
    }
    
    public static <T> T removeObject(String key, String field)
    {
      return removeObjectFromDB(null, key, field);
    }
    
    public static <T> T removeObjectFromDB(Integer db, String key, String field)
    {
      return removeObjectFromDB(null, db, key, field);
    }
    
    public static <T> T removeObjectFromDB(String templateName, Integer db, final String key, final String field)
    {
      if ((StringUtils.isBlank(key).booleanValue()) || (StringUtils.isBlank(field).booleanValue())) {
        return null;
      }
      try
      {
        JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new RedisCallback()
        {
          public T doInRedis(RedisConnection connection) throws DataAccessException
          {
            if (db != null) {
              connection.select(db.intValue());
            }
            return connection.hDel(key.getBytes(), new byte[][] { field.getBytes() });
          }
        });
      } catch (Exception e) {
        LOGGER.error("removeObjectFromDB error", e);
      }
      
      return null;
    }
    
    public static <T> boolean lPush(String key, T... value)
    {
      return lPushFromDB(null, key, value);
    }
    
    public static <T> boolean lPushFromDB(Integer db, String key, T... value)
    {
      return lPushFromDB(db, true, key, value);
    }
    
    public static <T> boolean lPushFromDB(Integer db, boolean serializable, String key, T... value)
    {
      return lPushFromDB(null, db, serializable, key, value);
    }
    
    public static <T> boolean lPushFromDB(String templateName, Integer db, final boolean serializable, final String key, final T... value)
    {
      boolean flag = false;
      if ((StringUtils.isBlank(key).booleanValue()) || (value == null)) {
        return flag;
      }
      try
      {
        flag = ((Boolean)JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new Object())
        {
          public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
            if (db != null) {
              connection.select(db.intValue());
            }
            
            for (T v : value) {
              byte[] vbyte = null;
              
              if (serializable) {
                vbyte = SerializeUtil.serialize(v);
              }
              else if (v != null) {
                vbyte = v.toString().getBytes();
              }
              
              connection.lPush(key.getBytes(), new byte[][] { vbyte });
            }
            return Boolean.valueOf(true);
          }
        }()).booleanValue();
      } catch (Exception e) {
        LOGGER.error("lPushFromDB error", e);
      }
      
      return flag;
    }
    
    public static <T> T lPop(String key)
    {
      return lPopFromDB(null, key);
    }
    
    public static <T> T lPopFromDB(Integer db, String key)
    {
      return lPopFromDB(db, true, key);
    }
    
    public static <T> T lPopFromDB(Integer db, boolean serializable, String key)
    {
      return lPopFromDB(null, db, serializable, key);
    }
    
    public static <T> T lPopFromDB(String templateName, Integer db, final boolean serializable, final String key)
    {
      if (StringUtils.isBlank(key).booleanValue()) {
        return null;
      }
      try
      {
        JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new Object()
        {
          public T doInRedis(RedisConnection connection) throws DataAccessException
          {
            if (db != null) {
              connection.select(db.intValue());
            }
            
            T result = null;
            byte[] vbyte = connection.lPop(key.getBytes());
            
            if (serializable) {
              result = SerializeUtil.unserialize(vbyte);
            }
            else if (vbyte != null) {
              result = new String(vbyte);
            }
            
            return result;
          }
        });
      } catch (Exception e) {
        LOGGER.error("lPopFromDB error", e);
      }
      
      return null;
    }
    
    public static <T> T bRPop(String key, long timeout, TimeUnit unit)
    {
      return bRPopFromDB(null, true, key, timeout, unit);
    }
    
    public static <T> T bRPopFromDB(Integer db, boolean serializable, String key, long timeout, TimeUnit unit)
    {
      return bRPopFromDB(null, db, serializable, key, timeout, unit);
    }
    
    public static <T> T bRPopFromDB(String templateName, Integer db, final boolean serializable, final String key, final long timeout, TimeUnit unit)
    {
      if (StringUtils.isBlank(key).booleanValue()) {
        return null;
      }
      try
      {
        JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new Object()
        {
          public T doInRedis(RedisConnection connection) throws DataAccessException
          {
            if (db != null) {
              connection.select(db.intValue());
            }
            
            int tm = (int)TimeoutUtils.toSeconds(timeout, key);
            T result = null;
            List<byte[]> bRPop = connection.bRPop(tm, new byte[][] { serializable.getBytes() });
            byte[] vbyte = CollectionUtils.isEmpty(bRPop) ? null : (byte[])bRPop.get(1);
            
            if (serializable) {
              result = SerializeUtil.unserialize(vbyte);
            }
            else if (vbyte != null) {
              result = new String(vbyte);
            }
            
            return result;
          }
        });
      } catch (Exception e) {
        LOGGER.error("bRPopFromDB error", e);
      }
      
      return null;
    }
    
    public static int getLength(String key)
    {
      return getLength(null, key);
    }
    
    public static int getLength(RedisTemplate<Serializable, Serializable> template, String key)
    {
      if (StringUtils.isBlank(key).booleanValue()) {
        return 0;
      }
      
      if (template == null) {
        template = JedisPoolHolder.getRedisTemplate();
      }
      try
      {
        ((Integer)template.execute(new RedisCallback())
        {
          public Integer doInRedis(RedisConnection connection) throws DataAccessException {
            Long size = connection.lLen(key.getBytes());
            return Integer.valueOf(size.intValue());
          }
        }()).intValue();
      } catch (Exception e) {
        LOGGER.error("getLength error", e);
      }
      
      return 0;
    }
    
    public static <T> T getTimeToLive(String key)
    {
      return getTimeToLiveFromDB(null, key);
    }
    
    public static <T> T getTimeToLiveFromDB(Integer db, String key)
    {
      return getTimeToLiveFromDB(null, db, key);
    }
    
    public static <T> T getTimeToLiveFromDB(String templateName, Integer db, final String key)
    {
      if (StringUtils.isBlank(key).booleanValue()) {
        return null;
      }
      try
      {
        JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new RedisCallback()
        {
          public T doInRedis(RedisConnection connection) throws DataAccessException
          {
            if (db != null) {
              connection.select(db.intValue());
            }
            
            return connection.ttl(key.getBytes());
          }
        });
      } catch (Exception e) {
        LOGGER.error("getTimeToLiveFromDB error", e);
      }
      
      return null;
    }
    
    public static Properties getAllInfo()
    {
      return getAllInfo("redisTemplate");
    }
    
    public static Properties getAllInfo(RedisTemplate<Serializable, Serializable> template)
    {
      return getInfo(template, null);
    }
    
    public static Properties getAllInfo(String templateName) {
      RedisTemplate<Serializable, Serializable> template = JedisPoolHolder.getNamedRedisTemplate(templateName);
      return getInfo(template, null);
    }
    
    public static Properties getInfo(RedisTemplate<Serializable, Serializable> template, String section)
    {
      if (template == null) {
        template = JedisPoolHolder.getRedisTemplate();
      }
      
      (Properties)template.execute(new RedisCallback()
      {
        public Properties doInRedis(RedisConnection connection) throws DataAccessException {
          if (StringUtils.isNotBlank(section).booleanValue()) {
            return connection.info(section);
          }
          return connection.info();
        }
      });
    }
    
    public static Long incrBy(String key, Long incr)
    {
      return incrByFromDB(null, null, key, incr, 0L);
    }
    
    public static Long incrBy(String key, Long incr, long timeout)
    {
      return incrByFromDB(null, null, key, incr, timeout);
    }
    
    public static Long incrByFromDB(String templateName, Integer db, final String key, final Long incr, final long timeout)
    {
      if (StringUtils.isBlank(key).booleanValue()) {
        return Long.valueOf(0L);
      }
      try
      {
        (Long)JedisPoolHolder.getNamedRedisTemplate(templateName).execute(new RedisCallback()
        {
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
    
    public static Long time()
    {
      return time(null);
    }
    
    public static Long time(RedisTemplate<Serializable, Serializable> template)
    {
      if (template == null) {
        template = JedisPoolHolder.getRedisTemplate();
      }
      try
      {
        (Long)template.execute(new RedisCallback()
        {
          public Long doInRedis(RedisConnection connection) throws DataAccessException {
            return connection.time();
          }
        });
      } catch (Exception e) {
        LOGGER.error("incrByFromDB error", e);
      }
      
      return Long.valueOf(0L);
    }
    
    public static <T> List<T> pipelinedLPopFromDB(RedisTemplate<Serializable, Serializable> template, Integer db, final boolean serializable, final int size, final String key)
    {
      if ((StringUtils.isBlank(key).booleanValue()) || (size <= 0)) {
        return null;
      }
      
      if (template == null) {
        template = JedisPoolHolder.getRedisTemplate();
      }
      try
      {
        (List)template.execute(new Object()
        {
          public List<T> doInRedis(RedisConnection connection) throws DataAccessException
          {
            if (db != null) {
              connection.select(db.intValue());
            }
            
            List<T> resultList = new ArrayList(size);
            
            connection.openPipeline();
            
            for (int i = 0; i < size; i++) {
              connection.lPop(key.getBytes());
            }
            
            List<Object> pipelineList = connection.closePipeline();
            Iterator localIterator; if (pipelineList != null) {
              for (localIterator = pipelineList.iterator(); localIterator.hasNext();) { Object obj = localIterator.next();
                T result = null;
                
                if ((obj instanceof byte[])) {
                  byte[] vbyte = (byte[])obj;
                  
                  if (serializable) {
                    result = SerializeUtil.unserialize(vbyte);
                  }
                  else if (vbyte != null) {
                    result = new String(vbyte);
                  }
                }
                else {
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
    
    public static <T> boolean pipelinedLPushFromDB(RedisTemplate<Serializable, Serializable> template, Integer db, final boolean serializable, final String key, final List<T> value)
    {
      boolean flag = false;
      
      if (StringUtils.isBlank(key).booleanValue()) {
        return flag;
      }
      
      if (template == null) {
        template = JedisPoolHolder.getRedisTemplate();
      }
      try
      {
        flag = ((Boolean)template.execute(new Object())
        {
          public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
            if (db != null) {
              connection.select(db.intValue());
            }
            
            connection.openPipeline();
            
            for (Iterator localIterator = value.iterator(); localIterator.hasNext();) { T v = localIterator.next();
              byte[] vbyte = null;
              
              if (serializable) {
                vbyte = SerializeUtil.serialize(v);
              }
              else if (v != null) {
                vbyte = v.toString().getBytes();
              }
              
              connection.lPush(key.getBytes(), new byte[][] { vbyte });
            }
            
            connection.closePipeline();
            
            return Boolean.valueOf(true);
          }
        }()).booleanValue();
      } catch (Exception e) {
        LOGGER.error("pipelinedLPushFromDB error", e);
      }
      
      return flag;
    }
    
    public static <T> List<T> pipelinedRPopFromDB(RedisTemplate<Serializable, Serializable> template, Integer db, final boolean serializable, final int size, final String key)
    {
      if ((StringUtils.isBlank(key).booleanValue()) || (size <= 0)) {
        return null;
      }
      
      if (template == null) {
        template = JedisPoolHolder.getRedisTemplate();
      }
      try
      {
        (List)template.execute(new Object()
        {
          public List<T> doInRedis(RedisConnection connection) throws DataAccessException
          {
            if (db != null) {
              connection.select(db.intValue());
            }
            
            List<T> resultList = new ArrayList(size);
            
            connection.openPipeline();
            
            for (int i = 0; i < size; i++) {
              connection.rPop(key.getBytes());
            }
            
            List<Object> pipelineList = connection.closePipeline();
            Iterator localIterator; if (pipelineList != null) {
              for (localIterator = pipelineList.iterator(); localIterator.hasNext();) { Object obj = localIterator.next();
                T result = null;
                
                if ((obj instanceof byte[])) {
                  byte[] vbyte = (byte[])obj;
                  
                  if (serializable) {
                    result = SerializeUtil.unserialize(vbyte);
                  }
                  else if (vbyte != null) {
                    result = new String(vbyte);
                  }
                }
                else {
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
    
    public static <T> boolean pipelinedRPushFromDB(RedisTemplate<Serializable, Serializable> template, Integer db, final boolean serializable, final String key, final List<T> value)
    {
      boolean flag = false;
      
      if (StringUtils.isBlank(key).booleanValue()) {
        return flag;
      }
      
      if (template == null) {
        template = JedisPoolHolder.getRedisTemplate();
      }
      try
      {
        flag = ((Boolean)template.execute(new Object())
        {
          public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
            if (db != null) {
              connection.select(db.intValue());
            }
            
            connection.openPipeline();
            
            for (Iterator localIterator = value.iterator(); localIterator.hasNext();) { T v = localIterator.next();
              byte[] vbyte = null;
              
              if (serializable) {
                vbyte = SerializeUtil.serialize(v);
              }
              else if (v != null) {
                vbyte = v.toString().getBytes();
              }
              
              connection.rPush(key.getBytes(), new byte[][] { vbyte });
            }
            
            connection.closePipeline();
            
            return Boolean.valueOf(true);
          }
        }()).booleanValue();
      } catch (Exception e) {
        LOGGER.error("pipelinedRPushFromDB error", e);
      }
      
      return flag;
    }
    */
  }

class JedisPoolHolder
{
  public static RedisTemplate<Serializable, Serializable> getRedisTemplate()
  {
    return (RedisTemplate)SpringUtils.getBean("redisTemplate");
  }
  
  public static RedisTemplate<Serializable, Serializable> getNamedRedisTemplate(String name)
  {
    RedisTemplate<Serializable, Serializable> redisTemplate = null;
    
    if (StringUtils.isNotBlank(name)) {
      redisTemplate = (RedisTemplate)SpringUtils.getBean(name);
    } else {
      redisTemplate = (RedisTemplate)SpringUtils.getBean("redisTemplate");
    }
    
    return redisTemplate;
  }
}
