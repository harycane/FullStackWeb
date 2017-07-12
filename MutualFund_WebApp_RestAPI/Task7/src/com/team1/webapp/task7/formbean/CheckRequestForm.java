package com.team1.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class CheckRequestForm extends FormBean {
	private String amount;
	private String action;
	
	public String getAmount() {return amount;}
	public String getAction() {
		return action;
	}
	
	public void setAmount(String s) {
		String a  = sanitize(s);
		amount = a.trim();
		}
	public void setAction(String s) {action = s;}
	
	public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();
        
        try {
        	double money = Double.parseDouble(amount);
        	if (money < 0.01) {
        		errors.add("The minimum money amount allowed is $0.01");
        	}
        } catch (NumberFormatException e) {
        	errors.add("Money amount has to be a number");
        } catch (NullPointerException e) {
        	errors.add("Money amount is required");
        }
        if (errors.size() > 0) return errors;		
        return errors;
    }
	private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
