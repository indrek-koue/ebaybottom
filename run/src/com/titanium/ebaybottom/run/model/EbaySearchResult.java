package com.titanium.ebaybottom.run.model;

import java.util.List;

public class EbaySearchResult {
	private List<FindItemsAdvancedResponse> findItemsAdvancedResponse;

	public List<FindItemsAdvancedResponse> getFindItemsAdvancedResponse() {
		return this.findItemsAdvancedResponse;
	}

	public void setFindItemsAdvancedResponse(
			List<FindItemsAdvancedResponse> findItemsAdvancedResponse) {
		this.findItemsAdvancedResponse = findItemsAdvancedResponse;
	}

	private List<ErrorMessage> errorMessage;


	public List<ErrorMessage> getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(List<ErrorMessage> error) {
		this.errorMessage = error;
	}
}
