package com.team1.webapp.task7.utility;

import java.util.Comparator;

import com.team1.webapp.task7.databean.EmployeeBean;


/**
 * This class is used to sort the names of funds
 * @author Hary
 *
 */
public class EmpNameComparator implements Comparator<EmployeeBean>{
	@Override
	public int compare(EmployeeBean o1, EmployeeBean o2) {
		String firstname1 = o1.getFirstname();
		String firstname2 = o2.getFirstname();
		int i = 0;
		while (i < firstname1.length() && i < firstname2.length()) {
			if (firstname1.charAt(i) < firstname2.charAt(i)) {
				return -1; 
			} else if (firstname1.charAt(i) > firstname2.charAt(i)) {
				return 1;
			} else {
				i++;
			}
		}
		
		String lastname1 = o1.getLastname();
		String lastname2 = o2.getLastname();
		while (i < lastname1.length() && i < lastname2.length()) {
			if (lastname1.charAt(i) < lastname2.charAt(i)) {
				return -1; 
			} else if (lastname1.charAt(i) > lastname2.charAt(i)) {
				return 1;
			} else {
				i++;
			}
		}
		return 0;
	}
}
