package com.team1.webapp.task7.utility;

import java.util.Comparator;
import java.util.List;

public class FundListComparator implements Comparator<List<String>>{
	@Override
	public int compare(List<String> o1, List<String> o2) {
		String name1 = o1.get(0);
		String name2 = o2.get(0);
		int i = 0;
		while (i < name1.length() && i < name2.length()) {
			if (name1.charAt(i) < name2.charAt(i)) {
				return -1; 
			} else if (name1.charAt(i) > name2.charAt(i)) {
				return 1;
			} else {
				i++;
			}
		}
		return 0;
	}

}
