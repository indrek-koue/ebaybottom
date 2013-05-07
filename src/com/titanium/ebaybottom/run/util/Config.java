package com.titanium.ebaybottom.run.util;

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

import com.titanium.ebaybottom.run.model.Pair;

public class Config {

	public static final CharSequence PERSONAL_MESSAGE_ADDON = "***PERSONAL_MESSAGE_ADDON***";

	// BotMain
	public static int resultCount;
	public static int feedbackLimit;
	public static String locale;
	public static String personalMessageAddon;

	// csvlists
	public static List<Pair<String, String>> users;
	public static List<String> keywords;
	public static List<List<Integer>> categories;
	public static List<Pair<String, String>> messagesToUsers;
	public static List<String> blackListedUsers;
	public static List<Pair<Integer, Integer>> prices;

	// dev
	public static boolean isDebugLogging;

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
			prices = parseKeyValueList(main.get("PRICES"));
			resultCount = main.get("RESULT_COUNT", Integer.class);
			feedbackLimit = main.get("FEEDBACK_LIMIT_FILTER", Integer.class);
			locale = main.get("LOCALE");
			personalMessageAddon = main.get("PERSONAL_MESSAGE_ADDON");

			Section lists = ini.get("CSVLists");
			users = parseKeyValueList(lists.get("USERS"));
			keywords = Arrays.asList(lists.get("KEYWORDS").split(","));
			categories = parseGroups(lists.get("CATEGORIES"));
			messagesToUsers = parseKeyValueList(lists.get("MESSAGESTOUSERS"));
			blackListedUsers = Arrays.asList(lists.get("BLACKLISTEDUSERS")
					.split(","));

			isDebugLogging = ini.get("Dev").get("DEBUG", Boolean.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			UI.printError(e);
			return false;
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

	private static <K, V> List<Pair<K, V>> parseKeyValueList(String input) {

		List<Pair<K, V>> result = new ArrayList<>();

		for (String msg : input.split(","))
			result.add(new Pair(msg.split("#")[0], msg.split("#")[1]));

		return result;
	}

	public static void print() {
		UI.printList("users", users);
		UI.printList("keywords", keywords);
		UI.printList("categories", categories);
		UI.printList("messagesToUser", messagesToUsers);
		UI.printList("blackListedUsers", blackListedUsers);
		UI.printList("prices", prices);
	}

}
