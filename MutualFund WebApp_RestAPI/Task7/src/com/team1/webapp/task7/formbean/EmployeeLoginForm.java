package com.team1.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;


public class EmployeeLoginForm extends FormBean {
	private String username;// employee username
	private String password;
	private String action;
	
	public String getUsername()  { return username; }
	public String getPassword()  { return password; }
	public String getAction() { return action; }
	
	public void setUsername(String s) { 
		String a  = sanitize(s);
		username = a.trim();  
		}
	public void setPassword(String s) {	
		String a  = sanitize(s);
		password = a.trim();  }
	public void setAction(String s) { 
		String a  = sanitize(s);
		action = a.trim();
		}
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (username == null || username.length() == 0) {
			errors.add("Employee User Name is required");
		}
		
		if (password == null || password.length() == 0) {
			errors.add("Employee Password is required");
		}
		return errors;
	}
	private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
