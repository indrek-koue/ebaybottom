package com.titanium.ebaybottom.model;

public class UserMessage {

	private String title;
	private String msg;

	public UserMessage(String title, String msg) {
		this.setTitle(title);
		this.setMsg(msg);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "TITLE:" + title + " MESSAGE:" + msg;
	}
}
