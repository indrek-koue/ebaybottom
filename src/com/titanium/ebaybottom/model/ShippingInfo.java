
package com.titanium.ebaybottom.model;

import java.util.List;

public class ShippingInfo{
   	private List<String> expeditedShipping;
   	private List<String> handlingTime;
   	private List<String> oneDayShippingAvailable;
   	private List<String> shipToLocations;
   	private List<ShippingServiceCost> shippingServiceCost;
   	private List<String> shippingType;

 	public List<String> getExpeditedShipping(){
		return this.expeditedShipping;
	}
	public void setExpeditedShipping(List<String> expeditedShipping){
		this.expeditedShipping = expeditedShipping;
	}
 	public List<String> getHandlingTime(){
		return this.handlingTime;
	}
	public void setHandlingTime(List<String> handlingTime){
		this.handlingTime = handlingTime;
	}
 	public List<String> getOneDayShippingAvailable(){
		return this.oneDayShippingAvailable;
	}
	public void setOneDayShippingAvailable(List<String> oneDayShippingAvailable){
		this.oneDayShippingAvailable = oneDayShippingAvailable;
	}
 	public List<String> getShipToLocations(){
		return this.shipToLocations;
	}
	public void setShipToLocations(List<String> shipToLocations){
		this.shipToLocations = shipToLocations;
	}
 	public List<ShippingServiceCost> getShippingServiceCost(){
		return this.shippingServiceCost;
	}
	public void setShippingServiceCost(List<ShippingServiceCost> shippingServiceCost){
		this.shippingServiceCost = shippingServiceCost;
	}
 	public List<String> getShippingType(){
		return this.shippingType;
	}
	public void setShippingType(List<String> shippingType){
		this.shippingType = shippingType;
	}
}
