package com.team1.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateCustomerForm extends FormBean {

	private String firstname;
	private String lastname;
	private String addrline1;
	private String addrline2;
	private String city;
	private String state;
	private String zip;
	private String cash;
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

	public String getAddrline1() {
		return addrline1;
	}

	public String getAddrline2() {
		return addrline2;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
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

	public String getCash() {
		return cash;
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

	public void setAddrline1(String s) {
		String a  = sanitize(s);
		addrline1 = a.trim();
	}

	public void setAddrline2(String s) {
		String a  = sanitize(s);
		addrline2 = a.trim();
	}

	public void setCity(String s) {
		String a  = sanitize(s);
		city = a.trim();
	}

	public void setState(String s) {
		String a  = sanitize(s);
		state = a.trim();
	}

	public void setZip(String s) {
		String a  = sanitize(s);
		zip = a.trim();
	}

	public void setCash(String s) {
		String a  = sanitize(s);
		cash = a.trim();
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

		if (username == null || username.length() == 0) {
			errors.add("Username is required");
		}

		if (password == null || password.length() == 0) {
			errors.add("Password is required");
		}
		if (firstname == null || firstname.length() == 0)
			errors.add("First Name is required");
		if (lastname == null || lastname.length() == 0)
			errors.add("Last Name is required");
		if (action == null)
			errors.add("Button is required");
		try {
			Double.parseDouble(cash);
		} catch (NumberFormatException e) {
			errors.add("Initial Deposit is required and has to be a number");
		} catch (NullPointerException e) {
			errors.add("Initial Deposit is required");
		}
		
		if (username.matches(".*[<>\"].*") || firstname.matches(".*[<>\"].*") || lastname.matches(".*[<>\"].*")) {
			errors.add("Item may not contain angle brackets or quotes");
		}
		if (addrline1.matches(".*[<>\"].*") || addrline2.matches(".*[<>\"].*") || city.matches(".*[<>\"].*")
				|| state.matches(".*[<>\"].*") || zip.matches(".*[<>\"].*")) {
			errors.add("Item may not contain angle brackets or quotes");
		}
		if (errors.size() > 0)
			return errors;

		if (!action.equals("Create"))
			errors.add("Invalid button");

		if (!password.equals(confirmpassword)) {
			errors.add("Passwords do not match");
		}

		return errors;
	}
	private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}