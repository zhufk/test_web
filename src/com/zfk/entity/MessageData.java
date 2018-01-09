package com.zfk.entity;


import java.io.Serializable;
import com.alibaba.fastjson.JSONObject;

public class MessageData implements Serializable{
	private static final long serialVersionUID = 3244401049985486450L;
	
	/*机器人信息*/
	String robotId;
	String robotName;
	
	/*用户信息*/
	String userId;
	String userName;
	
	/*内容*/
	String content;
	/*时间*/
	String time;
	
	/*类型：0，问；1答*/
	String type;
	
	public String getRobotId() {
		return robotId;
	}
	public void setRobotId(String robotId) {
		this.robotId = robotId;
	}
	public String getRobotName() {
		return robotName;
	}
	public void setRobotName(String robotName) {
		this.robotName = robotName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return JSONObject.toJSONString(this);
	}
}
