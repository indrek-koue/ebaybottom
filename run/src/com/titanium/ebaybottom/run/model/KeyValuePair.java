package com.titanium.ebaybottom.run.model;

public class KeyValuePair {

	private String key;
	private String value;

	public KeyValuePair(String key, String value) {
		this.setKey(key);
		this.setValue(value);
	}

	public String getKey() {
		return key;
	}

	public void setKey(String title) {
		this.key = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String msg) {
		this.value = msg;
	}

	@Override
	public String toString() {
		return key + ":" + value;
	}
}
