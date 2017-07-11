/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw4.formbean;

import java.util.ArrayList;
import java.util.List;

import org.mybeans.form.FormBean;

//import javax.servlet.http.HttpServletRequest;

public class RegisterForm extends FormBean {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String button;
    private String confirm;
  
    
    public String getEmail() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPassword() {
        return password;
    }
    public String getButton() {
        return button;
    }
    public String getConfirm() {
        return confirm;
    }

    public void setEmail(String s)  { email = s.trim(); }
    public void setFirstName(String s)  { firstName = s.trim(); }
    public void setLastName(String s)  { lastName = s.trim(); }
    public void setPassword(String s)  { password = s.trim(); }
    public void setButton(String s)    { button   = s;        }
    public void setConfirm(String s)    { confirm   = s.trim();        }
    
    public boolean isPresent() {
        return button != null;
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (email == null || email.length() == 0 || email.trim().isEmpty())
            errors.add("Email Address is a required field. Cannot be Empty.");
        if(firstName == null || firstName.length()==0 || firstName.trim().isEmpty())
            errors.add("First Name is a required field. Cannot be Empty.");
        if(lastName == null || lastName.length()==0 || lastName.trim().isEmpty())
            errors.add("Last Name is a required field. Cannot be Empty.");
        if (password == null || password.length() == 0 || password.trim().isEmpty())
            errors.add("Password is a required field. Cannot be Empty.");
        if (button == null)
            errors.add("Button is required");
        if (confirm == null || confirm.length() == 0 || confirm.trim().isEmpty())
            errors.add("Confirm Password is a required field. Cannot be Empty.");
        if (errors.size() > 0)
            return errors;

        if (!button.equals("Register")) 
            errors.add("Invalid button");
        if (email.matches(".*[<>\"].*")|| firstName.matches(".*[<>\"].*")|| lastName.matches(".*[<>\"].*"))
            errors.add("Email/FirstName/LastName may not contain angle brackets or quotes");
        if(!password.equals(confirm))
            errors.add("Password retyped does not match with the original value");
        return errors;
    }
}
