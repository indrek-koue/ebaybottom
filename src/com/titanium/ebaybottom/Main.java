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
import com.titanium.ebaybottom.model.SearchResult;

public class Main {

//	private static final String SEARCH_KEYWORD = "ipod";
//	private static final String API_CALL = "FindItems";
//	private static final String API_VER = "517";
//	private static final String EBAY_API_PATH = "http://open.api.ebay.com/shopping?";
//	private static final String API_KEY = "TomThoma-61de-4a88-81bc-38753a959533";

	private static final boolean DEBUG = true;

	public static void main(String[] args) {
		try {
			HttpResponse response = new DefaultHttpClient()
					.execute(new HttpGet(buildUri()));

			String json = EntityUtils.toString(response.getEntity());
			printDebug(json);

			SearchResult r = new Gson().fromJson(json, SearchResult.class);
			printDebug(r);

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
		String uri = uBuilder.build().toString();

		printUI(uri);

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
