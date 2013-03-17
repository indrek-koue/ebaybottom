
package com.titanium.ebaybottom.model;

import java.util.List;

public class SellerInfo{
   	private List<String> feedbackRatingStar;
   	private List<String> feedbackScore;
   	private List<String> positiveFeedbackPercent;
   	private List<String> sellerUserName;
   	private List<String> topRatedSeller;

   	
   	
 	public List<String> getFeedbackRatingStar(){
		return this.feedbackRatingStar;
	}
	public void setFeedbackRatingStar(List<String> feedbackRatingStar){
		this.feedbackRatingStar = feedbackRatingStar;
	}
 	public List<String> getFeedbackScore(){
		return this.feedbackScore;
	}
	public void setFeedbackScore(List<String> feedbackScore){
		this.feedbackScore = feedbackScore;
	}
 	public List<String> getPositiveFeedbackPercent(){
		return this.positiveFeedbackPercent;
	}
	public void setPositiveFeedbackPercent(List<String> positiveFeedbackPercent){
		this.positiveFeedbackPercent = positiveFeedbackPercent;
	}
 	public List<String> getSellerUserName(){
		return this.sellerUserName;
	}
	public void setSellerUserName(List<String> sellerUserName){
		this.sellerUserName = sellerUserName;
	}
 	public List<String> getTopRatedSeller(){
		return this.topRatedSeller;
	}
	public void setTopRatedSeller(List<String> topRatedSeller){
		this.topRatedSeller = topRatedSeller;
	}
	@Override
	public String toString() {
		return String
				.format("sellerUserName=%s, feedbackScore=%s, positiveFeedbackPercent=%s, ",
						sellerUserName, feedbackScore, positiveFeedbackPercent);
	}
}
