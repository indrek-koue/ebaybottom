
package com.titanium.ebaybottom.model;

import java.util.List;

public class ConvertedCurrentPrice{
   	private String currencyID;
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
