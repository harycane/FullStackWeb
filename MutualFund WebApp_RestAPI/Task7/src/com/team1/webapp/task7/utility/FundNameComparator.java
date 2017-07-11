package com.team1.webapp.task7.utility;

import java.util.Comparator;

import com.team1.webapp.task7.databean.FundBean;

/**
 * This class is used to sort the names of funds
 * @author Ding
 *
 */
public class FundNameComparator implements Comparator<FundBean>{
	@Override
	public int compare(FundBean o1, FundBean o2) {
		String name1 = o1.getName();
		String name2 = o2.getName();
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
