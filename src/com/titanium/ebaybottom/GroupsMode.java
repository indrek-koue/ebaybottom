package com.titanium.ebaybottom;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.titanium.ebaybottom.model.Group;
import com.titanium.ebaybottom.model.KeyValuePair;
import com.titanium.ebaybottom.util.Config;
import com.titanium.ebaybottom.util.UI;
import com.titanium.ebaybottom.util.Util;

public class GroupsMode {

	public static void save(Group g) {

		try {
			UI.printDebug("write to groups mode: " + g);

			FileUtils.writeStringToFile(new File(Main.GROUP_FILE), g.toString()
					+ "\n", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<Group> load() {

		List<Group> result = new ArrayList<>();
		try {

			File f = new File(Main.GROUP_FILE);
			if (!f.exists()) {
				f.createNewFile();
				return null;
			}

			List<String> rows = FileUtils.readLines(f);

			for (String row : rows)
				result.add(new Group(row));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static void askForSave(Group g) {

		String userResponse = UI
				.getUserInput(
						"Save to groups mode for later use (y/x/n)? "
								+ "y = yes; x=yes with user; n = no")
				.toLowerCase().trim();

		if (userResponse.equals("y")) {
			// remove user account
			g.userAccount = null;
			GroupsMode.save(g);
			UI.printUI("Group saved without user!");
		} else if (userResponse.equals("x")) {
			GroupsMode.save(g);
			UI.printUI("Group saved with user!");
		} else {
			UI.printUI("Group not saved");
		}
	}
}
