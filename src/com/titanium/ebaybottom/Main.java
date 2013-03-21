package com.titanium.ebaybottom;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import com.google.gson.Gson;
import com.titanium.ebaybottom.model.EbaySearchResult;
import com.titanium.ebaybottom.model.FindItemsAdvancedResponse;
import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.model.SearchResult;
import com.titanium.ebaybottom.model.UserMessage;
import com.titanium.ebaybottom.util.ApiKey;
import com.titanium.ebaybottom.util.Config;
import com.titanium.ebaybottom.util.TextIO;
import com.titanium.ebaybottom.util.Util;

public class Main {

	private static final String PATH = "\\config.ini";
	private static List<Item> returnedItems;

	public static void main(String[] args) throws IOException {

		// 1. verify config file param parse
		if (!Config.load(new File(".").getCanonicalPath() + "" + PATH)) {
			Util.printError("loading values from config file failed @ " + PATH);
			return;
		} else {
			Util.printUI("config load success:\n" + Config.string());
		}

		if (false)
			connectUserToApp();

		String searchKeyword = Util.getUserInput("Search Keyword:");
		returnedItems = parseItems(loadJson(searchKeyword));
		printItems(returnedItems);
		selectUserMessagesToSendUI();

		Util.printUI("DONE!");
	}

	private static void selectUserMessagesToSendUI() {
		String itemNumbers = Util
				.getUserInput("Enter sequence numbers of items you wish message to separated with ,  Example: 1,5,6,7,21");

		String[] itemNums = itemNumbers.split(",");

		Util.printUI("You want to send message to " + itemNums.length
				+ " items\n");

		for (String string : itemNums) {
			Item selectedItem = returnedItems.get(Integer.parseInt(string));
			Util.printUI("Select message for item: "
					+ selectedItem.getTitle().get(0));

			if (Config.messagesToUsers.size() == 0) {
				Util.printError("you have to enter messages to users in config.ini file");
				return;
			}

			for (int i = 0; i < Config.messagesToUsers.size(); i++) {
				Util.printUI(i + ". " + Config.messagesToUsers.get(i));

			}

			System.out
					.println("\nEnter sequence number of message you wish to send:\n");
			int messageIndex = TextIO.getInt();

			Util.printUI(Config.messagesToUsers.get(messageIndex)
					+ "\nPRODUCT:" + selectedItem.getTitle().get(0));

			TextIO.getln();
			Util.getUserInput("press ENTER key to confirm");

			if (sendMessageToUser(Config.messagesToUsers.get(messageIndex),
					selectedItem))
				Util.printUI("Message sent");
			else
				Util.printUI("Message sending failed");

		}
	}

	private static boolean sendMessageToUser(UserMessage userMessage,
			Item selectedItem) {
		// TODO Auto-generated method stub
		return false;
	}

	private static void connectUserToApp() {

		// 2. get user input msg: username, pass, search keyword
		Util.printUI("Connecting app EbayBotTom with your user account");
		String username = Util.getUserInput("eBay Username:");
		String password = Util.getUserInput("Password:");

		if (!authenticate(username, password)) {
			Util.printError("Authentication failed");
		} else {
			Util.printUI("Authentication success");
		}

	}

	private static void printItems(List<Item> returnedItems) {
		int counter = 0;

		for (Item item : returnedItems) {

			boolean isOverFeedbackCountLimit = Integer.parseInt(item
					.getSellerInfo().get(0).getFeedbackScore().get(0)) >= Config.feedbackLimit;

			String sellerUsername = item.getSellerInfo().get(0)
					.getSellerUserName().get(0);

			String isSellerInBlackList = "";
			for (int i = 0; i < Config.blackListedUsers.length; i++) {
				if (sellerUsername
						.trim()
						.toLowerCase()
						.equals(Config.blackListedUsers[i].trim().toLowerCase())) {

					isSellerInBlackList = Config.blackListedUsers[i];
				}
			}

			String msg = item.toString();

			if (isOverFeedbackCountLimit)
				msg = "item removed, seller feedback limit >="
						+ Config.feedbackLimit;
			else if (!isSellerInBlackList.equals(""))
				msg = "item removed, seller is black listed: "
						+ isSellerInBlackList;

			Util.printUI(counter++ + ". " + msg + "\n");
		}
	}

