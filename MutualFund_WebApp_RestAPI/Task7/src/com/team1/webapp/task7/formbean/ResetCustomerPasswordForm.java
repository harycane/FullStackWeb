package com.team1.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ResetCustomerPasswordForm extends FormBean {
	private String username;
	private String password;
	private String confirmpassword;
	private String action;
	
	public String getUsername()  { return username; }
	public void setUsername(String s)  { 
		String a  = sanitize(s);
		username = a.trim(); 
		}
	
	public String getPassword()  { return password; }
	public String getConfirmpassword() { return confirmpassword; }
	
	public void setPassword(String s)  { 
		String a  = sanitize(s);
		password = a.trim(); }
    public void setConfirmpassword(String s)  { 
    	String a  = sanitize(s);
    	confirmpassword = a.trim(); }
    
    public String getAction() {
    	return action;
    }
    public void setAction(String s) {
    	action = s;
    }
    
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();
        if (username == null || username.trim().length() == 0) {
        	errors.add("Customer's username is required");
        }
        if (password == null || password.length() == 0) {
        	errors.add("New Password is required");
        }
        if (confirmpassword == null || confirmpassword.length() == 0) {
        	errors.add("Confirm Password is required");
        }
        if (errors.size() > 0) {
        	return errors;
        }
		if (password != null) {
			if (!password.equals(confirmpassword)) {
				errors.add("Passwords do not match");
			}
		}

        return errors;
    }
    private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
