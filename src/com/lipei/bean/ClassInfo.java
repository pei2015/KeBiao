
package com.lipei.bean;

public class ClassInfo {
	private int fromX;
	private int fromY;
	private int toX;
	private int toY;
    
	private String user_id;
	private String classid;
	private String classname;
	private int fromClassNum;
	private int classNumLen;
	private int weekday;
	private String classRoom;
	private String weeks;
	private String time1;
	private String time2;
	private String time3;
	private String teachter_name;
	private String number;
	private String seat_num;

	public void setPoint(int fromX, int fromY, int toX, int toY) {
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
	}

	public int getFromX() {
		return fromX;
	}

	public void setFromX(int fromX) {
		this.fromX = fromX;
	}

	public int getFromY() {
		return fromY;
	}

	public void setFromY(int fromY) {
		this.fromY = fromY;
	}

	public int getToX() {
		return toX;
	}

	public void setToX(int toX) {
		this.toX = toX;
	}

	public int getToY() {
		return toY;
	}

	public void setToY(int toY) {
		this.toY = toY;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public int getFromClassNum() {
		return fromClassNum;
	}

	public void setFromClassNum(int fromClassNum) {
		this.fromClassNum = fromClassNum;
	}

	public int getClassNumLen() {
		return classNumLen;
	}

	public void setClassNumLen(int classNumLen) {
		this.classNumLen = classNumLen;
	}

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}

	public String getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public String getTime1() {
		return time1;
	}

	public void setTime1(String time1) {
		this.time1 = time1;
	}

	public String getTime2() {
		return time2;
	}

	public void setTime2(String time2) {
		this.time2 = time2;
	}

	public String getTime3() {
		return time3;
	}

	public String getTeachter_name() {
		return teachter_name;
	}

	public void setTeachter_name(String teachter_name) {
		this.teachter_name = teachter_name;
	}

	public void setTime3(String time3) {
		this.time3 = time3;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getSeat_num() {
		return seat_num;
	}

	public void setSeat_num(String seat_num) {
		this.seat_num = seat_num;
	}

}
