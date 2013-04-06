package com.titanium.ebaybottom;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.model.KeyValuePair;
import com.titanium.ebaybottom.swebdriver.WebWindow;
import com.titanium.ebaybottom.util.Config;
import com.titanium.ebaybottom.util.TextIO;
import com.titanium.ebaybottom.util.UI;

public class SendPrivateMessage {
	// private static final int TIMEOUT_BETWEEN_OPEN_PM_WINDOW = 10;
	public static List<Item> items = new ArrayList<>();
	public static List<KeyValuePair> messages = new ArrayList<>();

	public static void addToMessageQue(Item selectedItem, KeyValuePair msg) {
		UI.printDebug("MSG QUEUE ADD:" + selectedItem.toString() + " " + msg);
		items.add(selectedItem);
		messages.add(msg);
	}

	public static void sendMessagesInQueue(KeyValuePair selectedUserAccount) {
		UI.printUI("Starting private message sending setup...");

		WebDriver driver = new FirefoxDriver();
		driver.get("https://signin.ebay.com/ws/eBayISAPI.dll?SignIn");
		logIn(selectedUserAccount, driver);

		for (int i = 0; i < items.size(); i++) {

			Item selectedItem = items.get(i);
			KeyValuePair selectedMessage = messages.get(i);

			new WebWindow(driver, composeMessageUrl(selectedItem));
			// new WebDriverWait(driver, Config.windowOpenTimeout*1000);
			try {
				Thread.sleep(Config.windowOpenTimeout * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// select other
			// <input type="radio" style="margin-top:0px;margin-top:-2px\9;"
			// name="cat" value="5" id="Other">
			WebElement radio = findElementById(driver, "Other");
			if (!radio.isSelected())
				radio.click();

			// continue button
			// <input type="submit" value="Continue" class="btn btn-m btn-prim"
			// style="width:109px;height:33px">
			findElementById(driver, "continueBtnDiv").submit();

			// title
			// <input id="qusetOther_cnt_cnt" class="gryFnt" type="text"
			// name="qusetOther_cnt_cnt" size="50" maxlength="32" value=""
			// style="display: inline;">
			if (findElementById(driver, "qusetOther_cnt_cnt") != null)
				findElementById(driver, "qusetOther_cnt_cnt").sendKeys(
						selectedMessage.getKey());

			// message
			// <textarea id="msg_cnt_cnt" class="gryFnt"
			// defval="Enter your question here..." name="msg_cnt_cnt" cols="70"
			// rows="5" style="display: inline;"></textarea>
			if (findElementById(driver, "msg_cnt_cnt") != null)
				findElementById(driver, "msg_cnt_cnt").sendKeys(
						selectedMessage.getValue());
		}

		UI.printUI("Message sending setup finished");
	}

	private static String composeMessageUrl(Item i) {
		String s = String
				.format("http://contact.ebay.com/ws/eBayISAPI.dll?ShowSellerFAQ&iid=%s&"
						+ "requested=%s&redirect=0&ssPageName=PageSellerM2MFAQ_VI",
						i.getItemId().get(0), i.getSellerInfo().get(0)
								.getSellerUserName().get(0));
		UI.printDebug("MSG URL: " + s);
		return s;
	}

	private static void logIn(KeyValuePair selectedUserAccount, WebDriver driver) {

		Set<Cookie> cookies = SessionCache.Load(selectedUserAccount);

		// check if has already active session
		if (cookies != null) {
			UI.printUI("Session active, skip loggin: " + selectedUserAccount);

			for (Cookie cookie : cookies) {
				driver.manage().addCookie(cookie);
			}
			
			UI.printUI("session copied to browser");
			
			return;
		}

		UI.printUI("Log in with: " + selectedUserAccount);
		// username
		WebElement element = driver.findElement(By.id("userid"));
		element.sendKeys(selectedUserAccount.getKey());

		// password
		driver.findElement(By.id("pass")).sendKeys(
				selectedUserAccount.getValue());

		// remember me
		if (!driver.findElement(By.id("signed_in")).isSelected())
			driver.findElement(By.id("signed_in")).click();

		element.submit();

	}

	private static WebElement findElementById(WebDriver driver, String id) {
		try {
			return driver.findElement(By.id(id));
		} catch (NoSuchElementException e) {
			UI.printDebug("element not found with ID:" + id);
			return null;
		}
	}
}
