package com.titanium.ebaybottom.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.ini4j.Ini;
import org.ini4j.InvalidFileFormatException;
import org.ini4j.Profile.Section;

import com.titanium.ebaybottom.model.KeyValuePair;

public class Config {

	// BotMain
	public static int minPrice;
	public static int maxPrice;
	public static int resultCount;
	public static int feedbackLimit;
	public static String locale;
	public static int windowOpenTimeout;

	// csvlists
	public static List<KeyValuePair> users;
	public static List<String> keywords;
	public static List<List<Integer>> categories;
	public static List<KeyValuePair> messagesToUsers;
	public static List<String> blackListedUsers;

	// dev
	public static boolean isDebug;

	public static boolean load(String path) {
		try {
			UI.printUI("Load config.ini from:" + path);

			File f = new File(path);

			if (!f.exists()) {
				UI.printError(".ini config file not found:" + path);
				f.createNewFile();
				return false;
			}

			Ini ini = new Ini(f);

			Section main = ini.get("BotMain");
			minPrice = main.get("MIN_PRICE", Integer.class);
			maxPrice = main.get("MAX_PRICE", Integer.class);
			resultCount = main.get("RESULT_COUNT", Integer.class);
			feedbackLimit = main.get("FEEDBACK_LIMIT_FILTER", Integer.class);
			locale = main.get("LOCALE");
			windowOpenTimeout = Integer.parseInt(main
					.get("TIMEOUT_BETWEEN_OPEN_PM_WINDOW"));

			Section lists = ini.get("CSVLists");
			users = parseKeyValueList(lists.get("USERS"));
			keywords = Arrays.asList(lists.get("KEYWORDS").split(","));

			categories = parseGroups(lists.get("CATEGORIES"));

			messagesToUsers = parseKeyValueList(lists.get("MESSAGESTOUSERS"));
			blackListedUsers = Arrays.asList(lists.get("BLACKLISTEDUSERS")
					.split(","));

			isDebug = ini.get("Dev").get("DEBUG", Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UI.printError(e);
		}

		return true;
	}

	private static List<List<Integer>> parseGroups(String input) {

		List<List<Integer>> cs = new ArrayList<List<Integer>>();

		for (String rawGroup : input.split("#")) {

			String[] rawMembers = rawGroup.split(",");

			ArrayList<Integer> group = new ArrayList<Integer>();

			for (int i = 0; i < rawMembers.length; i++)
				group.add(Integer.parseInt(rawMembers[i]));

			cs.add(group);
		}

		return cs;
	}

	private static List<KeyValuePair> parseKeyValueList(String input) {

		List<KeyValuePair> result = new ArrayList<KeyValuePair>();

		for (String msg : input.split(","))
			result.add(new KeyValuePair(msg.split("#")[0], msg.split("#")[1]));

		return result;
	}

	public static void print() {
		System.out
				.println(String
						.format("minPrice=%s\nmaxPrice=%s\nresultCount=%s\nfeedbackLimit=%s\nlocale=%s\nwindowOpenTimeoutSec=%s",
								minPrice, maxPrice, resultCount, feedbackLimit,
								locale, windowOpenTimeout));

		UI.printList("users", users);
		UI.printList("keywords", keywords);
		UI.printList("categories", categories);
		UI.printList("messagesToUser", messagesToUsers);
		UI.printList("blackListedUsers", blackListedUsers);
	}

}
