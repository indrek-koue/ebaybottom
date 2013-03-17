package com.titanium.ebaybottom;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.titanium.ebaybottom.model.EbaySearchResult;
import com.titanium.ebaybottom.model.SearchResult;
import com.titanium.ebaybottom.util.ApiKey;

public class Main {

	private static final String RESULT_COUNT = "2";
	private static final String LOCALE = "EBAY-US";
	private static final String SEARCH_KEYWORD = "ipod";
	private static final boolean DEBUG = true;

	static List<Integer> Categories = new ArrayList<>();

	public static void main(String[] args) {
		try {
			HttpResponse response = new DefaultHttpClient()
					.execute(new HttpGet(buildUri()));

			String json = EntityUtils.toString(response.getEntity());
			String jsonValidVariableNames = json.replace("\"@", "");
			// printDebug(jsonValidVariableNames);
			int charsRemoved = json.length() - jsonValidVariableNames.length();
			printDebug("@ chars removed count:" + charsRemoved);

			EbaySearchResult r = new Gson().fromJson(jsonValidVariableNames,
					EbaySearchResult.class);
			printDebug(r.getFindItemsAdvancedResponse().get(0)
					.getSearchResult().get(0).getItem().get(0).getSellerInfo()
					.get(0).getFeedbackScore());

		} catch (Exception e) {
			printError(e.toString());
		}
	}

	private static void printError(String error) {
		printUI("ERROR:" + error);
	}

	private static String buildUri() throws URISyntaxException {

		// http://svcs.ebay.com/services/search/FindingService/v1?
		// OPERATION-NAME=findItemsAdvanced&
		// SERVICE-VERSION=1.0.0&
		// SECURITY-APPNAME=YourAppID&
		// RESPONSE-DATA-FORMAT=XML&
		// REST-PAYLOAD&
		// outputSelector=AspectHistogram&
		// itemFilter(0).name=Condition&
		// itemFilter(0).value=Used&
		// itemFilter(1).name=ListingType&
		// itemFilter(1).value=AuctionWithBIN&
		// paginationInput.entriesPerPage=2&
		// categoryId=31388

		URIBuilder uBuilder = new URIBuilder(
				"http://svcs.ebay.com/services/search/FindingService/v1");

		// compulsory
		uBuilder.setParameter("OPERATION-NAME", "findItemsAdvanced");
		uBuilder.setParameter("SECURITY-APPNAME", ApiKey.KEY);
		uBuilder.setParameter("RESPONSE-DATA-FORMAT", "JSON");

		// optional
		uBuilder.setParameter("keywords", SEARCH_KEYWORD);
		uBuilder.setParameter("GLOBAL-ID", LOCALE);
		uBuilder.setParameter("outputSelector", "SellerInfo");
		uBuilder.setParameter("paginationInput.entriesPerPage", RESULT_COUNT);
		uBuilder.setParameter("SERVICE-VERSION", "1.12.0");

		// Specifies the category from which you want to retrieve item listings.
		// This field can be repeated to include multiple categories. Up to
		// three (3) categories can be specified.
		//
		// If a specified category ID doesn't match an existing category for the
		// site, // eBay returns an invalid-category error message. To determine
		// valid categories, use the Shopping API GetCategoryInfo call.

		// http://developer.researchadvanced.com/tools/categoryBrowser.php

		// Consumer Electronics;
		Categories.add(293);
		// Vehicle Electronics & GPS;
		Categories.add(3270);
		// GPS Units
		Categories.add(156955);

		for (int categoryId : Categories) {
			uBuilder.setParameter("categoryId", categoryId + "");
		}

		// 1.1.4 Condition: New
		// 1.1.5 New other (see details);
		// 1.1.6 Manufacturer refurbished;
		// 1.1.7 Seller refurbished;
		// 1.1.8 Used;

		// outputSelector=AspectHistogram&
		// itemFilter(0).name=Condition&
		// itemFilter(0).value=Used&
		// itemFilter(1).name=ListingType&
		// itemFilter(1).value=AuctionWithBIN&
		
		//http://developer.ebay.com/devzone/finding/callref/types/ItemFilterType.html
		
//		1000
//		New
//		1500
//		New other (see details)
//		1750
//		New with defects
//		2000
//		Manufacturer refurbished
//		2500
//		Seller refurbished
//		3000
//		Used
//		4000
//		Very Good
//		5000
//		Good
//		6000
//		Acceptable
//		7000
//		For parts or not working
		
		uBuilder.setParameter("itemFilter(0).name", "Condition");
		uBuilder.setParameter("itemFilter(0).value(0)", "1000");
		uBuilder.setParameter("itemFilter(0).value(1)", "1500");
		uBuilder.setParameter("itemFilter(0).value(2)", "2000");
		uBuilder.setParameter("itemFilter(0).value(3)", "2500");
		uBuilder.setParameter("itemFilter(0).value(4)", "3000");
		// price
		// ...&itemFilter.name=FreeShippingOnly&itemFilter.value=true...
		// Some filters take additional parameters. For example, when
		// using the MaxPrice or MinPrice filters, you can specify the
		// currency value with itemFilter.paramName and itemFilter.paramValue.
		// When you use MaxPrice and MinPrice item filters, itemFilter.paramName
		// defaults to Currency and itemFilter.paramValue defaults to USD.
		uBuilder.setParameter("itemFilter(3).name", "Condition");
		uBuilder.setParameter("itemFilter(3).value", "...");
		uBuilder.setParameter("itemFilter(3).name", "Condition");
		uBuilder.setParameter("itemFilter(3).value", "...");

		
		String uri = uBuilder.build().toString();
		printDebug("REQUEST URL:" + uri);
		return uri;
	}

	private static void printDebug(Object o) {
		if (DEBUG)
			printUI("DEBUG:" + o.toString());
	}

	private static void printUI(Object o) {
		System.out.println("::" + o + "\n");
	}

}
