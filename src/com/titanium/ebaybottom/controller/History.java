package com.titanium.ebaybottom.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.ini4j.jdk14.edu.emory.mathcs.backport.java.util.Arrays;

import com.titanium.ebaybottom.Main;
import com.titanium.ebaybottom.model.Item;
import com.titanium.ebaybottom.model.Pair;
import com.titanium.ebaybottom.ui.UI;
import com.titanium.ebaybottom.util.Util;

public class History {
	private static final int INDEX_OF_ITEM_ID = 4;
	public static String csvSeparator = "|";
	public static ArrayList<String> messagesSentIds;

	public static void write(List<Item> items, List<Pair<String,String>> messages) {

		for (int i = 0; i < items.size(); i++) {
			try {
				String toWrite = items.get(i).toLog(csvSeparator,
						messages.get(i));

				FileUtils.writeStringToFile(new File(Main.HISTORY_FILE),
						toWrite, true);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void loadMessageSentItemIds() {
		messagesSentIds = new ArrayList<String>();

		try {

			File f = new File(Main.HISTORY_FILE);
			if (!f.exists()) {
				f.createNewFile();
				return;
			}

			List<String> log = FileUtils.readLines(f);

			for (String s : log) {
				String itemId = s.split("\\" + csvSeparator)[INDEX_OF_ITEM_ID];
				messagesSentIds.add(Util.cleanString(itemId));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(Arrays.toString(History.messagesSentIds.toArray()));

	}

}
