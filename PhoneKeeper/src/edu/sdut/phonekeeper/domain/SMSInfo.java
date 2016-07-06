package edu.sdut.phonekeeper.domain;

import java.text.SimpleDateFormat;

public class SMSInfo {
	private int _id;
	private String num;
	private String content;
	private long time;
	
	
	public SMSInfo(int _id, String num, String content, long time) {
		super();
		this._id = _id;
		this.num = num;
		this.content = content;
		this.time = time;
	}
	
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getContext() {
		return content;
	}
	public void setContext(String context) {
		this.content = context;
	}
	public String getTimeString() {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
        String dateStr = format.format(time);  
        
		return dateStr;
	}
	public long getTime() {
		       
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	
}
