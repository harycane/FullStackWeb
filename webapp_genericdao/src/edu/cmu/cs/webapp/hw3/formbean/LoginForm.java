/* @author HaryKrishnan Ramasubramanian.
 * 25 November 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw3.formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class LoginForm {
   
	private String email;
	private String password;
	private String button;

	public LoginForm(HttpServletRequest request) {
		
	    email = request.getParameter("email").trim();
		password = request.getParameter("password").trim();
		button = request.getParameter("button");
	}
	
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
	public String getButton() {
		return button;
	}

	public boolean isPresent() {
		return button != null;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (email == null || email.length() == 0 || email.trim().isEmpty())
			errors.add("Email Address is a required field. Cannot be Empty.");
		if (password == null || password.length() == 0 || password.trim().isEmpty())
			errors.add("Password is a required field. Cannot be Empty.");
		if (button == null)
			errors.add("Button is required");

		if (errors.size() > 0)
			return errors;

		if (!button.equals("Login") && !button.equals("Register"))
			errors.add("Invalid button");
		if (email.matches(".*[<>\"].*"))
			errors.add("User Name may not contain angle brackets or quotes");

		return errors;
	}
}
