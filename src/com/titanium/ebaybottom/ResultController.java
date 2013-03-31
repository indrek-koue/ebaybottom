package com.titanium.ebaybottom;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.titanium.ebaybottom.model.EbaySearchResult;
import com.titanium.ebaybottom.model.FindItemsAdvancedResponse;
import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.util.Config;
import com.titanium.ebaybottom.util.UI;

public class ResultController {

	public static List<Item> removeInvalid(List<Item> items) {

		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);

			// verify if needs removing
			boolean isOverFeedbackCountLimit = isSellerOverPositiveFeedbackLimit(item);
			String isSellerInBlackList = isUserBlackListed(item);
			boolean isMessageAlreadySent = hasBeenMessageSent(item);

			if (Config.isDebug && isMessageAlreadySent)
				System.out.println("msg sent by itemid");

			// remove
			if (isOverFeedbackCountLimit || isMessageAlreadySent
					&& !isSellerInBlackList.equals(""))
				items.remove(i);
		}

		return items;
	}

	private static boolean hasBeenMessageSent(Item item) {
		String id = item.getItemId().get(0);

		return History.loadMessageSentItemIds().contains(id);
	}

	private static boolean isSellerOverPositiveFeedbackLimit(Item item) {
		boolean isOverFeedbackCountLimit = Integer.parseInt(item
				.getSellerInfo().get(0).getFeedbackScore().get(0)) >= Config.feedbackLimit;
		return isOverFeedbackCountLimit;
	}

	private static String isUserBlackListed(Item item) {
		String sellerUsername = item.getSellerInfo().get(0).getSellerUserName()
				.get(0);

		String isSellerInBlackList = "";
		for (int j = 0; j < Config.blackListedUsers.size(); j++) {
			if (sellerUsername
					.trim()
					.toLowerCase()
					.equals(Config.blackListedUsers.get(j).trim().toLowerCase())) {

				return Config.blackListedUsers.get(j);
			}
		}
		return isSellerInBlackList;
	}

	static List<Item> parseItems(String jsonValidVariableNames) {

		List<Item> result = new ArrayList<Item>();
		EbaySearchResult resultSet = new Gson().fromJson(
				jsonValidVariableNames, EbaySearchResult.class);

		if (resultSet.getErrorMessage() != null) {
			// we have a error
			UI.printError(resultSet.getErrorMessage().get(0).getError()
					.get(0));
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
