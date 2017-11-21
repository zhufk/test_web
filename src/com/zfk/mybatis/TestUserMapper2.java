package com.zfk.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public class TestUserMapper2 {
	static SqlSessionFactory sqlSessionFactory = MybatisDBUtil.getSqlSessionFactory();
	
	public static void main(String[] args) {
		SqlSession session = sqlSessionFactory.openSession();
		UserMapper2 userMapper = session.getMapper(UserMapper2.class);
		int count = userMapper.insertUser2(new User("cc","小猪",111));
		session.commit();//记得提交事务
		System.out.println(count);
		List<User> users = userMapper.listAllUser2();
//		List<User> users = session.selectList("mybatis.UserMapper2.listAllUser");
		System.out.println(users);
	}
	
}
