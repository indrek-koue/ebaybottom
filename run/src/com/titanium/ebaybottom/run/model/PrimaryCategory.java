
package com.titanium.ebaybottom.run.model;

import java.util.List;

public class PrimaryCategory{
   	private List<String> categoryId;
   	private List<String> categoryName;

 	public List<String> getCategoryId(){
		return this.categoryId;
	}
	public void setCategoryId(List<String> categoryId){
		this.categoryId = categoryId;
	}
 	public List<String> getCategoryName(){
		return this.categoryName;
	}
	public void setCategoryName(List<String> categoryName){
		this.categoryName = categoryName;
	}
}
