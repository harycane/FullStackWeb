/* @author HaryKrishnan Ramasubramanian.
 * 25 November 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw3.formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class RegisterForm {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String button;

    public RegisterForm(HttpServletRequest request) {
        email = request.getParameter("email").trim();
        firstName = request.getParameter("firstName").trim();
        lastName = request.getParameter("lastName").trim();
        password = request.getParameter("password").trim();
        button = request.getParameter("button2");
    }
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

        if (errors.size() > 0)
            return errors;

        if (!button.equals("Register")) // && !button.equals("Login"))
            errors.add("Invalid button");
        if (email.matches(".*[<>\"].*"))
            errors.add("User Name may not contain angle brackets or quotes");

        return errors;
    }
}
