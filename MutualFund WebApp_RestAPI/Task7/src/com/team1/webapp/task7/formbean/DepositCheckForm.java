package com.team1.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class DepositCheckForm extends FormBean{
	private String username;
	private String amount;
	private String action;
	
	public String getUsername() {return username;}
	public String getAmount() {return amount;}
	public String getAction() {
		return action;
	}
	
	public void setUsername(String s) {
		String a  = sanitize(s);
		username = a;
		}
	public void setAmount(String s) {
		String a  = sanitize(s);
		amount = a;
		}
	public void setAction(String s) {
		action = s;
		}
	
	public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (username == null || username.trim().length() == 0) {
        	errors.add("Customer's username is required");
        }

        try {
        	double moneyAmount = Double.parseDouble(amount);
        	if (moneyAmount < 0.01) {
        		errors.add("The minimum money amount allowed is $0.01");
        	}
        } catch (NumberFormatException e) {
        	errors.add("Money amount has to be a number");
        	if (errors.size() > 0) return errors;	
        } catch (NullPointerException e) {
        	errors.add("Money amount is required");
        }	
        return errors;
    }
	private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}

