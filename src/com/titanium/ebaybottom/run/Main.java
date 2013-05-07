package com.titanium.ebaybottom.run;

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
import org.apache.commons.lang3.StringUtils;
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
import com.titanium.ebaybottom.run.model.EbaySearchResult;
import com.titanium.ebaybottom.run.model.FindItemsAdvancedResponse;
import com.titanium.ebaybottom.run.model.Group;
import com.titanium.ebaybottom.run.model.Item;
import com.titanium.ebaybottom.run.model.Pair;
import com.titanium.ebaybottom.run.model.SearchResult;
import com.titanium.ebaybottom.run.util.ApiKey;
import com.titanium.ebaybottom.run.util.Config;
import com.titanium.ebaybottom.run.util.TextIO;
import com.titanium.ebaybottom.run.util.UI;
import com.titanium.ebaybottom.run.util.Util;
import com.titanium.ebaybottom.setup.Setup;

public class Main {

	public static final String CONFIG_FILE = "config.ini";
	public static final String HISTORY_FILE = "app_data/history.txt";
	public static final String GROUP_FILE = "app_data/groups.txt";
	public static final String DISPLAY_SEPARATOR = "|";

	private static final int APP_VERSION = 10;
	public static final boolean isDebug = false;

	// superdealsyysi#Stupid123456
	public static void main(String[] args) {

		try {
			// load config file
			if (!Config.load(new File(".").getCanonicalPath() + "\\"
					+ CONFIG_FILE)) {
				UI.printError("loading values from config file failed @ "
						+ CONFIG_FILE);
				return;
			} else {
				UI.printUI("config load success:\n");
				Config.print();
			}

			UI.printUI("Version: " + APP_VERSION);

			if (args.length != 0) {
				Setup.run();
				return;
			}

			while (true) {

				// load saved groups
				List<Group> groups = GroupsMode.load();
				if (groups == null || groups.size() == 0) {
					UI.printError("no groups exist in " + GROUP_FILE);
					UI.getUserInput("exit?");
					return;
				}

				UI.printListWithIndexNumbers(groups);
				Group group = groups.get(isDebug ? 0 : UI.getUserInputInt());

				if (group.userAccount == null)
					group.userAccount = Setup.selectUserAccount();

				// 4. Load, filter & sort
				List<Item> filteredSortedItems = loadFilterSort(group);

				// 5. Print and choose items
				// print header
				UI.printResultHeader();
				UI.printListWithIndexNumbers(filteredSortedItems);
				List<Integer> selectedItemsRowNumbers = UI
						.getUserInputMultiSelect();

				// 6. Select & send messages
				UI.selectItemsAndMessages(filteredSortedItems,
						selectedItemsRowNumbers);
				SendPrivateMessage.sendMessagesInQueue(group.userAccount);

				// 7. Confirm and write to history
				if (UI.getUserInput("Was message sending success (y/n) ? ")
						.trim().toLowerCase().equals("y")) {
					// write history
					History.write(SendPrivateMessage.items,
							SendPrivateMessage.messages);
					UI.printUI("logged to history");
				} else {
					UI.printUI("history cleared");
				}

				// 9. cleanup
				UI.printUI("Clearing messages from memory for new cycle");
				SendPrivateMessage.items.clear();
				SendPrivateMessage.messages.clear();

				UI.printUI("Cycle done: Re-start");

			}
		} catch (Exception e) {
			UI.printError(e.toString());
			e.printStackTrace();
			UI.getUserInput("exit?");
		}

		UI.getUserInput("exit?");
	}

	private static List<Item> loadFilterSort(Group group) {
		List<Item> returnedItems = Network.loadFromEbay(group);

		UI.printUI("filtering items");

		List<Item> filteredItems = ResultController
				.removeInvalid(returnedItems);

		UI.printUI("sorting items");
		List<Item> sortedItems = ResultController
				.sortByEndDateAsc(filteredItems);

		return sortedItems;
	}
}