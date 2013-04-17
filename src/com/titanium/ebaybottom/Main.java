package com.titanium.ebaybottom;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
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
import org.ini4j.jdk14.edu.emory.mathcs.backport.java.util.Arrays;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.openqa.selenium.WebDriver;

import com.google.gson.Gson;
import com.titanium.ebaybottom.model.EbaySearchResult;
import com.titanium.ebaybottom.model.FindItemsAdvancedResponse;
import com.titanium.ebaybottom.model.Group;
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
	public static final String GROUP_FILE = "groups.txt";
	private static final int APP_VERSION = 7;

	public static final boolean isDebug = true;

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

		UI.printUI("Version: " + APP_VERSION);

		while (true) {

			Group selectedGroup = null;

			if (Config.appMode == Config.MODE_GROUPS) {

				// load saved groups
				List<Group> groups = GroupsMode.load();
				UI.printListWithIndexNumbers(groups);
				selectedGroup = groups.get(UI
						.getUserInputInt(UI.LINE_NUMBER_TO_SELECT));
			} else {
				// 1. User account selection
				UI.printListWithIndexNumbers(Config.users);
				KeyValuePair selectedUserAccount = Config.users.get(isDebug ? 0
						: UI.getUserInputInt(UI.LINE_NUMBER_TO_SELECT));

				// 2. Search keyword selection
				UI.printListWithIndexNumbers(Config.keywords);
				String selectedKeyword = Config.keywords.get(isDebug ? 0 : UI
						.getUserInputInt(UI.LINE_NUMBER_TO_SELECT));

				// 3. Category group selection
				UI.printListWithIndexNumbers(Config.categories);
				List<Integer> selectedCategoryGroup = Config.categories
						.get(isDebug ? 0 : UI
								.getUserInputInt(UI.LINE_NUMBER_TO_SELECT));

				selectedGroup = new Group(selectedUserAccount, selectedKeyword,
						selectedCategoryGroup, Config.minPrice, Config.maxPrice);
			}

			// 4. Load & filter
			List<Item> returnedItems = Network.loadFromEbay(
					selectedGroup.selectedKeyword,
					selectedGroup.selectedCategoryGroup);
			List<Item> filteredItems = ResultController
					.removeInvalid(returnedItems);

			// 5. Print and choose items
			UI.printListWithIndexNumbers(filteredItems);
			List<String> selectedItemsRowNumbers = UI.getUserInputAndParse();

			// 6. Select & send messages
			UI.selectItemsAndMessages(filteredItems, selectedItemsRowNumbers);
			SendPrivateMessage
					.sendMessagesInQueue(selectedGroup.selectedUserAccount);

			// 7. Confirm and write to history
			if (UI.getUserInput("Was message sending success (y/n) ? ").trim()
					.toLowerCase().equals("y")) {
				// write history
				History.write(SendPrivateMessage.items,
						SendPrivateMessage.messages);
				UI.printUI("logged to history");
			} else {
				UI.printUI("history cleared");
			}

			// 8. save search group
			if (Config.appMode == Config.MODE_NORMAL)
				GroupsMode.askForSave(selectedGroup);

			// 9. cleanup
			UI.printUI("Clearing messages from memory for new cycle");
			SendPrivateMessage.items.clear();
			SendPrivateMessage.messages.clear();

			UI.printUI("Cycle done: Re-start");
		}
	}
}