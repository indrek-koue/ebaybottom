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
import com.titanium.ebaybottom.util.Util;

import core.Controller;
import core.HistoryWriterReader;
import core.Network;
import core.Parse;

public class Main {

	public static final String CONFIG_FILE = "config.ini";
	public static final String HISTORY_FILE = "history.txt";

	public static void main(String[] args) throws IOException {

		// http://docs.seleniumhq.org/docs/03_webdriver.jsp

		// driver.get("http://www.example.com");
		//
		// // Now set the cookie. This one's valid for the entire domain
		// Cookie cookie = new Cookie("key", "value");
		// driver.manage().addCookie(cookie);
		//
		// // And now output all the available cookies for the current URL
		// Set<Cookie> allCookies = driver.manage().getCookies();
		// for (Cookie loadedCookie : allCookies) {
		// System.out.println(String.format("%s -> %s", loadedCookie.getName(),
		// loadedCookie.getValue()));
		// }
		//
		// // You can delete cookies in 3 ways
		// // By name
		// driver.manage().deleteCookieNamed("CookieName");
		// // By Cookie
		// driver.manage().deleteCookie(loadedCookie);
		// // Or all of them
		// driver.manage().deleteAllCookies();

		// load config file
		if (!Config.load(new File(".").getCanonicalPath() + "\\" + CONFIG_FILE)) {
			Util.printError("loading values from config file failed @ "
					+ CONFIG_FILE);
			return;
		} else {
			Util.printUI("config load success:\n");
			Config.print();
		}

		// 1. Kasutajakonto
		Util.printListWithIndexNumbers(Config.users);
		KeyValuePair selectedUserAccount = Config.users.get(Util
				.getUserInputInt(Util.LINE_NUMBER_TO_SELECT));

		// 2. OTSINGUSÕNA
		Util.printListWithIndexNumbers(Config.keywords);
		String selectedKeyword = Config.keywords.get(Util
				.getUserInputInt(Util.LINE_NUMBER_TO_SELECT));

		// 3. KATEGOORIA GRUPP
		Util.printListWithIndexNumbers(Config.categories);
		List<Integer> selectedCategoryGroup = Config.categories.get(Util
				.getUserInputInt(Util.LINE_NUMBER_TO_SELECT));

		// load items
		List<Item> returnedItems = Network.loadAndParse(selectedKeyword,
				selectedCategoryGroup);
		List<Item> invalidRemoved = Parse.removeInvalid(returnedItems);
		Util.printListWithIndexNumbers(invalidRemoved);

		// 4. TOOTED KELLE SAATA SÕNUMID // 5. sõnumite valimine

		Controller.selectUserMessagesToSendUI(invalidRemoved);

		// 6. Sõnumite saatmine
		Controller.sendMessagesInQueue(selectedUserAccount);

		// 7. Kinnitus & mark done in history
		if (Util.getUserInput("Was message sending success (yes/no) ? ").trim()
				.toLowerCase().equals("yes")) {
			// write history
			HistoryWriterReader.write(Controller.items, Controller.messages);
			Util.printUI("logged to history");
		} else {
			Util.printUI("history cleared");
		}
		Util.printUI("DONE!");
	}
}