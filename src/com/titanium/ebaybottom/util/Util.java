package com.titanium.ebaybottom.util;

import java.util.List;

import com.titanium.ebaybottom.Main;
import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.model.KeyValuePair;

public class Util {
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
		return TextIO.getlnString();

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

	public static void printItems(List<Item> returnedItems) {
		for (int i = 0; i < returnedItems.size(); i++) {
			Util.printUI(i + ". " + returnedItems.get(i) + "\n");
		}
	}
}
