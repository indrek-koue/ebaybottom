package com.titanium.ebaybottom.selenium;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.titanium.ebaybottom.ui.UI;

public class WebdriverHelper {

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
	
	public static WebElement findElementById(WebDriver driver, String id) {
		try {
			return driver.findElement(By.id(id));
		} catch (NoSuchElementException e) {
			UI.printDebug("element not found with ID:" + id);
			return null;
		}
	}
	
}
