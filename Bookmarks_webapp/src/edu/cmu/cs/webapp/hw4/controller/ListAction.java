/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw4.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import edu.cmu.cs.webapp.hw4.model.*;


import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.cs.webapp.hw4.databean.*;
import edu.cmu.cs.webapp.hw4.formbean.*;

/*
 * Looks up the photos for a given "user".
 * 
 * If successful:
 *   (1) Sets the "userList" request attribute in order to display
 *       the list of users on the navbar.
 *   (2) Sets the "photoList" request attribute in order to display
 *       the list of given user's photos for selection.
 *   (3) Forwards to list.jsp.
 */
public class ListAction extends Action {
	private FormBeanFactory<UserForm> formBeanFactory = FormBeanFactory
			.getInstance(UserForm.class);

	private FavoriteDAO favoriteDAO;
	private UserDAO userDAO;

	public ListAction(Model model) {
		favoriteDAO = model.getFavoriteDAO();
		userDAO = model.getUserDAO();
	}

	public String getName() {
		return "list.do";
	}

	public String perform(HttpServletRequest request) {
		// Set up the request attributes (the errors list and the form bean so
		// we can just return to the jsp with the form if the request isn't
		// correct)
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			// Set up user list for nav bar
			request.setAttribute("userList", userDAO.getUsers());

			UserForm form = formBeanFactory.create(request);

			String email = form.getEmail();
			if (email == null || email.length() == 0 || email.trim().isEmpty()) {
				errors.add("Email must be specified");
				return "error.jsp";
			}

			// Set up photo list
			UserBean user = userDAO.readExistingUser(email);
			if (user == null) {
				errors.add("Invalid User: " + email);
				return "error.jsp";
			}

			FavoriteBean[] favoriteList = favoriteDAO.getUserFavorites(user.getEmail());
			request.setAttribute("favoriteList", favoriteList);
			return "list.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
