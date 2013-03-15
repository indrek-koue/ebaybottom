
package com.titanium.ebaybottom.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ShippingCostSummary{
	@SerializedName("ListedShippingServiceCost")
   	private ListedShippingServiceCost listedShippingServiceCost;
   	
	@SerializedName("ShippingServiceCost")
	private ShippingServiceCost shippingServiceCost;
   	
	@SerializedName("ShippingType")
	private String shippingType;

 	public ListedShippingServiceCost getListedShippingServiceCost(){
		return this.listedShippingServiceCost;
	}
	public void setListedShippingServiceCost(ListedShippingServiceCost listedShippingServiceCost){
		this.listedShippingServiceCost = listedShippingServiceCost;
	}
 	public ShippingServiceCost getShippingServiceCost(){
		return this.shippingServiceCost;
	}
	public void setShippingServiceCost(ShippingServiceCost shippingServiceCost){
		this.shippingServiceCost = shippingServiceCost;
	}
 	public String getShippingType(){
		return this.shippingType;
	}
	public void setShippingType(String shippingType){
		this.shippingType = shippingType;
	}
}
