package com.team1.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CreateFundForm extends FormBean {
	private String symbol;
	private String name;
	private String action;

	public String getSymbol() {
		return symbol;
	}

	public String getName() {
		return name;
	}

	public String getAction() {
		return action;
	}

	public void setSymbol(String s) {
		String a  = sanitize(s);
		symbol = a.trim();
	}

	public void setName(String s) {
		String a  = sanitize(s);
		name = a.trim();
	}

	public void setAction(String s) {
		action = s;
	}

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		if (symbol == null || symbol.trim().length() == 0) {
			errors.add("Fund symbol is required");
		}

		if (name == null || name.trim().length() == 0) {
			errors.add("Fund name is required");
		}
		
		if (name.matches(".*[<>\"].*") || symbol.matches(".*[<>\"].*")) {
			errors.add("Name and symbol may not contain angle brackets, quotes");
		}
		
		if (name.matches(".*'.*") || symbol.matches(".*'.*")) {
			errors.add("Name and symbol may not contain apostrophe");
		}
		
		if (errors.size() > 0)
			return errors;
		return errors;
	}
	private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
