package com.titanium.ebaybottom.controller;

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

import com.titanium.ebaybottom.Main;
import com.titanium.ebaybottom.model.Pair;
import com.titanium.ebaybottom.ui.UI;

public class Config {

	public static final CharSequence PERSONAL_MESSAGE_ADDON = "***PERSONAL_MESSAGE_ADDON***";

	// BotMain
	public static int resultCount;
	public static int feedbackLimit;
	public static String locale;
	public static String personalMessageAddon;

	// csvlists
	public static List<Pair<String, String>> users;
	public static List<Pair<String, String>> messagesToUsers;
	public static List<String> blackListedUsers;

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
			resultCount = main.get("RESULT_COUNT", Integer.class);
			feedbackLimit = main.get("FEEDBACK_LIMIT_FILTER", Integer.class);
			locale = main.get("LOCALE");
			personalMessageAddon = main.get("PERSONAL_MESSAGE_ADDON");

			Section lists = ini.get("CSVLists");
			users = parseKeyValueList(lists.get("USERS"));
			messagesToUsers = parseKeyValueList(lists.get("MESSAGESTOUSERS"));
			blackListedUsers = Arrays.asList(lists.get("BLACKLISTEDUSERS")
					.split(","));

			isDebugLogging = ini.get("Dev").get("DEBUG", Boolean.class);
		} catch (Exception e) {
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
		UI.printList("messagesToUser", messagesToUsers);
		UI.printList("blackListedUsers", blackListedUsers);
	}

	public static Pair<String, String> selectUserAccount() {
		UI.printListWithIndexNumbers(Config.users);

		UI.printUI("Select user account. Enter -1 to leave this field empty");
		int selectedIndex = Main.isDebug ? 0 : UI.getUserInputInt();

		if (selectedIndex == -1)
			return null;

		Pair<String, String> selectedUserAccount = Config.users
				.get(selectedIndex);

		return selectedUserAccount;
	}
}