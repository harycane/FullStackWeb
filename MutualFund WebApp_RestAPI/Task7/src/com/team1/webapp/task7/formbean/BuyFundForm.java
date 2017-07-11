package com.team1.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class BuyFundForm extends FormBean{
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
        	double moneyAmount = Double.parseDouble(amount);
        	if (moneyAmount < 10.0) {
        		errors.add("The minimum money amount allowed is $ 10.0");
        	}
        } catch (NumberFormatException e) {
        	errors.add("Money amount need to be specified, and it has to be a number");
        } catch (NullPointerException e) {
        	errors.add("Money amount need to be specified, and it has to be a number");
        }
        if (errors.size() > 0) return errors;		
        return errors;
        
    }
    private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
