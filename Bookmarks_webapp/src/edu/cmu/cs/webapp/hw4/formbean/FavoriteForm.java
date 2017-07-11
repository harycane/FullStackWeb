/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw4.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class FavoriteForm extends FormBean {
    private String url;
    private String comment;
    private String button;
    
    public String getUrl() {
        return url;
    }
   
    public String getComment() {
    return comment;
}
    public String getButton() { return button; }
	
	public void setUrl(String s) { url = sanitize(s).trim();        }
	public void setComment(String s)   { comment   = sanitize(s).trim(); }
	public void setButton(String s) { button = s;        }

	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();

		 if (url == null || url.length() == 0 || url.trim().isEmpty()) {
	            errors.add("Url is a required field. Cannot be Empty.");
	        }
	       
	        if (comment == null || comment.length() == 0 || comment.trim().isEmpty() ) {
	            errors.add("Comment is a required field. Cannot be Empty.");
	        }
		
		if (errors.size() > 0) return errors;

       
        //Do we need to check for logout button ?
        if (!button.equals("AddFavorite")) errors.add("Invalid button");


		return errors;
	}
	
	 private String sanitize(String s) {
	        return s.replace("&", "&amp;").replace("<", "&lt;")
	                .replace(">", "&gt;").replace("\"", "&quot;");
	    }
}
