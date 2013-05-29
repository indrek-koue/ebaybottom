package com.titanium.ebaybottom.model;

public class Pair<K, V> {

	public static final String VALUE_SPLITTER = ":";
	private K key;
	private V value;

	public Pair(K key, V value) {
		this.setKey(key);
		this.setValue(value);
	}

	public K getKey() {
		return key;
	}

	public void setKey(K title) {
		this.key = title;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V msg) {
		this.value = msg;
	}

	@Override
	public String toString() {
		return key.toString() + VALUE_SPLITTER + value.toString();
	}
}
