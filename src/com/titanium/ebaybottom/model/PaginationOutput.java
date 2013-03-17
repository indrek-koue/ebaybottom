
package com.titanium.ebaybottom.model;

import java.util.List;

public class PaginationOutput{
   	private List<String> entriesPerPage;
   	private List<String> pageNumber;
   	private List<String> totalEntries;
   	private List<String> totalPages;

 	public List<String> getEntriesPerPage(){
		return this.entriesPerPage;
	}
	public void setEntriesPerPage(List<String> entriesPerPage){
		this.entriesPerPage = entriesPerPage;
	}
 	public List<String> getPageNumber(){
		return this.pageNumber;
	}
	public void setPageNumber(List<String> pageNumber){
		this.pageNumber = pageNumber;
	}
 	public List<String> getTotalEntries(){
		return this.totalEntries;
	}
	public void setTotalEntries(List<String> totalEntries){
		this.totalEntries = totalEntries;
	}
 	public List<String> getTotalPages(){
		return this.totalPages;
	}
	public void setTotalPages(List<String> totalPages){
		this.totalPages = totalPages;
	}
}
