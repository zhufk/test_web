package com.zfk.mybatis;

public class Book {
	String id;
	String name;
	String userId;
	
	public Book() {
		super();
	}

	public Book(String id, String name, String userId) {
		super();
		this.id = id;
		this.name = name;
		this.userId = userId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", userId=" + userId + "]";
	}
	
	
}
