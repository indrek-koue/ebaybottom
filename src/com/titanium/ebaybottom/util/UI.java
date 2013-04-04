package com.titanium.ebaybottom.util;

import java.util.ArrayList;
import java.util.List;

import com.titanium.ebaybottom.Main;
import com.titanium.ebaybottom.SendPrivateMessage;
import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.model.KeyValuePair;

public class UI {
	public static final String LINE_NUMBER_TO_SELECT = "Line number to select: ";

	public static void printError(Object o) {
		printUI("ERROR:" + o);
	}

	public static void printDebug(Object o) {
		if (Config.isDebug)
			printUI("DEBUG:" + o.toString());
	}

	public static void printUI(Object o) {
		System.out.println("::" + o);
	}

	public static String getUserInput(String header) {
		System.out.print("\n++ " + header + "\n");
		String input = TextIO.getln();

		while (input.equals(""))
			input = TextIO.getln();

		return input;
	}

	public static int getUserInputInt(String header) {
		System.out.print("\n++ " + header);
		return TextIO.getInt();
	}

	public static <T> void printListWithIndexNumbers(List<T> list) {
		System.out.println();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i + ". " + list.get(i));
		}
	}

	public static <T> void printList(String key, List<T> list) {
		System.out.print(key + "=");
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + "; ");
		}
		System.out.println();
	}

	public static void selectUserPrivateMessages(List<Item> returnedItems) {

		String itemNumbers = UI
				.getUserInput("Enter sequence numbers of items you wish message to separated with ,  Example: 1,5,6,7,21");

		String[] itemNums = itemNumbers.split(",");

		UI.printUI("You want to send message to " + itemNums.length
				+ " items\n");

		if (Config.messagesToUsers.size() == 0) {
			UI.printError("you have to enter messages to users in config.ini file");
			return;
		}

		// v3
		boolean isFirstRun = true;
		KeyValuePair msg = null;

		for (String string : itemNums) {
			Item selectedItem = returnedItems.get(Integer.parseInt(string));

			if (isFirstRun) {
				UI.printUI("Select message for item: "
						+ selectedItem.getTitle().get(0));

				UI.printListWithIndexNumbers(Config.messagesToUsers);

				msg = Config.messagesToUsers.get(UI
						.getUserInputInt(UI.LINE_NUMBER_TO_SELECT));
				isFirstRun = false;
			}
			SendPrivateMessage.addToMessageQue(selectedItem, msg);
		}

		// for (String string : itemNums) {
		// Item selectedItem = returnedItems.get(Integer.parseInt(string));
		// UI.printUI("Select message for item: "
		// + selectedItem.getTitle().get(0));
		//
		// UI.printListWithIndexNumbers(Config.messagesToUsers);
		//
		// KeyValuePair msg = Config.messagesToUsers.get(UI
		// .getUserInputInt(UI.LINE_NUMBER_TO_SELECT));
		//
		// SendPrivateMessage.addToMessageQue(selectedItem, msg);
		//
		// }
	}
}
