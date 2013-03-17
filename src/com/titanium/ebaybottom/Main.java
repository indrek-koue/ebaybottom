package com.titanium.ebaybottom;

import java.io.Console;
import java.io.File;
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
import com.titanium.ebaybottom.model.FindItemsAdvancedResponse;
import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.model.SearchResult;
import com.titanium.ebaybottom.util.ApiKey;
import com.titanium.ebaybottom.util.ConfigWorker;
import com.titanium.ebaybottom.util.TextIO;
import com.titanium.ebaybottom.util.Util;

public class Main {

	private static final String PATH = "\\config.ini";
	private static String searchKeyword = "tomtom";
	private static List<Item> returnedItems;
	private static ConfigWorker config;

	public static void main(String[] args) throws IOException {

		config = new ConfigWorker();

		// 1. verify config file param parse
		if (!config.load(new File(".").getCanonicalPath() + "" + PATH)) {
			Util.printError("loading values from config file failed @ " + PATH);
			return;
		} else {
			Util.printUI("config load success:\n" + config.toString());
		}

		if (!config.isDebug) {
			// 2. get user input msg: username, pass, search keyword
			Util.printUI("AUDENTIMINE (Eelnevalt peab olema EbayBotTom rakendus lubatud eBay kasutajakonto alt)");
			String username = Util.getUserInput("eBay Username:");
			String password = Util.getUserInput("Password:");

			if (!authenticate(username, password)) {
				Util.printError("Audentimine ebaġnnestus");
				return;
			} else {
				Util.printUI("audentimine edukas");
			}
		}

		while (true) {
			searchKeyword = Util.getUserInput("Search Keyword:");
			try {
				HttpResponse response = new DefaultHttpClient()
						.execute(new HttpGet(buildUri()));

				String json = EntityUtils.toString(response.getEntity());
				String jsonValidVariableNames = json.replace("\"@", "");

				Util.printDebug(json.length(), config.isDebug);
				Util.printDebug(jsonValidVariableNames.length(), config.isDebug);
				Util.printDebug("@ chars removed count:"
						+ (json.length() - jsonValidVariableNames.length()),
						config.isDebug);

				EbaySearchResult result = new Gson().fromJson(
						jsonValidVariableNames, EbaySearchResult.class);

				if (result.getErrorMessage() != null) {
					// we have a error
					Util.printError(result.getErrorMessage().get(0).getError()
							.get(0));
					return;
				}

				if (result.getFindItemsAdvancedResponse().get(0)
						.getErrorMessage() != null) {
					// we have a error
					Util.printError(result.getFindItemsAdvancedResponse()
							.get(0).getErrorMessage().get(0).getError().get(0));
					return;
				}

				// response header
				FindItemsAdvancedResponse advancedResponse = result
						.getFindItemsAdvancedResponse().get(0);
				if (advancedResponse.getSearchResult().get(0).getItem() == null) {
					Util.printError("0 items returned");
					return;
				}
				Util.printUI(advancedResponse);
				returnedItems = advancedResponse.getSearchResult().get(0)
						.getItem();
				int counter = 0;

				for (Item item : returnedItems) {

					boolean isOverFeedbackCountLimit = Integer.parseInt(item
							.getSellerInfo().get(0).getFeedbackScore().get(0)) >= config.feedbackLimit;

					String sellerUsername = item.getSellerInfo().get(0)
							.getSellerUserName().get(0);

					String isSellerInBlackList = "";
					for (int i = 0; i < config.blackListedUsers.length; i++) {
						if (sellerUsername
								.trim()
								.toLowerCase()
								.equals(config.blackListedUsers[i].trim()
										.toLowerCase())) {

							isSellerInBlackList = config.blackListedUsers[i];
						}
					}

					String msg = item.toString();

					if (isOverFeedbackCountLimit)
						msg = "item removed, seller feedback limit >="
								+ config.feedbackLimit;
					else if (!isSellerInBlackList.equals(""))
						msg = "item removed, seller is black listed: "
								+ isSellerInBlackList;

					Util.printUI(counter++ + ". " + msg);
				}
				// Util.printUI("DONE!");
			} catch (Exception e) {
				e.printStackTrace();
				Util.printError(e);
			}
		}

	}

