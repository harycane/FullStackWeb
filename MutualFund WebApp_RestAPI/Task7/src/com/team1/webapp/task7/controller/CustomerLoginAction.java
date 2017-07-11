package com.team1.webapp.task7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.team1.webapp.task7.databean.CustomerBean;
import com.team1.webapp.task7.formbean.CustomerLoginForm;
import com.team1.webapp.task7.model.CusDAO;
import com.team1.webapp.task7.model.Model;

public class CustomerLoginAction extends Action{

private FormBeanFactory<CustomerLoginForm> formBeanFactory = FormBeanFactory.getInstance(CustomerLoginForm.class);
	
	private CusDAO customerDAO;

	public CustomerLoginAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	public String getName() { 
		return "customerLogin.do"; 
	}
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        HttpSession session = request.getSession();

        try {
	    	CustomerLoginForm form = formBeanFactory.create(request);
	        request.setAttribute("form",form);
	        
            if (!form.isPresent()) {
                return "customerLogin.jsp";
            }
            
            if (form.getAction().equals("Go Back")) {
            	return "welcome.jsp";
            }
            
            if (form.getAction().equals("Login")) {
		        // Any validation errors?
		        errors.addAll(form.getValidationErrors());
		        if (errors.size() != 0) {
		            return "customerLogin.jsp";
		        }
	
		        // Look up the user
		        CustomerBean customer = customerDAO.getCustomerByUserName(form.getUsername());
		        
		        if (customer == null) {
		            errors.add("Customer not found");
		            return "customerLogin.jsp";
		        }
	
		        // Check the password
		        if (!customer.getPassword().equals(form.getPassword())) {
		            errors.add("Incorrect password");
		            return "customerLogin.jsp";
		        }
		
		        // Attach (this copy of) the user bean to the session
	
		        session.setAttribute("customer", customer);
		        session.setAttribute("employee", null);
            }
	        return "viewacct.do";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }

}
