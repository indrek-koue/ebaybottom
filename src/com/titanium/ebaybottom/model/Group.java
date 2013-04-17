package com.titanium.ebaybottom.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Group {

	private static final String EMPTY_USER = "###:###";
	public KeyValuePair selectedUserAccount;
	public String selectedKeyword;
	public List<Integer> selectedCategoryGroup;
	public int minPrice;
	public int maxPrice;

	public Group(KeyValuePair selectedUserAccount, String selectedKeyword,
			List<Integer> selectedCategoryGroup, int minPrice, int maxPrice) {
		this.selectedUserAccount = selectedUserAccount;
		this.selectedKeyword = selectedKeyword;
		this.selectedCategoryGroup = selectedCategoryGroup;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
	}

	public Group(String selectedKeyword, List<Integer> selectedCategoryGroup,
			int minPrice, int maxPrice) {
		this(null, selectedKeyword, selectedCategoryGroup, minPrice, maxPrice);
	}

	public Group(String[] columns) {
		// TODO Auto-generated constructor stub

		if (!columns[0].equals(EMPTY_USER))
			selectedUserAccount = new KeyValuePair(columns[0].split(":")[0],
					columns[0].split(":")[1]);

		selectedKeyword = columns[1];

		String[] gategories = columns[2].split(",");
		selectedCategoryGroup = new ArrayList<Integer>();
		for (int i = 0; i < gategories.length; i++)
			selectedCategoryGroup.add(Integer.parseInt(gategories[i]));

		minPrice = Integer.parseInt(columns[3]);
		maxPrice = Integer.parseInt(columns[4]);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return String.format(
				"%s|%s|%s|%s|%s|",
				selectedUserAccount == null ? EMPTY_USER : selectedUserAccount
						.toString(), selectedKeyword, Arrays
						.toString(selectedCategoryGroup.toArray()), minPrice,
				maxPrice);
	}

}
