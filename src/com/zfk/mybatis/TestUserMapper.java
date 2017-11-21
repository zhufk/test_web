package com.zfk.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class TestUserMapper {
	static SqlSessionFactory sqlSessionFactory = MybatisDBUtil.getSqlSessionFactory();
	
	public static void main(String[] args) throws IOException {
		InputStream inputStream = Resources.getResourceAsStream("config.xml");
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		SqlSession session = sqlSessionFactory.openSession();
		System.out.println(session);
		listAllUser();
//		getUser();
//		insertUser();
//		updateUser();
//		deleteUser();
		
//		listAllBook();
//		getBookById();
//		getBookByUserId();
//		insertBook();
//		updateBook();
//		deleteBook();
	}
	

	private static void listAllUser() {
		SqlSession session = sqlSessionFactory.openSession();
		List<User> users = session.selectList("com.zfk.mybatis.UserMapper.listAllUser");
		System.out.println(users);
	}
	
	private static void getUser(){
		SqlSession session = sqlSessionFactory.openSession();
		User user = session.selectOne("com.zfk.mybatis.UserMapper.getUser","aa");
		System.out.println(user);
	}
	
	/**
	 * 后面3个注意事物
	 */
	private static void insertUser(){
		SqlSession session = sqlSessionFactory.openSession();
		User user = new User("cc","小猪",12);
		int count=session.insert("com.zfk.mybatis.UserMapper.insertUser", user);
		session.commit();
	    System.out.println(count);
	}
	
	private static void updateUser() {
		SqlSession session = sqlSessionFactory.openSession();
		User user = new User("cc","小猪",122);
		int count=session.update("com.zfk.mybatis.UserMapper.updateUser", user);
		session.commit();
	    System.out.println(count);
	}
	
	private static void deleteUser() {
		SqlSession session = sqlSessionFactory.openSession();
		int count=session.delete("com.zfk.mybatis.UserMapper.deleteUser", "cc");
		session.commit();
		System.out.println(count);
	}
	
	////////////////////////////////////////////////////////////////////////////
	private static void listAllBook() {
		SqlSession session = sqlSessionFactory.openSession();
		List<Book> users = session.selectList("com.zfk.mybatis.BookMapper.listAllBook");
		System.out.println(users);
	}
	
	private static void getBookById(){
		SqlSession session = sqlSessionFactory.openSession();
		Book user = session.selectOne("com.zfk.mybatis.BookMapper.getBookById","11");
		System.out.println(user);
	}
	
	private static void getBookByUserId(){
		SqlSession session = sqlSessionFactory.openSession();
		List<Book> books = session.selectList("mybatis.BookMapper.getBookByUserId","aa");
		System.out.println(books);
	}
	
	/**
	 * 后面3个注意事物
	 */
	private static void insertBook(){
		SqlSession session = sqlSessionFactory.openSession();
		Book user = new Book("111","小猪的书","cc");
		int count=session.insert("mybatis.BookMapper.insertBook", user);
		session.commit();
	    System.out.println(count);
	}
	
	private static void updateBook() {
		SqlSession session = sqlSessionFactory.openSession();
		Book user = new Book("111","小猪的书2","cc");
		int count=session.update("mybatis.BookMapper.updateBook", user);
		session.commit();
	    System.out.println(count);
	}
	
	private static void deleteBook() {
		SqlSession session = sqlSessionFactory.openSession();
		int count=session.delete("mybatis.BookMapper.deleteBook", "111");
		session.commit();
		System.out.println(count);
	}


	


	
}