	private static List<Item> parseItems(String jsonValidVariableNames) {

		List<Item> result = new ArrayList<Item>();
		EbaySearchResult resultSet = new Gson().fromJson(
				jsonValidVariableNames, EbaySearchResult.class);

		if (resultSet.getErrorMessage() != null) {
			// we have a error
			Util.printError(resultSet.getErrorMessage().get(0).getError()
					.get(0));
			return result;
		}

		if (resultSet.getFindItemsAdvancedResponse().get(0).getErrorMessage() != null) {
			// we have a error
			Util.printError(resultSet.getFindItemsAdvancedResponse().get(0)
					.getErrorMessage().get(0).getError().get(0));
			return result;
		}

		// response header
		FindItemsAdvancedResponse advancedResponse = resultSet
				.getFindItemsAdvancedResponse().get(0);

		if (advancedResponse.getSearchResult().get(0).getItem() == null) {
			Util.printError("0 items returned");
			return result;
		}

		Util.printUI(advancedResponse);

		result = advancedResponse.getSearchResult().get(0).getItem();

		return result;
	}

	private static String loadJson(String keyword) {

		String returnJson = "";
		try {
			HttpResponse response = new DefaultHttpClient()
					.execute(new HttpGet(buildUri(keyword)));

			String json = EntityUtils.toString(response.getEntity());

			// have to replace @ signs because eBay returns faulty JSON
			returnJson = json.replace("\"@", "");

			Util.printDebug("@ chars removed count:"
					+ (json.length() - returnJson.length()));

		} catch (Exception e) {
			e.printStackTrace();
			Util.printError(e.toString());
		}

		return returnJson;
	}

	private static boolean authenticate(String username2, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	private static String buildUri(String keyword) throws URISyntaxException {
		URIBuilder uBuilder = new URIBuilder(
				"http://svcs.ebay.com/services/search/FindingService/v1");

		// compulsory
		uBuilder.addParameter("OPERATION-NAME", "findItemsAdvanced");
		uBuilder.addParameter("SECURITY-APPNAME", ApiKey.KEY);
		uBuilder.addParameter("RESPONSE-DATA-FORMAT", "JSON");

		// optional
		uBuilder.addParameter("keywords", keyword);
		uBuilder.addParameter("GLOBAL-ID", Config.locale);
		uBuilder.addParameter("outputSelector", "SellerInfo");
		uBuilder.addParameter("paginationInput.entriesPerPage",
				String.valueOf(Config.resultCount));
		uBuilder.addParameter("SERVICE-VERSION", "1.12.0");

		// Specifies the category from which you want to retrieve item listings.
		// This field can be repeated to include multiple categories. Up to
		// three (3) categories can be specified.
		// http://developer.researchadvanced.com/tools/categoryBrowser.php
		for (int i = 0; i <= Config.categories.length && i < 3; i++)
			uBuilder.addParameter("categoryId", Config.categories[i]);

		// custom filters, see
		// more:http://developer.ebay.com/devzone/finding/callref/types/ItemFilterType.html
		uBuilder.addParameter("itemFilter(0).name", "Condition");
		uBuilder.addParameter("itemFilter(0).value(0)", "1000");
		uBuilder.addParameter("itemFilter(0).value(1)", "1500");
		uBuilder.addParameter("itemFilter(0).value(2)", "2000");
		uBuilder.addParameter("itemFilter(0).value(3)", "2500");
		uBuilder.addParameter("itemFilter(0).value(4)", "3000");

		// defaults to USD.
		uBuilder.addParameter("itemFilter(1).name", "MaxPrice");
		uBuilder.addParameter("itemFilter(1).value",
				Integer.toString(Config.maxPrice));
		uBuilder.addParameter("itemFilter(2).name", "MinPrice");
		uBuilder.addParameter("itemFilter(2).value",
				Double.toString(Config.minPrice));

		// Limits the results to items ending on or after the specified time.
		// 24h is user request
		uBuilder.addParameter("itemFilter(3).name", "EndTimeFrom");
		uBuilder.addParameter("itemFilter(3).value",
				new DateTime().plusHours(24).toString());

		String uri = uBuilder.build().toString();
		Util.printDebug("REQUEST URL:" + uri);
		return uri;
	}
}