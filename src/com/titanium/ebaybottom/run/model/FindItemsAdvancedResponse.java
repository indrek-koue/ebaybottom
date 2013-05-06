package com.titanium.ebaybottom.run.model;

import java.util.List;

public class FindItemsAdvancedResponse {
	private List<String> ack;
	private List<String> itemSearchURL;
	private List<PaginationOutput> paginationOutput;
	private List<SearchResult> searchResult;
	private List<String> timestamp;
	private List<String> version;
	
	private List<ErrorMessage> errorMessage;

	public List<String> getAck() {
		return this.ack;
	}

	public void setAck(List<String> ack) {
		this.ack = ack;
	}

	public List<String> getItemSearchURL() {
		return this.itemSearchURL;
	}

	public void setItemSearchURL(List<String> itemSearchURL) {
		this.itemSearchURL = itemSearchURL;
	}

	public List<PaginationOutput> getPaginationOutput() {
		return this.paginationOutput;
	}

	public void setPaginationOutput(List<PaginationOutput> paginationOutput) {
		this.paginationOutput = paginationOutput;
	}

	public List<SearchResult> getSearchResult() {
		return this.searchResult;
	}

	public void setSearchResult(List<SearchResult> searchResult) {
		this.searchResult = searchResult;
	}

	public List<String> getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(List<String> timestamp) {
		this.timestamp = timestamp;
	}

	public List<String> getVersion() {
		return this.version;
	}

	public void setVersion(List<String> version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return String
				.format("status=%s, results=%s, timestamp=%s, APIversion=%s, itemSearchURL=%s]",
						ack, searchResult.get(0).getItem().size(),
						timestamp, version, itemSearchURL);
	}

	public List<ErrorMessage> getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(List<ErrorMessage> errorMessage) {
		this.errorMessage = errorMessage;
	}
}
