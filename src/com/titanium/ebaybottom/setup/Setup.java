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

//			// 1. User account selection
//			Pair<String, String> userAccount = selectUserAccount();
//
//			// 2. Search keyword selection
//			List<String> keywords = select(Config.keywords);
//
//			// 3. Categories selection
//			List<List<Integer>> categoryGroups = select(Config.categories);
//
//			// 4. max-min price
//			List<Pair<Integer, Integer>> prices = select(Config.prices);
//
//			// 5. save
//			GroupsMode.save(new Group(userAccount, keywords, categoryGroups,
//					prices));
//
//			UI.printUI("Cycle done: Re-start");

			List<Group> load = GroupsMode.load();
			for (Group group : load) {
				UI.printDebug(group);
				
				
			}
		}

	}

	// private static List<Pair<Integer, Integer>> selectPrices() {
	//
	// UI.printListWithIndexNumbers(Config.prices);
	//
	// List<Pair<Integer, Integer>> selectedPrices = new ArrayList<>();
	//
	// for (int i : UI.getUserInputMultiSelect())
	// selectedPrices.add(Config.prices.get(i));
	//
	// return selectedPrices;
	// }
	//
	// private static List<List<Integer>> selectCategoryGroup() {
	// UI.printListWithIndexNumbers(Config.categories);
	//
	// List<List<Integer>> selectedCategoryGroup = new ArrayList<>();
	//
	// for (int i : UI.getUserInputMultiSelect())
	// selectedCategoryGroup.add(Config.categories.get(i));
	//
	// return selectedCategoryGroup;
	// }
	//
	// private static List<String> selectKeyword() {
	// UI.printListWithIndexNumbers(Config.keywords);
	//
	// List<String> selectedKeyword = new ArrayList<>();
	//
	// for (int i : UI.getUserInputMultiSelect())
	// selectedKeyword.add(Config.keywords.get(i));
	//
	// return selectedKeyword;
	// }

	private static <T> List<T> select(List<T> list) {

		UI.printListWithIndexNumbers(list);

		List<T> selectedItems = new ArrayList<>();

		for (int i : UI.getUserInputMultiSelect())
			selectedItems.add(list.get(i));

		return selectedItems;
	}

	private static Pair<String, String> selectUserAccount() {
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