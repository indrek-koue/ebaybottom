package com.titanium.ebaybottom;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.gson.Gson;
import com.titanium.ebaybottom.model.EbaySearchResult;
import com.titanium.ebaybottom.model.FindItemsAdvancedResponse;
import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.swebdriver.WebWindow;
import com.titanium.ebaybottom.util.ApiKey;
import com.titanium.ebaybottom.util.Config;
import com.titanium.ebaybottom.util.UI;

public class Network {
	public static List<Item> loadItemsFromEbay(String keyword,
			List<Integer> categories) {

		String returnJson = "";
		try {
			HttpResponse response = new DefaultHttpClient()
					.execute(new HttpGet(buildUri(keyword, categories)));

			String json = EntityUtils.toString(response.getEntity());

			// have to replace @ signs because eBay returns faulty JSON
			returnJson = json.replace("\"@", "");

			UI.printDebug("@ chars removed count:"
					+ (json.length() - returnJson.length()));

		} catch (Exception e) {
			e.printStackTrace();
			UI.printError(e.toString());
		}

		return ResultController.parseItems(returnJson);
	}

	private static String buildUri(String keyword, List<Integer> categories)
			throws URISyntaxException {
		URIBuilder uBuilder = new URIBuilder(
				"http://svcs.ebay.com/services/search/FindingService/v1");

		// compulsory
		uBuilder.addParameter("OPERATION-NAME", "findItemsAdvanced");
		uBuilder.addParameter("SECURITY-APPNAME", ApiKey.KEY);
		uBuilder.addParameter("RESPONSE-DATA-FORMAT", "JSON");

		// ### OPTIONAL
		uBuilder.addParameter("keywords", keyword);
		uBuilder.addParameter("GLOBAL-ID", Config.locale);
		uBuilder.addParameter("outputSelector", "SellerInfo");
		uBuilder.addParameter("paginationInput.entriesPerPage",
				String.valueOf(Config.resultCount));
		uBuilder.addParameter("SERVICE-VERSION", "1.12.0");

		//v4 - sort by date
		uBuilder.addParameter("sortOrder", "EndTimeSoonest");
		
		// Specifies the category from which you want to retrieve item listings.
		// This field can be repeated to include multiple categories. Up to
		// three (3) categories can be specified.
		// http://developer.researchadvanced.com/tools/categoryBrowser.php
		for (int i = 0; i <= categories.size() && i < 3; i++)
			uBuilder.addParameter("categoryId", categories.get(i).toString());

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
		UI.printDebug("REQUEST URL:" + uri);
		return uri;
	}
}