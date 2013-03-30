package core;

import java.util.ArrayList;
import java.util.List;

import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.model.KeyValuePair;
import com.titanium.ebaybottom.util.Config;
import com.titanium.ebaybottom.util.TextIO;
import com.titanium.ebaybottom.util.Util;

public class Controller {
	public static List<Item> items;
	public static List<KeyValuePair> messages;

	public static void selectUserMessagesToSendUI(List<Item> returnedItems) {
		items = new ArrayList<>();
		messages = new ArrayList<>();

		String itemNumbers = Util
				.getUserInput("Enter sequence numbers of items you wish message to separated with ,  Example: 1,5,6,7,21");

		String[] itemNums = itemNumbers.split(",");

		Util.printUI("You want to send message to " + itemNums.length
				+ " items\n");

		for (String string : itemNums) {
			Item selectedItem = returnedItems.get(Integer.parseInt(string));
			Util.printUI("Select message for item: "
					+ selectedItem.getTitle().get(0));

			if (Config.messagesToUsers.size() == 0) {
				Util.printError("you have to enter messages to users in config.ini file");
				return;
			}

			Util.printListWithIndexNumbers(Config.messagesToUsers);

			KeyValuePair msg = Config.messagesToUsers.get(Util
					.getUserInputInt(Util.LINE_NUMBER_TO_SELECT));

			addToMessageQue(selectedItem, msg);

		}
	}

	private static void addToMessageQue(Item selectedItem, KeyValuePair msg) {

		System.out.println("QUEUE:" + selectedItem.toString() + " " + msg);

		items.add(selectedItem);
		messages.add(msg);
	}

	public static void sendMessagesInQueue(KeyValuePair selectedUserAccount) {
		System.out.println("open selenium webdirver here with firefox");
		// open selenium browser api with session and go to item send msg pages
	}
}
