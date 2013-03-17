
package com.titanium.ebaybottom.model;

import java.util.List;

public class Seller{
   	private List<SellerInfo> sellerInfo;

 	public List<SellerInfo> getSellerInfo(){
		return this.sellerInfo;
	}
	public void setSellerInfo(List<SellerInfo> sellerInfo){
		this.sellerInfo = sellerInfo;
	}
}
