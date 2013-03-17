
package com.titanium.ebaybottom.model;

import java.util.List;

public class Item{
   	private List<String> autoPay;
   	private List<Condition> condition;
   	private List<String> country;
   	private List<String> galleryURL;
   	private List<String> globalId;
   	private List<String> isMultiVariationListing;
   	private List<String> itemId;
   	private List<ListingInfo> listingInfo;
   	private List<String> location;
   	private List<String> paymentMethod;
   	private List<String> postalCode;
   	private List<PrimaryCategory> primaryCategory;
   	private List<ProductId> productId;
   	private List<String> returnsAccepted;
   	private List<SellingStatus> sellingStatus;
   	private List<ShippingInfo> shippingInfo;
   	private List<String> title;
   	private List<String> topRatedListing;
   	private List<String> viewItemURL;
   	private List<SellerInfo> sellerInfo;
   	

 	public List<String> getAutoPay(){
		return this.autoPay;
	}
	public void setAutoPay(List<String> autoPay){
		this.autoPay = autoPay;
	}
 	public List<Condition> getCondition(){
		return this.condition;
	}
	public void setCondition(List<Condition> condition){
		this.condition = condition;
	}
 	public List<String> getCountry(){
		return this.country;
	}
	public void setCountry(List<String> country){
		this.country = country;
	}
 	public List<String> getGalleryURL(){
		return this.galleryURL;
	}
	public void setGalleryURL(List<String> galleryURL){
		this.galleryURL = galleryURL;
	}
 	public List<String> getGlobalId(){
		return this.globalId;
	}
	public void setGlobalId(List<String> globalId){
		this.globalId = globalId;
	}
 	public List<String> getIsMultiVariationListing(){
		return this.isMultiVariationListing;
	}
	public void setIsMultiVariationListing(List<String> isMultiVariationListing){
		this.isMultiVariationListing = isMultiVariationListing;
	}
 	public List<String> getItemId(){
		return this.itemId;
	}
	public void setItemId(List<String> itemId){
		this.itemId = itemId;
	}
 	public List<ListingInfo> getListingInfo(){
		return this.listingInfo;
	}
	public void setListingInfo(List<ListingInfo> listingInfo){
		this.listingInfo = listingInfo;
	}
 	public List<String> getLocation(){
		return this.location;
	}
	public void setLocation(List<String> location){
		this.location = location;
	}
 	public List<String> getPaymentMethod(){
		return this.paymentMethod;
	}
	public void setPaymentMethod(List<String> paymentMethod){
		this.paymentMethod = paymentMethod;
	}
 	public List<String> getPostalCode(){
		return this.postalCode;
	}
	public void setPostalCode(List<String> postalCode){
		this.postalCode = postalCode;
	}
 	public List<PrimaryCategory> getPrimaryCategory(){
		return this.primaryCategory;
	}
	public void setPrimaryCategory(List<PrimaryCategory> primaryCategory){
		this.primaryCategory = primaryCategory;
	}
 	public List<ProductId> getProductId(){
		return this.productId;
	}
	public void setProductId(List<ProductId> productId){
		this.productId = productId;
	}
 	public List<String> getReturnsAccepted(){
		return this.returnsAccepted;
	}
	public void setReturnsAccepted(List<String> returnsAccepted){
		this.returnsAccepted = returnsAccepted;
	}
 	public List<SellingStatus> getSellingStatus(){
		return this.sellingStatus;
	}
	public void setSellingStatus(List<SellingStatus> sellingStatus){
		this.sellingStatus = sellingStatus;
	}
 	public List<ShippingInfo> getShippingInfo(){
		return this.shippingInfo;
	}
	public void setShippingInfo(List<ShippingInfo> shippingInfo){
		this.shippingInfo = shippingInfo;
	}
 	public List<String> getTitle(){
		return this.title;
	}
	public void setTitle(List<String> title){
		this.title = title;
	}
 	public List<String> getTopRatedListing(){
		return this.topRatedListing;
	}
	public void setTopRatedListing(List<String> topRatedListing){
		this.topRatedListing = topRatedListing;
	}
 	public List<String> getViewItemURL(){
		return this.viewItemURL;
	}
	public void setViewItemURL(List<String> viewItemURL){
		this.viewItemURL = viewItemURL;
	}
	@Override
	public String toString() {
		return String.format("Item [productId=%s, title=%s, viewItemURL=%s]",
				productId, title, viewItemURL);
	}
	public List<SellerInfo> getSellerInfo() {
		return sellerInfo;
	}
	public void setSellerInfo(List<SellerInfo> sellerInfo) {
		this.sellerInfo = sellerInfo;
	}
}
