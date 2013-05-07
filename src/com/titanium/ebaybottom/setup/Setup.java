package com.titanium.ebaybottom.setup;

import java.util.ArrayList;
import java.util.List;

import com.titanium.ebaybottom.run.GroupsMode;
import com.titanium.ebaybottom.run.Main;
import com.titanium.ebaybottom.run.model.Group;
import com.titanium.ebaybottom.run.model.Pair;
import com.titanium.ebaybottom.run.util.Config;
import com.titanium.ebaybottom.run.util.UI;

public class Setup {

	public static void run() {

		while (true) {

			// 1. User account selection
			Pair<String, String> userAccount = selectUserAccount();

			// 2. Search keyword selection
			List<String> keywords = select(Config.keywords);

			// 3. Categories selection
			List<List<Integer>> categoryGroups = select(Config.categories);

			// 4. max-min price
			List<Pair<Integer, Integer>> prices = select(Config.prices);

			// 5. save
			GroupsMode.save(new Group(userAccount, keywords, categoryGroups,
					prices));
		}

	}

	private static <T> List<T> select(List<T> list) {

		UI.printListWithIndexNumbers(list);

		List<T> selectedItems = new ArrayList<>();

		for (int i : UI.getUserInputMultiSelect())
			selectedItems.add(list.get(i));

		return selectedItems;
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