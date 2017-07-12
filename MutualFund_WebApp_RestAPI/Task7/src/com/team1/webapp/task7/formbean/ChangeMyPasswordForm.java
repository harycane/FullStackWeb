package com.team1.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class ChangeMyPasswordForm extends FormBean {
	private String password;
	private String confirmpassword;
	private String action;
	
	public String getPassword()  { return password; }
	public String getConfirmpassword() { return confirmpassword; }
	
	public void setPassword(String s)  {
		String a  = sanitize(s);
		password = a.trim(); 
		}
    public void setConfirmpassword(String s)  { 
    	String a  = sanitize(s);
		confirmpassword = a.trim(); 
    	 }
    
    public String getAction() {
    	return action;
    }
    public void setAction(String s) {
    	action = s;
    }
    
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (password == null || password.length() == 0) {
        	errors.add("New Password is required");
        }
        
        if (errors.size() > 0) {
        	return errors;
        }
        
		if (!password.equals(confirmpassword)) {
			errors.add("Passwords do not match");
		}

        if (!action.equals("Change"))
			errors.add("Invalid button");
        
        return errors;
    }
    private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}

