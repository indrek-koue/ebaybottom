package com.titanium.ebaybottom.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SearchResult {

	@SerializedName("Timestamp")
	private String timestamp;
	@SerializedName("Ack")
	private String ack;
	@SerializedName("Build")
	private String build;
	@SerializedName("Version")
	private String version;
	@SerializedName("TotalItems")
	private int totalItems;
	@SerializedName("ItemSearchURL")
	private String itemSearchURL;

	@SerializedName("Item")
	private List<Item> items;

	@Override
	public String toString() {
		
		String is = "";
		for (Item i : items) {
			is += i;
		}
		
		return "SearchResult [timestamp=" + timestamp + ", ack=" + ack
				+ ", build=" + build + ", version=" + version + ", totalItems="
				+ totalItems + ", itemSearchURL=" + itemSearchURL + ", items="
				+ items.size() + "] " + is;
	}


	public SearchResult() {
		super();
	}


	public List<Item> getItems() {
		return this.items;
	}

	public void setItems(List<Item> item) {
		this.items = item;
	}

	public String getAck() {
		return this.ack;
	}

	public void setAck(String ack) {
		this.ack = ack;
	}

	public String getBuild() {
		return this.build;
	}

	public void setBuild(String build) {
		this.build = build;
	}

	public String getItemSearchURL() {
		return this.itemSearchURL;
	}

	public void setItemSearchURL(String itemSearchURL) {
		this.itemSearchURL = itemSearchURL;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public int getTotalItems() {
		return this.totalItems;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
