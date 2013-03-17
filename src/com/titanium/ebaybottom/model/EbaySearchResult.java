
package com.titanium.ebaybottom.model;

import java.util.List;

public class EbaySearchResult{
   	private List<FindItemsAdvancedResponse> findItemsAdvancedResponse;

 	public List<FindItemsAdvancedResponse> getFindItemsAdvancedResponse(){
		return this.findItemsAdvancedResponse;
	}
	public void setFindItemsAdvancedResponse(List<FindItemsAdvancedResponse> findItemsAdvancedResponse){
		this.findItemsAdvancedResponse = findItemsAdvancedResponse;
	}
}
