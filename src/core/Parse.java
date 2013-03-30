package core;

import java.util.ArrayList;
import java.util.List;

import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.util.Config;

public class Parse {

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

		return HistoryWriterReader.loadItemIds().contains(id);
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

}
