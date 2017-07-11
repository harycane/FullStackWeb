package com.team1.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateEmployeeForm extends FormBean {

	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String confirmpassword;
	private String action;

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getAction() {
		return action;
	}

	public String getConfirmpassword() {
		return confirmpassword;
	}

	public boolean isPresent() {
		if (action == null) {
			return false;
		}
		return action.equals("Create");
	}

	public void setUsername(String s) {
		String a  = sanitize(s);
		username = a.trim();
	}

	public void setFirstname(String s) {
		String a  = sanitize(s);
		firstname = a.trim();
	}

	public void setLastname(String s) {
		String a  = sanitize(s);
		lastname = a.trim();
	}

	public void setPassword(String s) {
		String a  = sanitize(s);
		password = a.trim();
	}

	public void setConfirmpassword(String s) {
		String a  = sanitize(s);
		confirmpassword = a.trim();
	}

	public void setAction(String s) {
		action = s;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (username == null || username.trim().length() == 0)
			errors.add("Username is required");
		if (password == null || password.trim().length() == 0)
			errors.add("Password is required");
		if (firstname == null || firstname.trim().length() == 0)
			errors.add("First Name is required");
		if (lastname == null || lastname.trim().length() == 0)
			errors.add("Last Name is required");
		if (action == null)
			errors.add("Button is required");

		if (errors.size() > 0)
			return errors;

		if (!action.equals("Create"))
			errors.add("Invalid button");
		if (!password.equals(confirmpassword)) {
			errors.add("Passwords do not match");
		}
		if (username.matches(".*[<>\"].*") || firstname.matches(".*[<>\"].*") || lastname.matches(".*[<>\"].*")) {
			errors.add("Item may not contain angle brackets or quotes");
		}

		return errors;
	}
	private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}