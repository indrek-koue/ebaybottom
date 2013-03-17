
package com.titanium.ebaybottom.model;

import java.util.List;

public class SearchResult{
   	private String count;
   	private List<Item> item;

 	public String getCount(){
		return this.count;
	}
	public void setCount(String count){
		this.count = count;
	}
 	public List<Item> getItem(){
		return this.item;
	}
	public void setItem(List<Item> item){
		this.item = item;
	}
}