	private static boolean authenticate(String username2, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	private static String buildUri() throws URISyntaxException {
		URIBuilder uBuilder = new URIBuilder(
				"http://svcs.ebay.com/services/search/FindingService/v1");

		// compulsory
		uBuilder.addParameter("OPERATION-NAME", "findItemsAdvanced");
		uBuilder.addParameter("SECURITY-APPNAME", ApiKey.KEY);
		uBuilder.addParameter("RESPONSE-DATA-FORMAT", "JSON");

		// optional
		uBuilder.addParameter("keywords", searchKeyword);
		uBuilder.addParameter("GLOBAL-ID", config.locale);
		uBuilder.addParameter("outputSelector", "SellerInfo");
		uBuilder.addParameter("paginationInput.entriesPerPage",
				String.valueOf(config.resultCount));
		uBuilder.addParameter("SERVICE-VERSION", "1.12.0");

		composeCategoryParams(uBuilder);
		composeConditionParams(uBuilder);
		composePriceParams(uBuilder);

		String uri = uBuilder.build().toString();
		Util.printDebug("REQUEST URL:" + uri, config.isDebug);
		return uri;
	}

	public static void composePriceParams(URIBuilder uBuilder) {
		// price
		// ...&itemFilter.name=FreeShippingOnly&itemFilter.value=true...
		// Some filters take additional parameters. For example, when
		// using the MaxPrice or MinPrice filters, you can specify the
		// currency value with itemFilter.paramName and itemFilter.paramValue.
		// When you use MaxPrice and MinPrice item filters, itemFilter.paramName
		// defaults to Currency and itemFilter.paramValue defaults to USD.
		uBuilder.addParameter("itemFilter(1).name", "MaxPrice");
		uBuilder.addParameter("itemFilter(1).value",
				Integer.toString(config.maxPrice));
		uBuilder.addParameter("itemFilter(2).name", "MinPrice");
		uBuilder.addParameter("itemFilter(2).value",
				Double.toString(config.minPrice));
	}

	public static void composeConditionParams(URIBuilder uBuilder) {
		// 1.1.4 Condition: New
		// 1.1.5 New other (see details);
		// 1.1.6 Manufacturer refurbished;
		// 1.1.7 Seller refurbished;
		// 1.1.8 Used;

		// http://developer.ebay.com/devzone/finding/callref/types/ItemFilterType.html
		// 1000
		// New
		// 1500
		// New other (see details)
		// 1750
		// New with defects
		// 2000
		// Manufacturer refurbished
		// 2500
		// Seller refurbished
		// 3000
		// Used
		// 4000
		// Very Good
		// 5000
		// Good
		// 6000
		// Acceptable
		// 7000
		// For parts or not working

		uBuilder.addParameter("itemFilter(0).name", "Condition");
		uBuilder.addParameter("itemFilter(0).value(0)", "1000");
		uBuilder.addParameter("itemFilter(0).value(1)", "1500");
		uBuilder.addParameter("itemFilter(0).value(2)", "2000");
		uBuilder.addParameter("itemFilter(0).value(3)", "2500");
		uBuilder.addParameter("itemFilter(0).value(4)", "3000");
	}

	public static void composeCategoryParams(URIBuilder uBuilder) {
		// Specifies the category from which you want to retrieve item listings.
		// This field can be repeated to include multiple categories. Up to
		// three (3) categories can be specified.
		//
		// If a specified category ID doesn't match an existing category for the
		// site, // eBay returns an invalid-category error message. To determine
		// valid categories, use the Shopping API GetCategoryInfo call.

		// http://developer.researchadvanced.com/tools/categoryBrowser.php

		// // Consumer Electronics;
		// config.categories.add(293);
		// // Vehicle Electronics & GPS;
		// config.categories.add(3270);
		// // GPS Units
		// config.categories.add(156955);

		for (int i = 0; i <= config.categories.length && i < 3; i++)
			uBuilder.addParameter("categoryId", config.categories[i]);

		// for (int categoryId : config.categories) {
		// uBuilder.addParameter("categoryId", categoryId + "");
		// }
	}
}