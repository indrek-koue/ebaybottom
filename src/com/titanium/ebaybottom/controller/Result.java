package com.titanium.ebaybottom.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ini4j.jdk14.edu.emory.mathcs.backport.java.util.Arrays;

import com.google.gson.Gson;
import com.titanium.ebaybottom.model.EbaySearchResult;
import com.titanium.ebaybottom.model.FindItemsAdvancedResponse;
import com.titanium.ebaybottom.model.Group;
import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.ui.UI;
import com.titanium.ebaybottom.util.Util;

public class Result {

	public static List<Item> removeInvalid(List<Item> items) {

		List<Item> filteredItems = new ArrayList<>();

		// load which item message has been sent already
		History.loadMessageSentItemIds();

		for (Item item : items) {
			// verify if needs removing
			if (isSellerOverPositiveFeedbackLimit(item))
				UI.printDebug("FILTER: feedback limit | " + item);
			else if (isUserBlackListed(item))
				UI.printDebug("FILTER: blacklisted | " + item);
			else if (isMessageSent(item))
				UI.printDebug("FILTER: message sent | " + item);
			else if (isDublicate(item, filteredItems))
				UI.printDebug("FILTER: dublicate ID | " + item);
			else
				filteredItems.add(item);
		}

		return filteredItems;
	}

	private static boolean isDublicate(Item item, List<Item> filteredItems) {

		for (Item filteredItem : filteredItems) {
			if (filteredItem.getItemId().get(0).equals(item.getItemId().get(0)))
				return true;
		}

		return false;
	}

	private static boolean isMessageSent(Item item) {
		String id = item.getItemId().get(0);
		return History.messagesSentIds.contains(Util.cleanString(id));
	}

	private static boolean isSellerOverPositiveFeedbackLimit(Item item) {
		int feedbackCount = Integer.parseInt(item.getSellerInfo().get(0)
				.getFeedbackScore().get(0));

		boolean isOverFeedbackCountLimit = (feedbackCount >= Config.feedbackLimit);

		return isOverFeedbackCountLimit;
	}

	private static boolean isUserBlackListed(Item item) {
		String sellerUsername = Util.cleanString(item.getSellerInfo().get(0)
				.getSellerUserName().get(0));

		for (int j = 0; j < Config.blackListedUsers.size(); j++) {
			String sellerBlacklisted = Util.cleanString(Config.blackListedUsers
					.get(j));

			if (sellerUsername.equals(sellerBlacklisted)) {
				return true;
			}
		}
		return false;
	}

	public static List<Item> sortByEndDateAsc(List<Item> filteredItems) {
		Collections.sort(filteredItems);
		return filteredItems;
	}
	
	/**
	 * loads, filters and sorts items
	 */
	public static List<Item> loadFilterSort(Group group) {
		List<Item> returnedItems = Network.loadFromEbay(group);

		UI.printUI("filtering items");

		List<Item> filteredItems = Result
				.removeInvalid(returnedItems);

		UI.printUI("sorting items");
		List<Item> sortedItems = Result
				.sortByEndDateAsc(filteredItems);

		return sortedItems;
	}

}
