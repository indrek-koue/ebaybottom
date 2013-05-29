
package com.titanium.ebaybottom.model;

import java.util.List;

public class ListingInfo{
   	private List<String> bestOfferEnabled;
   	private List<String> buyItNowAvailable;
   	private List<String> endTime;
   	private List<String> gift;
   	private List<String> listingType;
   	private List<String> startTime;

 	public List<String> getBestOfferEnabled(){
		return this.bestOfferEnabled;
	}
	public void setBestOfferEnabled(List<String> bestOfferEnabled){
		this.bestOfferEnabled = bestOfferEnabled;
	}
 	public List<String> getBuyItNowAvailable(){
		return this.buyItNowAvailable;
	}
	public void setBuyItNowAvailable(List<String> buyItNowAvailable){
		this.buyItNowAvailable = buyItNowAvailable;
	}
 	public List<String> getEndTime(){
		return this.endTime;
	}
	public void setEndTime(List<String> endTime){
		this.endTime = endTime;
	}
 	public List<String> getGift(){
		return this.gift;
	}
	public void setGift(List<String> gift){
		this.gift = gift;
	}
 	public List<String> getListingType(){
		return this.listingType;
	}
	public void setListingType(List<String> listingType){
		this.listingType = listingType;
	}
 	public List<String> getStartTime(){
		return this.startTime;
	}
	public void setStartTime(List<String> startTime){
		this.startTime = startTime;
	}
}
