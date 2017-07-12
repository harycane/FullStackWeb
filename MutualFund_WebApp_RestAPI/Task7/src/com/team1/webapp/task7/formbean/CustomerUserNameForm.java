
package com.team1.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CustomerUserNameForm extends FormBean {
	private String username = "";
	
	public String getUsername()  { return username; }
	
	public void setUsername(String s)  { 
		String a  = sanitize(s);
		username = a.trim(); }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (username == null || username.length() == 0 || username.trim().isEmpty()) {
			errors.add("Username is required");
		}
		
		return errors;
	}
	private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
