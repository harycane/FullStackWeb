/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package com.team1.webapp.task7.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import com.team1.webapp.task7.databean.CustomerBean;
import com.team1.webapp.task7.model.CusDAO;
import com.team1.webapp.task7.model.Model;
import com.team1.webapp.task7.utility.CustomerNameComparator;

/*
 * Sets up the request attributes for manage.jsp.
 * This is the way to enter "Manage Your Photos"
 * from someplace else in the site.
 * 
 * Sets the "userList" request attribute in order to display
 * the list of users on the navbar.
 * 
 * Sets the "photoList" request attribute in order to display
 * the list of user's photos for management.
 * 
 * Forwards to manage.jsp.
 */
public class ViewCustListByEmpAction extends Action {

	
	//private FormBeanFactory<CustomerUserNameForm> formBeanFactory = FormBeanFactory
		//	.getInstance(CustomerUserNameForm.class);

	private CusDAO customerDAO;
	
	public ViewCustListByEmpAction(Model model) {
    	customerDAO = model.getCustomerDAO();
    	
	}
	

	public String getName() { return "viewListByEmp.do"; }

	public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
       
		
		try {
			CustomerBean[] cuslist = customerDAO.getCustomers();
			if (cuslist == null || cuslist.length == 0) {
				errors.add("No Existing Customers.");
				
			}
			if (cuslist != null) {
				Arrays.sort(cuslist, new CustomerNameComparator());
			}
			request.setAttribute("custList", cuslist);
			 request.setAttribute("errors",errors);// in jsp need to write ahref with user name as the url name and link address
        	return "viewListByEmp.jsp"; //jsp that has for each iterating and displaying hyperlinked username lists.
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
		
    }
}
