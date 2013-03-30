package com.titanium.ebaybottom.model;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import core.HistoryWriterReader;

public class Item {
	private List<String> autoPay;
	private List<Condition> condition;
	private List<String> country;
	private List<String> galleryURL;
	// The identifier for the site on which the item is listed. Returns a Global
	// ID, which is a unique identifier that specifies the combination of the
	// site, language, and territory. In other eBay APIs (such as the Shopping
	// API), this value is know as the site ID.
	private List<String> globalId;
	private List<String> isMultiVariationListing;
	// The ID that uniquely identifies the item listing. eBay generates this ID
	// when an item is listed. ID values are unique across all eBay sites.
	private List<String> itemId;
	private List<ListingInfo> listingInfo;
	private List<String> location;
	private List<String> paymentMethod;
	private List<String> postalCode;
	private List<PrimaryCategory> primaryCategory;
	// Unique identifier for the eBay catalog product with which the item was
	// listed. An eBay catalog product consists of pre-filled Item Specifics,
	// additional descriptive information, plus a stock photo (if available).
	// These product details are used to pre-fill item information, which is
	// used to describe the item and can also help surface the item in searches.
	//
	// eBay supports the following types of product ID types: ISBN, UPC, EAN,
	// and ReferenceID (ePID, also known as an eBay Product Reference ID).
	// ReferenceID values are returned when available. A UPC, ISBN, or EAN
	// product identifier will be returned only when a ReferenceID is not
	// available.
	private List<ProductId> productId;
	private List<String> returnsAccepted;
	private List<SellingStatus> sellingStatus;
	private List<ShippingInfo> shippingInfo;
	private List<String> title;
	private List<String> topRatedListing;
	private List<String> viewItemURL;
	private List<SellerInfo> sellerInfo;

	public List<String> getAutoPay() {
		return this.autoPay;
	}

	public void setAutoPay(List<String> autoPay) {
		this.autoPay = autoPay;
	}

	public List<Condition> getCondition() {
		return this.condition;
	}

	public void setCondition(List<Condition> condition) {
		this.condition = condition;
	}

	public List<String> getCountry() {
		return this.country;
	}

	public void setCountry(List<String> country) {
		this.country = country;
	}

	public List<String> getGalleryURL() {
		return this.galleryURL;
	}

	public void setGalleryURL(List<String> galleryURL) {
		this.galleryURL = galleryURL;
	}

	public List<String> getGlobalId() {
		return this.globalId;
	}

	public void setGlobalId(List<String> globalId) {
		this.globalId = globalId;
	}

	public List<String> getIsMultiVariationListing() {
		return this.isMultiVariationListing;
	}

	public void setIsMultiVariationListing(List<String> isMultiVariationListing) {
		this.isMultiVariationListing = isMultiVariationListing;
	}

	public List<String> getItemId() {
		return this.itemId;
	}

	public void setItemId(List<String> itemId) {
		this.itemId = itemId;
	}

	public List<ListingInfo> getListingInfo() {
		return this.listingInfo;
	}

	public void setListingInfo(List<ListingInfo> listingInfo) {
		this.listingInfo = listingInfo;
	}

	public List<String> getLocation() {
		return this.location;
	}

	public void setLocation(List<String> location) {
		this.location = location;
	}

	public List<String> getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(List<String> paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public List<String> getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(List<String> postalCode) {
		this.postalCode = postalCode;
	}

	public List<PrimaryCategory> getPrimaryCategory() {
		return this.primaryCategory;
	}

	public void setPrimaryCategory(List<PrimaryCategory> primaryCategory) {
		this.primaryCategory = primaryCategory;
	}

	public List<ProductId> getProductId() {
		return this.productId;
	}

	public void setProductId(List<ProductId> productId) {
		this.productId = productId;
	}

	public List<String> getReturnsAccepted() {
		return this.returnsAccepted;
	}

	public void setReturnsAccepted(List<String> returnsAccepted) {
		this.returnsAccepted = returnsAccepted;
	}

	public List<SellingStatus> getSellingStatus() {
		return this.sellingStatus;
	}

	public void setSellingStatus(List<SellingStatus> sellingStatus) {
		this.sellingStatus = sellingStatus;
	}

	public List<ShippingInfo> getShippingInfo() {
		return this.shippingInfo;
	}

	public void setShippingInfo(List<ShippingInfo> shippingInfo) {
		this.shippingInfo = shippingInfo;
	}

	public List<String> getTitle() {
		return this.title;
	}

	public void setTitle(List<String> title) {
		this.title = title;
	}

	public List<String> getTopRatedListing() {
		return this.topRatedListing;
	}

	public void setTopRatedListing(List<String> topRatedListing) {
		this.topRatedListing = topRatedListing;
	}

	public List<String> getViewItemURL() {
		return this.viewItemURL;
	}

	public void setViewItemURL(List<String> viewItemURL) {
		this.viewItemURL = viewItemURL;
	}

	public List<SellerInfo> getSellerInfo() {
		return sellerInfo;
	}

	public void setSellerInfo(List<SellerInfo> sellerInfo) {
		this.sellerInfo = sellerInfo;
	}

	@Override
	public String toString() {
		return String.format("%s %s %s$ %s %s %s %s", sellerInfo.get(0)
				.getSellerUserName().get(0), HistoryWriterReader.csvSeparator,
				sellingStatus.get(0).getCurrentPrice().get(0).get__value__(),
				HistoryWriterReader.csvSeparator, itemId.get(0),
				HistoryWriterReader.csvSeparator, title.get(0));
	}

	public String toLog(String csvSeparator, KeyValuePair userMsg) {
		return DateTime.now().toString() + " " + csvSeparator + " "
				+ toString() + csvSeparator + userMsg + "\n";
	}
}
