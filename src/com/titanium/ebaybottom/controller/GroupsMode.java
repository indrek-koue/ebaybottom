package com.titanium.ebaybottom.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.titanium.ebaybottom.Main;
import com.titanium.ebaybottom.model.Group;
import com.titanium.ebaybottom.model.Pair;
import com.titanium.ebaybottom.ui.UI;
import com.titanium.ebaybottom.util.Util;

public class GroupsMode {

	public static void save(Group group) {
		try {
			FileUtils.writeStringToFile(new File(Main.GROUP_FILE),
					group.toString() + "\n", true);

			UI.printUI("group saved:" + group.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<Group> load() throws IOException {

		List<Group> result = new ArrayList<>();

			File f = new File(Main.GROUP_FILE);
			if (!f.exists()) {
				//f.createNewFile();
				return result;
			}

			List<String> rows = FileUtils.readLines(f);

			for (String row : rows)
				result.add(new Group(row));

		return result;
	}

}
