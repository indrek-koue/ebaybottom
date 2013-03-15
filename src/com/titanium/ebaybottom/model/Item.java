package com.titanium.ebaybottom.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Item {

	@SerializedName("BidCount")
	private Number bidCount;
	
	@SerializedName("ConvertedCurrentPrice")
	private ConvertedCurrentPrice convertedCurrentPrice;
	
	@SerializedName("EndTime")
	private String endTime;
	
	@SerializedName("GalleryURL")
	private String galleryURL;
	
	@SerializedName("ItemID")
	private String itemID;
	
	@SerializedName("ListingStatus")
	private String listingStatus;
	
	@SerializedName("ListingType")
	private String listingType;
	
	@SerializedName("PrimaryCategoryID")
	private String primaryCategoryID;
	
	@SerializedName("PrimaryCategoryName")
	private String primaryCategoryName;
	
	@SerializedName("ShippingCostSummary")
	private ShippingCostSummary shippingCostSummary;
	
	@SerializedName("TimeLeft")
	private String timeLeft;
	
	@SerializedName("Title")
	private String title;
	
	@SerializedName("ViewItemURLForNaturalSearch")
	private String viewItemURLForNaturalSearch;

	
	
	@Override
	public String toString() {
		return "\n#Item [galleryURL=" + galleryURL + ", itemID=" + itemID
				+ ", title=" + title + "]";
	}

	public Number getBidCount() {
		return this.bidCount;
	}

	public void setBidCount(Number bidCount) {
		this.bidCount = bidCount;
	}

	public ConvertedCurrentPrice getConvertedCurrentPrice() {
		return this.convertedCurrentPrice;
	}

	public void setConvertedCurrentPrice(
			ConvertedCurrentPrice convertedCurrentPrice) {
		this.convertedCurrentPrice = convertedCurrentPrice;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getGalleryURL() {
		return this.galleryURL;
	}

	public void setGalleryURL(String galleryURL) {
		this.galleryURL = galleryURL;
	}

	public String getItemID() {
		return this.itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public String getListingStatus() {
		return this.listingStatus;
	}

	public void setListingStatus(String listingStatus) {
		this.listingStatus = listingStatus;
	}

	public String getListingType() {
		return this.listingType;
	}

	public void setListingType(String listingType) {
		this.listingType = listingType;
	}

	public String getPrimaryCategoryID() {
		return this.primaryCategoryID;
	}

	public void setPrimaryCategoryID(String primaryCategoryID) {
		this.primaryCategoryID = primaryCategoryID;
	}

	public String getPrimaryCategoryName() {
		return this.primaryCategoryName;
	}

	public void setPrimaryCategoryName(String primaryCategoryName) {
		this.primaryCategoryName = primaryCategoryName;
	}

	public ShippingCostSummary getShippingCostSummary() {
		return this.shippingCostSummary;
	}

	public void setShippingCostSummary(ShippingCostSummary shippingCostSummary) {
		this.shippingCostSummary = shippingCostSummary;
	}

	public String getTimeLeft() {
		return this.timeLeft;
	}

	public void setTimeLeft(String timeLeft) {
		this.timeLeft = timeLeft;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getViewItemURLForNaturalSearch() {
		return this.viewItemURLForNaturalSearch;
	}

	public void setViewItemURLForNaturalSearch(
			String viewItemURLForNaturalSearch) {
		this.viewItemURLForNaturalSearch = viewItemURLForNaturalSearch;
	}
}
