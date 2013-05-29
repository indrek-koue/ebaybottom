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
import com.titanium.ebaybottom.controller.Config;
import com.titanium.ebaybottom.controller.GroupsMode;
import com.titanium.ebaybottom.controller.History;
import com.titanium.ebaybottom.controller.Network;
import com.titanium.ebaybottom.controller.Result;
import com.titanium.ebaybottom.controller.PrivateMessage;
import com.titanium.ebaybottom.model.EbaySearchResult;
import com.titanium.ebaybottom.model.FindItemsAdvancedResponse;
import com.titanium.ebaybottom.model.Group;
import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.model.Pair;
import com.titanium.ebaybottom.model.SearchResult;
import com.titanium.ebaybottom.ui.TextIO;
import com.titanium.ebaybottom.ui.UI;
import com.titanium.ebaybottom.util.ApiKey;
import com.titanium.ebaybottom.util.Util;

public class Main {

	public static final String CONFIG_FILE = "config.ini";
	public static final String HISTORY_FILE = "app_data/history.txt";
	public static final String GROUP_FILE = "app_data/groups.txt";
	public static final String DISPLAY_SEPARATOR = "|";

	private static final int APP_VERSION = 12;
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

			while (true) {

				// 1. Load saved groups
				List<Group> groups = GroupsMode.load();
				if (groups == null || groups.size() == 0) {
					UI.printError("no groups exist in " + GROUP_FILE);
					UI.getUserInput("exit?");
					return;
				}

				UI.printListWithIndexNumbers(groups);
				Group group = groups.get(isDebug ? 0 : UI.getUserInputInt());

				if (group.userAccount == null)
					group.userAccount = Config.selectUserAccount();

				// 2. Load, filter & sort
				List<Item> filteredSortedItems = Result.loadFilterSort(group);

				// 3. Print and choose items
				UI.printResultHeader();
				UI.printListWithIndexNumbers(filteredSortedItems);
				List<Integer> selectedItemsRowNumbers = UI
						.getUserInputMultiSelect();

				// 4. Select & send messages
				UI.selectItemsAndMessages(filteredSortedItems,
						selectedItemsRowNumbers);
				PrivateMessage.sendMessagesInQueue(group.userAccount);

				// 5. Confirm and write to history
				if (UI.getUserInput("Was message sending success (y/n) ? ")
						.trim().toLowerCase().equals("y")) {
					// write history
					History.write(PrivateMessage.items,
							PrivateMessage.messages);
					UI.printUI("logged to history");
				} else {
					UI.printUI("history cleared");
				}

				// 6. cleanup
				UI.printUI("Clearing messages from memory for new cycle");
				PrivateMessage.items.clear();
				PrivateMessage.messages.clear();

				UI.printUI("Cycle done: Re-start");

			}
		} catch (Exception e) {
			UI.printError(e.toString());
			e.printStackTrace();
			UI.getUserInput("exit?");
		}

		UI.getUserInput("exit?");
	}
}