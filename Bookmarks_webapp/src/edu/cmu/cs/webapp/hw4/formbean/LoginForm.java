/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw4.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

public class LoginForm extends FormBean{
    private String email;
    private String password;
    private String button;
	
    public String getEmail()  { return email; }
    public String getPassword()  { return password; }
    public String getButton()    { return button; }
	
    public void setEmail(String s)  { email = s.trim(); }
    public void setPassword(String s)  { password = s.trim(); }
    public void setButton(String s)    { button   = s;        }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (email == null || email.length() == 0 || email.trim().isEmpty())
            errors.add("Email Address is a required field. Cannot be Empty.");
        if (password == null || password.length() == 0 || password.trim().isEmpty())
            errors.add("Password is a required field. Cannot be Empty.");
        if (button == null)
            errors.add("Button is required");


        if (errors.size() > 0) return errors;

        if (!button.equals("Login")) errors.add("Invalid button");
        if (email.matches(".*[<>\"].*")) errors.add("Email may not contain angle brackets or quotes");
		
        return errors;
    }
}
