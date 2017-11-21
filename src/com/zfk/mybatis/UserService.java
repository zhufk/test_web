package com.zfk.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class UserService {
	SqlSessionFactory sqlSessionFactory = MybatisDBUtil.getSqlSessionFactory();
	
	public List<User> listAllUser() {
		SqlSession session = sqlSessionFactory.openSession();
		List<User> users = session.selectList("mybatis.UserMapper.listAllUser");
		return users;
	}
	
	public User getUser(String id){
		SqlSession session = sqlSessionFactory.openSession();
		User user = session.selectOne("mybatis.UserMapper.getUser",id);
		return user;
	}
	
	/**
	 * 后面3个注意事物
	 */
	public int insertUser(User user){
		SqlSession session = sqlSessionFactory.openSession();
		int count=session.insert("mybatis.UserMapper.insertUser", user);
		session.commit();
	    return count;
	}
	
	public int updateUser(User user) {
		SqlSession session = sqlSessionFactory.openSession();
		int count=session.update("mybatis.UserMapper.updateUser", user);
		session.commit();
		return count;
	}
	
	public int deleteUser(String id) {
		SqlSession session = sqlSessionFactory.openSession();
		int count=session.delete("mybatis.UserMapper.deleteUser", id);
		session.commit();
		return count;
	}
}
