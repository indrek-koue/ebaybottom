package com.titanium.ebaybottom;

import java.util.ArrayList;
import java.util.List;

import org.ini4j.jdk14.edu.emory.mathcs.backport.java.util.Arrays;

import com.google.gson.Gson;
import com.titanium.ebaybottom.model.EbaySearchResult;
import com.titanium.ebaybottom.model.FindItemsAdvancedResponse;
import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.util.Config;
import com.titanium.ebaybottom.util.UI;
import com.titanium.ebaybottom.util.Util;

public class ResultController {

	public static List<Item> removeInvalid(List<Item> items) {

		List<Item> filteredItems = new ArrayList<>();

		History.loadMessageSentItemIds();
		for (Item item : items) {
			// verify if needs removing
			boolean isOverFeedbackLimit = isSellerOverPositiveFeedbackLimit(item);
			boolean isInBlacklist = isUserBlackListed(item);
			boolean isMsgSent = isMessageSent(item);

			if (isOverFeedbackLimit)
				UI.printDebug("FILTER: feedback limit | " + item);
			else if (isInBlacklist)
				UI.printDebug("FILTER: blacklisted | " + item);
			else if (isMsgSent)
				UI.printDebug("FILTER: message sent | " + item);

			// add indexes of items to remove
			if (!isOverFeedbackLimit && !isMsgSent && !isInBlacklist)
				filteredItems.add(item);
		}

		return filteredItems;
	}

	private static boolean isMessageSent(Item item) {
		String id = item.getItemId().get(0);
		return History.messagesSentIds.contains(Util.cleanString(id));
	}

	private static boolean isSellerOverPositiveFeedbackLimit(Item item) {
		int feedbackCount = Integer.parseInt(item.getSellerInfo().get(0)
				.getFeedbackScore().get(0));

		boolean isOverFeedbackCountLimit = (feedbackCount >= Config.feedbackLimit);
		// UI.printDebug(item.getSellerInfo().get(0).getSellerUserName().get(0)
		// + " count:" + feedbackCount + " removed:"
		// + isOverFeedbackCountLimit);

		return isOverFeedbackCountLimit;
	}

	private static boolean isUserBlackListed(Item item) {
		String sellerUsername = Util.cleanString(item.getSellerInfo().get(0)
				.getSellerUserName().get(0));

		for (int j = 0; j < Config.blackListedUsers.size(); j++) {
			String sellerBlacklisted = Util.cleanString(Config.blackListedUsers
					.get(j));

			if (sellerUsername.equals(sellerBlacklisted)) {
				// UI.printDebug("BLACKLISTED: " + sellerBlacklisted);
				return true;
			}
		}
		return false;
	}

	static List<Item> parseItems(String jsonValidVariableNames) {

		List<Item> result = new ArrayList<Item>();
		EbaySearchResult resultSet = new Gson().fromJson(
				jsonValidVariableNames, EbaySearchResult.class);

		if (resultSet.getErrorMessage() != null) {
			// we have a error
			UI.printError(resultSet.getErrorMessage().get(0).getError().get(0));
			return result;
		}

		if (resultSet.getFindItemsAdvancedResponse().get(0).getErrorMessage() != null) {
			// we have a error
			UI.printError(resultSet.getFindItemsAdvancedResponse().get(0)
					.getErrorMessage().get(0).getError().get(0));
			return result;
		}

		// response header
		FindItemsAdvancedResponse advancedResponse = resultSet
				.getFindItemsAdvancedResponse().get(0);

		if (advancedResponse.getSearchResult().get(0).getItem() == null) {
			UI.printError("0 items returned");
			return result;
		}

		UI.printUI(advancedResponse);

		result = advancedResponse.getSearchResult().get(0).getItem();

		return result;
	}

}
