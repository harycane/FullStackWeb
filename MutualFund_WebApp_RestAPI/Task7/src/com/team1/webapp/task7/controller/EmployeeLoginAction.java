package com.team1.webapp.task7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.team1.webapp.task7.databean.EmployeeBean;
import com.team1.webapp.task7.formbean.EmployeeLoginForm;
import com.team1.webapp.task7.model.EmployeeDAO;
import com.team1.webapp.task7.model.Model;

public class EmployeeLoginAction extends Action{

private FormBeanFactory<EmployeeLoginForm> formBeanFactory = FormBeanFactory.getInstance(EmployeeLoginForm.class);
	
	private EmployeeDAO employeeDAO;


	public EmployeeLoginAction(Model model) {
		employeeDAO = model.getEmployeeDAO();  
	}

	public String getName() { return "employeeLogin.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        HttpSession session = request.getSession();

        try {
	    	EmployeeLoginForm form = formBeanFactory.create(request);
	        request.setAttribute("form",form);
	        
            if (!form.isPresent()) {
                return "employeeLogin.jsp";
            }
            
            if (form.getAction().equals("Go Back")) {
            	return "welcome.jsp";
            }
            
	        // Any validation errors?
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "employeeLogin.jsp";
	        }
	        
	        if (form.getAction().equals("Login")) {
		        // Look up the user
		        EmployeeBean employee = employeeDAO.getEmployeeByUserName(form.getUsername());
		        
		        if (employee == null) {
		            errors.add("Employee not found");
		            return "employeeLogin.jsp";
		        }
	
		        // Check the password
		        if (!employee.getPassword().equals(form.getPassword())) {
		            errors.add("Incorrect password");
		            return "employeeLogin.jsp";
		        }
		
		        // Attach (this copy of) the user bean to the session
	
		        session.setAttribute("employee", employee);
		    	session.setAttribute("customer", null);
	        }
	        return "employeeHome.jsp";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }

}
