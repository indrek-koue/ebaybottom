package com.titanium.ebaybottom.run.model;

public class Pair<K, V> {

	private static final String VALUE_SPLITTER = ":";
	private K key;
	private V value;

	public Pair(K key, V value) {
		this.setKey(key);
		this.setValue(value);
	}

	public Pair(String string) {
		this.setKey((K) string.split(VALUE_SPLITTER)[0]);
		this.setValue((V) string.split(VALUE_SPLITTER)[1]);
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
