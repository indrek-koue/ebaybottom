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

public class Main {

	// private static final String SEARCH_KEYWORD = "ipod";
	// private static final String API_CALL = "FindItems";
	// private static final String API_VER = "517";
	// private static final String EBAY_API_PATH =
	// "http://open.api.ebay.com/shopping?";
	// private static final String API_KEY =
	// "TomThoma-61de-4a88-81bc-38753a959533";

	private static final boolean DEBUG = true;

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
		URIBuilder uBuilder = new URIBuilder(
				"http://svcs.ebay.com/services/search/FindingService/v1");

		// compulsory
		uBuilder.setParameter("OPERATION-NAME", "findItemsAdvanced");
		uBuilder.setParameter("SECURITY-APPNAME",
				"TomThoma-61de-4a88-81bc-38753a959533");
		uBuilder.setParameter("RESPONSE-DATA-FORMAT", "JSON");

		// optional
		uBuilder.setParameter("keywords", "ipod");
		uBuilder.setParameter("GLOBAL-ID", "EBAY-US");
		uBuilder.setParameter("outputSelector", "SellerInfo");
		uBuilder.setParameter("paginationInput.entriesPerPage", "2");
		
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
