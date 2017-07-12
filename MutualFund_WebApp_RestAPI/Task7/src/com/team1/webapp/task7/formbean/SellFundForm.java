package com.team1.webapp.task7.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class SellFundForm extends FormBean{
	private String shares;
	private String action;
	
	public String getShares() {return shares;}
	public String getAction() {
		return action;
	}
	
	public void setShares(String s) {
		String a  = sanitize(s);
		shares = a.trim();}
	public void setAction(String s) {action = s;}
	
	public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (errors.size() > 0) return errors;		
        return errors;
    }
	private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
