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
import org.openqa.selenium.WebDriver;

import com.google.gson.Gson;
import com.titanium.ebaybottom.model.EbaySearchResult;
import com.titanium.ebaybottom.model.FindItemsAdvancedResponse;
import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.model.SearchResult;
import com.titanium.ebaybottom.model.KeyValuePair;
import com.titanium.ebaybottom.util.ApiKey;
import com.titanium.ebaybottom.util.Config;
import com.titanium.ebaybottom.util.TextIO;
import com.titanium.ebaybottom.util.UI;

public class Main {

	public static final String CONFIG_FILE = "config.ini";
	public static final String HISTORY_FILE = "history.txt";

	// superdealsyysi#Stupid123456
	public static void main(String[] args) throws IOException {

		// load config file
		if (!Config.load(new File(".").getCanonicalPath() + "\\" + CONFIG_FILE)) {
			UI.printError("loading values from config file failed @ "
					+ CONFIG_FILE);
			return;
		} else {
			UI.printUI("config load success:\n");
			Config.print();
		}

		// 1. User account selection
		UI.printListWithIndexNumbers(Config.users);
		KeyValuePair selectedUserAccount = Config.users.get(UI
				.getUserInputInt(UI.LINE_NUMBER_TO_SELECT));

		// 2. Search keyword selection
		UI.printListWithIndexNumbers(Config.keywords);
		String selectedKeyword = Config.keywords.get(UI
				.getUserInputInt(UI.LINE_NUMBER_TO_SELECT));

		// 3. Category group selection
		UI.printListWithIndexNumbers(Config.categories);
		List<Integer> selectedCategoryGroup = Config.categories.get(UI
				.getUserInputInt(UI.LINE_NUMBER_TO_SELECT));

		// 4. Load filtered items from ebay
		List<Item> returnedItems = Network.loadItemsFromEbay(selectedKeyword,
				selectedCategoryGroup);
		List<Item> invalidRemoved = ResultController
				.removeInvalid(returnedItems);
		UI.printListWithIndexNumbers(invalidRemoved);

		// 5. Select messages to send
		UI.selectUserPrivateMessages(invalidRemoved);

		// 6. Send messages
		SendPrivateMessage.sendMessagesInQueue(selectedUserAccount);

		// 7. Confirm and write to history
		if (UI.getUserInput("Was message sending success (yes/no) ? ").trim()
				.toLowerCase().equals("yes")) {
			// write history
			History.write(SendPrivateMessage.items, SendPrivateMessage.messages);
			UI.printUI("logged to history");
		} else {
			UI.printUI("history cleared");
		}

		UI.printUI("DONE!");
	}
}