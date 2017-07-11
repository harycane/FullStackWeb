/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw4.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;


import edu.cmu.cs.webapp.hw4.databean.*;
import edu.cmu.cs.webapp.hw4.databean.UserBean;
import edu.cmu.cs.webapp.hw4.formbean.*;
import edu.cmu.cs.webapp.hw4.model.FavoriteDAO;
import edu.cmu.cs.webapp.hw4.model.*;


public class AddAction extends Action {
	private FormBeanFactory<FavoriteForm>  itemFormFactory  = FormBeanFactory.getInstance(FavoriteForm.class);
	
	private FavoriteDAO favoriteDAO;
	 private UserDAO userDAO;
	 
	public AddAction(Model model) {
		favoriteDAO = model.getFavoriteDAO();
		userDAO = model.getUserDAO();
	}

	public String getName() { return "addfav.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        try {
       		// Fetch the items now, so that in case there is no form or there are errors
       		// We can just dispatch to the JSP to show the item list (and any errors)
            request.setAttribute("userList", userDAO.getUsers());
            UserBean user = (UserBean) request.getSession(false).getAttribute("hramasub_user");
       		request.setAttribute("favoriteList", favoriteDAO.getUserFavorites(user.getEmail()));
       		
	        FavoriteForm form = itemFormFactory.create(request);
        	request.setAttribute("form", form);

	        errors.addAll(form.getValidationErrors());
	        if (errors.size() > 0) {
	        	return "manage.jsp";
	        }
	        
	        FavoriteBean bean = new FavoriteBean();
	        bean.setUrl(form.getUrl());
       		bean.setComment(form.getComment());
       		bean.setId(((UserBean) request.getSession().getAttribute("hramasub_user")).getId());
       		bean.setOwner(((UserBean) request.getSession().getAttribute("hramasub_user")).getEmail());

        	if (form.getButton().equals("AddFavorite")) {
        		favoriteDAO.createFavorite(bean);
        	}  else {
        		errors.add("Invalid button: " + form.getButton());
        	}

       		// Fetch the items again, since we modified the list
       		request.setAttribute("favoriteList", favoriteDAO.getUserFavorites(((UserBean) request.getSession().getAttribute("hramasub_user")).getEmail()));
       		
       		return "manage.jsp";

        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
