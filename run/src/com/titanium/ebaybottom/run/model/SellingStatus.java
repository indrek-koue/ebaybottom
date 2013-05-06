
package com.titanium.ebaybottom.run.model;

import java.util.List;

public class SellingStatus{
   	private List<ConvertedCurrentPrice> convertedCurrentPrice;
   	private List<CurrentPrice> currentPrice;
   	private List<String> sellingState;
   	private List<String> timeLeft;

 	public List<ConvertedCurrentPrice> getConvertedCurrentPrice(){
		return this.convertedCurrentPrice;
	}
	public void setConvertedCurrentPrice(List<ConvertedCurrentPrice> convertedCurrentPrice){
		this.convertedCurrentPrice = convertedCurrentPrice;
	}
 	public List<CurrentPrice> getCurrentPrice(){
		return this.currentPrice;
	}
	public void setCurrentPrice(List<CurrentPrice> currentPrice){
		this.currentPrice = currentPrice;
	}
 	public List<String> getSellingState(){
		return this.sellingState;
	}
	public void setSellingState(List<String> sellingState){
		this.sellingState = sellingState;
	}
 	public List<String> getTimeLeft(){
		return this.timeLeft;
	}
	public void setTimeLeft(List<String> timeLeft){
		this.timeLeft = timeLeft;
	}
}
