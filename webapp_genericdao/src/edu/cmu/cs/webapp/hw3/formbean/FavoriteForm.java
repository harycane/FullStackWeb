/* @author HaryKrishnan Ramasubramanian.
 * 25 November 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw3.formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class FavoriteForm {
    private String url;
    private String comment;

    public FavoriteForm(HttpServletRequest request) {
        url = sanitize(request.getParameter("url")).trim();
        comment = sanitize(request.getParameter("comment")).trim();
    }

    public String getUrl() {
        return url;
    }
   
    public String getComment() {
    return comment;
}
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (url == null || url.length() == 0 || url.trim().isEmpty()) {
            errors.add("Url is a required field. Cannot be Empty.");
        }
       
        if (comment == null || comment.length() == 0 || comment.trim().isEmpty() ) {
            errors.add("Comment is a required field. Cannot be Empty.");
        }
       
        return errors;
    }

    private String sanitize(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;");
    }
}
