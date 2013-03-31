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
//	private static Set<Cookie> cookies;

	// 281085320872
	// http://cgi.ebay.com/ws/eBayISAPI.dll?ViewItem&item=281085320872
	// http://contact.ebay.com/ws/eBayISAPI.dll?ShowSellerFAQ&iid=281085320872
	// &requested=inventoryexpressonline&redirect=0&frm=284&rt=nc&_trksid=p2047675.l1499
	// &ssPageName=PageSellerM2MFAQ_VI
//	static boolean authenticate(String username2, String password) {
//		getSession(username2, password);
		// WebDriver driver2 = new FirefoxDriver();
		// driver2.get("http://www.ebay.com/");
		// for (Cookie cookie : cookies) {
		// driver2.manage().addCookie(cookie);
		// System.out.println(cookie);
		// }
		// driver2.navigate().to("http://contact.ebay.com/ws/eBayISAPI.dll?FindAnswers&iid=281085320872&requested=inventoryexpressonline");
		//
		// driver2.findElement(By.id("Other")).click();

		// new WebDriverWait(driver2, 3000);
		// driver2.navigate().to("https://signin.ebay.com/ws/eBayISAPI.dll?SignIn");
		// driver2.get("https://signin.ebay.com/ws/eBayISAPI.dll?SignIn");
		// contact
		// http://contact.ebay.com/ws/eBayISAPI.dll?FindAnswers&iid=281085320872&requested=inventoryexpressonline
		// http://contact.ebay.com/ws/eBayISAPI.dll?FindAnswers&iid={itemid}&requested={userid}

		// <input id="qusetOther_cnt_cnt" class="gryFnt" type="text"
		// name="qusetOther_cnt_cnt" size="50" maxlength="32" value=""
		// style="display: inline;">

		// <textarea id="msg_cnt_cnt" class="gryFnt"
		// defval="Enter your question here..." name="msg_cnt_cnt" cols="70"
		// rows="5" style="display: inline;"></textarea>

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
		//
		// TODO Auto-generated method stub
	// return true;
	// }

//	private static void getSession(String username2, String password) {
//		WebDriver driver = new FirefoxDriver();
//		driver.get("https://signin.ebay.com/ws/eBayISAPI.dll?SignIn");
//
//		// username
//		WebElement element = driver.findElement(By.id("userid"));
//		element.sendKeys(username2);
//
//		// password
//		driver.findElement(By.id("pass")).sendKeys(password);
//
//		// remember me
//		if (!driver.findElement(By.id("signed_in")).isSelected())
//			driver.findElement(By.id("signed_in")).click();
//
//		element.submit();
//
//		cookies = driver.manage().getCookies();
//
//		System.out.println("cookies aquired:" + cookies.size());
//		// driver.close();
//
//		new WebWindow(
//				driver,
//				"http://contact.ebay.com/ws/eBayISAPI.dll?ShowSellerFAQ&iid=281085320872&requested=inventoryexpressonline&redirect=0&ssPageName=PageSellerM2MFAQ_VI");
//
//		// <input type="radio" style="margin-top:0px;margin-top:-2px\9;"
//		// name="cat" value="5" id="Other">
//
//		WebElement radio = findElementById(driver, "Other");
//		if (radio.isSelected())
//			radio.click();
//
//		// <input type="submit" value="Continue" class="btn btn-m btn-prim"
//		// style="width:109px;height:33px">
////		radio.submit();
//		findElementById(driver, "continueBtnDiv").submit();
//		
//		// <input id="qusetOther_cnt_cnt" class="gryFnt" type="text"
//		// name="qusetOther_cnt_cnt" size="50" maxlength="32" value=""
//		// style="display: inline;">
//
////		findElementById(driver, "qusetOther_cnt_cnt").sendKeys("title");
//
//		// <textarea id="msg_cnt_cnt" class="gryFnt"
//		// defval="Enter your question here..." name="msg_cnt_cnt" cols="70"
//		// rows="5" style="display: inline;"></textarea>
//
//		findElementById(driver, "msg_cnt_cnt").sendKeys("subject");
//
//		// new WebWindow(
//		// driver,
//		// "http://contact.ebay.com/ws/eBayISAPI.dll?FindAnswers&iid=281085320872&requested=inventoryexpressonline");
//		// new WebWindow(
//		// driver,
//		// "http://contact.ebay.com/ws/eBayISAPI.dll?FindAnswers&iid=281085320872&requested=inventoryexpressonline");
//
//	}
//
//	private static WebElement findElementById(WebDriver driver, String id) {
//		return driver.findElement(By.id(id));
//	}

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