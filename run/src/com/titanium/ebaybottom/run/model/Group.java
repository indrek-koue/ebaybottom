package com.titanium.ebaybottom.run.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group {

	private static final String EMPTY_USER = "###:###";
	public KeyValuePair userAccount;
	public String keyword;
	public List<Integer> categoryGroup = new ArrayList<Integer>();
	public int minPrice;
	public int maxPrice;
	
	public Group(KeyValuePair selectedUserAccount, String selectedKeyword,
			List<Integer> selectedCategoryGroup, int minPrice, int maxPrice) {
		this.userAccount = selectedUserAccount;
		this.keyword = selectedKeyword;
		this.categoryGroup = selectedCategoryGroup;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
	}

	public Group(String selectedKeyword, List<Integer> selectedCategoryGroup,
			int minPrice, int maxPrice) {
		this(null, selectedKeyword, selectedCategoryGroup, minPrice, maxPrice);
	}

	public Group(String row) {

		String[] columns = row.split("\\|");
		if (!columns[0].equals(EMPTY_USER)) {
			String username = columns[0].split(":")[0];
			String password = columns[0].split(":")[1];
			userAccount = new KeyValuePair(username, password);
		} else {
			userAccount = null;
		}

		keyword = columns[1];

		String[] gategories = columns[2].split(",");
		for (int i = 0; i < gategories.length; i++) {
			String rawString = gategories[i].replace("[", "").replace("]", "")
					.trim();
			categoryGroup.add(Integer.parseInt(rawString));
		}

		minPrice = Integer.parseInt(columns[3]);
		maxPrice = Integer.parseInt(columns[4]);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format(
				"%s|%s|%s|%s|%s|",
				userAccount == null ? EMPTY_USER : userAccount
						.toString(), keyword, Arrays
						.toString(categoryGroup.toArray()), minPrice,
				maxPrice);
	}

}
