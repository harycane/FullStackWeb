/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw4.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class UserForm extends FormBean {
	private String email = "";
	
	public String getEmail()  { return email; }
	
	public void setEmail(String s)  { email = trimAndConvert(s,"<>>\"]"); }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (email == null || email.length() == 0 || email.trim().isEmpty()) {
			errors.add("Email is required");
		}
		
		return errors;
	}
}
