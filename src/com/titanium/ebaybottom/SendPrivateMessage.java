package com.titanium.ebaybottom;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
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
	private static final String CAPTCHA_ID = "tokenText";
	private static final String PRIVATEMESSAGE_TITLE_ID = "qusetOther_cnt_cnt";
	private static final String PRIVATEMESSAGE_BODY_ID = "msg_cnt_cnt";
	public static List<Item> items = new ArrayList<>();
	public static List<KeyValuePair> messages = new ArrayList<>();
	private static WebDriver driver;

	public static void addToMessageQue(Item selectedItem, KeyValuePair msg) {
		UI.printDebug("MSG QUEUE ADD:" + selectedItem.toString() + " " + msg);
		items.add(selectedItem);
		messages.add(msg);
	}

	public static void sendMessagesInQueue(KeyValuePair selectedUserAccount) {

		UI.printUI("Starting private message sending setup...");

		if (driver == null)
			driver = Network.logIn(selectedUserAccount);

		for (int i = 0; i < items.size(); i++) {

			Item selectedItem = items.get(i);
			KeyValuePair selectedMessage = messages.get(i);
			if (!driver.getCurrentUrl().contains(
					"contact.ebay.com/ws/eBayISAPI.dll?")) {
				driver.get(composeMessageUrl(selectedItem));
			} else {
				new WebWindow(driver, composeMessageUrl(selectedItem));
			}

			try {
				Thread.sleep(Config.windowOpenTimeout * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// select other
			// <input type="radio" style="margin-top:0px;margin-top:-2px\9;"
			// name="cat" value="5" id="Other">
			WebElement radio = findElementById(driver, "Other");
			if (radio != null && !radio.isSelected())
				radio.click();

			// continue button
			// <input type="submit" value="Continue" class="btn btn-m btn-prim"
			// style="width:109px;height:33px">
			findElementById(driver, "continueBtnDiv").submit();

			// title
			// <input id="qusetOther_cnt_cnt" class="gryFnt" type="text"
			// name="qusetOther_cnt_cnt" size="50" maxlength="32" value=""
			// style="display: inline;">
			// if (findElementById(driver, "qusetOther_cnt_cnt") != null)
			// findElementById(driver, "qusetOther_cnt_cnt").sendKeys(
			// selectedMessage.getKey());

			if (findElementById(driver, PRIVATEMESSAGE_TITLE_ID) != null) {
				setInstantKeys(driver, PRIVATEMESSAGE_TITLE_ID,
						selectedMessage.getKey());
				findElementById(driver, PRIVATEMESSAGE_TITLE_ID).click();
			}

			// message
			// <textarea id="msg_cnt_cnt" class="gryFnt"
			// defval="Enter your question here..." name="msg_cnt_cnt" cols="70"
			// rows="5" style="display: inline;"></textarea>
			// if (findElementById(driver, "msg_cnt_cnt") != null)
			// findElementById(driver, "msg_cnt_cnt").sendKeys(
			// selectedMessage.getValue());
			if (findElementById(driver, PRIVATEMESSAGE_BODY_ID) != null) {
				setInstantKeys(driver, PRIVATEMESSAGE_BODY_ID,
						selectedMessage.getValue());
				findElementById(driver, PRIVATEMESSAGE_BODY_ID).click();
			}

			// captcha highlight
			if (findElementById(driver, CAPTCHA_ID) != null) {
				findElementById(driver, CAPTCHA_ID).click();

				// onresume website highlight captcha HACK
				JavascriptExecutor js2 = (JavascriptExecutor) driver;
				String onFocusFocus = "var s = document.createElement('script'); s.type = 'text/javascript'; "
						+ "var code = 'window.onfocus = function () {document.getElementById(\"tokenText\").focus();}';"
						+ "try {s.appendChild(document.createTextNode(code)); document.body.appendChild(s); } catch (e) {s.text = code; document.body.appendChild(s);}";
				js2.executeScript(onFocusFocus);
			}
		}

		SessionCache.Write(selectedUserAccount, driver.manage().getCookies());
		UI.printUI("Message sending setup finished");
	}

	public static void setInstantKeys(WebDriver driver, String id,
			String selectedMessage) {
		try {
			JavascriptExecutor js2 = (JavascriptExecutor) driver;
			js2.executeScript(String.format(
					"document.getElementById(\"%s\").value = \"%s\"", id,
					StringEscapeUtils.escapeJava(selectedMessage)));
		} catch (Exception e) {
			UI.printDebug(e);
		}

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

	private static WebElement findElementById(WebDriver driver, String id) {
		try {
			return driver.findElement(By.id(id));
		} catch (NoSuchElementException e) {
			UI.printDebug("element not found with ID:" + id);
			return null;
		}
	}
}
