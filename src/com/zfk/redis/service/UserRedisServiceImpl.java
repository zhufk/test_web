package com.zfk.redis.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.zfk.entity.User;
import com.zfk.redis.SerializeUtil;

@Service
public class UserRedisServiceImpl implements UserRedisService {

	@Resource
	protected RedisTemplate<Serializable, Serializable> redisTemplate;

	@Override
	public int insertUser(User user) {
		try {
			redisTemplate.execute(new RedisCallback<Integer>() {
				@Override
				public Integer doInRedis(RedisConnection connection) throws DataAccessException {
					return connection.setNX(user.getId().getBytes(), SerializeUtil.serialize(user)) == true ? 0 : -1;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	@Override
	public int insertUserBatch(List<User> users) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUserBatch(List<User> users) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteUser(String id) {
		redisTemplate.delete(id);
		return 0;
	}

	@Override
	public int deleteUserBatch(List<String> ids) {
		// redisTemplate.delete(ids);
		return 0;
	}

	@Override
	public User getUser(String id) {
		User result = redisTemplate.execute(new RedisCallback<User>() {
			public User doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] value = connection.get(id.getBytes());
				if (value == null) {
					return null;
				}
				return (User) SerializeUtil.unserialize(value);
			}
		});
		return result;
	}

}
