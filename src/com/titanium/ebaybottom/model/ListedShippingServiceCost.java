
package com.titanium.ebaybottom.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ListedShippingServiceCost{
   
	@SerializedName("CurrencyID")
	private String currencyID;
	@SerializedName("Value")
   	private Number value;

 	public String getCurrencyID(){
		return this.currencyID;
	}
	public void setCurrencyID(String currencyID){
		this.currencyID = currencyID;
	}
 	public Number getValue(){
		return this.value;
	}
	public void setValue(Number value){
		this.value = value;
	}
}
