package com.titanium.ebaybottom.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.titanium.ebaybottom.Main;
import com.titanium.ebaybottom.controller.Config;
import com.titanium.ebaybottom.controller.PrivateMessage;
import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.model.Pair;
import com.titanium.ebaybottom.util.Util;

public class UI {
	public static final String LINE_NUMBER_TO_SELECT = "Line number to select: ";

	public static void printError(Object o) {
		printUI("ERROR:" + o);
	}

	public static void printDebug(Object o) {
		if (Config.isDebugLogging)
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

	public static int getUserInputInt() {
		System.out.print("\n++ " + LINE_NUMBER_TO_SELECT);
		return TextIO.getInt();
	}

	public static <T> void printListWithIndexNumbers(List<T> list) {
		System.out.println();
		for (int i = 0; i < list.size(); i++) {
			System.out.println(String.format("%2d. %s", i, list.get(i)));
			// System.out.println(i + ". " + list.get(i));
		}
	}

	public static <T> void printList(String key, List<T> list) {
		System.out.print(key + "=");
		for (int i = 0; i < list.size(); i++) {
			System.out.print(list.get(i) + "; ");
		}
		System.out.println();
	}

	public static void selectItemsAndMessages(List<Item> returnedItems,
			List<Integer> selectedItemsRowNumbers) {

		UI.printUI("You want to send message to "
				+ selectedItemsRowNumbers.size() + " items\n");

		if (Config.messagesToUsers.size() == 0) {
			UI.printError("you have to 0"
					+ "enter messages to users in config.ini file");
			return;
		}

		// v3 - the same message goes to all participants
		boolean isFirstRun = true;
		Pair msg = null;

		for (int i : selectedItemsRowNumbers) {
			Item selectedItem = returnedItems.get(i);

			if (isFirstRun) {
				UI.printUI("Select messages for items");

				// + selectedItem.getTitle().get(0));

				UI.printListWithIndexNumbers(Config.messagesToUsers);

				msg = Config.messagesToUsers.get(Main.isDebug ? 0 : UI
						.getUserInputInt());
				isFirstRun = false;
			}
			PrivateMessage.addToMessageQue(selectedItem, msg);
		}
	}

	public static List<Integer> getUserInputMultiSelect() {

		List<Integer> selectedItems = new ArrayList<>();

		String itemNumbersRaw = Main.isDebug ? "0"
				: UI.getUserInput("Enter sequence of numbers of items you wish to select "
						+ "separated with , or with -  Example: 1,5,6,7,21 or 1-21");

		if (itemNumbersRaw.contains("-")) {// we have a range

			String[] nums = itemNumbersRaw.split("-");

			int start = Integer.parseInt(nums[0]);
			int end = Integer.parseInt(nums[1]);

			for (int i = start; i <= end; i++)
				selectedItems.add(i);

		} else {
			List<String> tempList = Arrays.asList(itemNumbersRaw.split(","));

			// convert String list to Integer list
			for (String s : tempList)
				selectedItems.add(Integer.parseInt(s));

		}

		return selectedItems;
	}

	public static void printResultHeader() {
		System.out.println();
		System.out.println(StringUtils.join(
				new String[] { Util.padLeftAndCutIfNeeded("USERNAME", 14),
						Util.padLeftAndCutIfNeeded("PRICE", 6),
						Util.padLeftAndCutIfNeeded("POS.", 4),
						Util.padLeftAndCutIfNeeded("ITEM ID", 13),
						Util.padLeftAndCutIfNeeded("TIME LEFT", 13),
						Util.padLeftAndCutIfNeeded("TITLE", 20) },
				Main.DISPLAY_SEPARATOR));
	}

}
