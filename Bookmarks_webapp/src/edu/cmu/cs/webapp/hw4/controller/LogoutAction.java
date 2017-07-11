/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw4.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import edu.cmu.cs.webapp.hw4.model.Model;
import edu.cmu.cs.webapp.hw4.model.UserDAO;

/*
 * Logs out by setting the "user" session attribute to null.
 * (Actions don't be much simpler than this.)
 */
public class LogoutAction extends Action {

    private UserDAO userDAO;
    public LogoutAction(Model model) {
        userDAO = model.getUserDAO();
    }

    public String getName() {
        return "logout.do";
    }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        try {
            request.setAttribute("userList", userDAO.getUsers());
            HttpSession session = request.getSession(false);
            session.setAttribute("hramasub_user", null);

            request.setAttribute("message","You are now logged out");
            return "success.jsp";
        
           
            
        }catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}
