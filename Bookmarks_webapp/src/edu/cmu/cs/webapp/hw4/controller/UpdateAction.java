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
 * Uploads a file from the user.  If successful, sets the "userList"
 * and "photoList" request attributes, creates a new Photo bean with the
 * image, and forward (back to) manage.jsp.
 * 
 * Note that to upload a file, the multipart encoding type is used
 * in the HTML form.  This needs to be specially parsed.  The FormBeanFactory
 * can do this, but to do it, the FormBeanFactory uses the Jakarta Commons FileUpload
 * package (org.apache.commons.fileupload).
 * These classes are in the commons-fileupload-x.x.jar file in the webapp's
 * WEB-INF/lib directory.  See the User Guide on
 * http://jakarta.apache.org/commons/fileupload for details.
 */
public class UpdateAction extends Action {
    private FormBeanFactory<FavoriteForm> formBeanFactory = FormBeanFactory
            .getInstance(FavoriteForm.class);

    private FavoriteDAO favoriteDAO;
    private UserDAO userDAO;

    public UpdateAction(Model model) {
        favoriteDAO = model.getFavoriteDAO();
        userDAO = model.getUserDAO();
    }

    public String getName() {
        return "update.do";
    }

    public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
            // Set up user list for nav bar
            request.setAttribute("userList", userDAO.getUsers());

            UserBean user = (UserBean) request.getSession(false).getAttribute("hramasub_user");
            
          if(request.getParameter("fid")!=null) {
           //     FavoriteBean fav = favoriteDAO.read(Integer.parseInt(request.getParameter("fid")));
              
             //   if(user == null || user.getId()==fav.getId())
                //{
                    Integer fid = Integer.parseInt(request.getParameter("fid"));
                
                favoriteDAO.IncrementCount(fid);
                return request.getParameter("hyperlink");
                }
                else{
                    return request.getParameter("hyperlink");
                }
          
            
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "manage.jsp";
        } /*catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "manage.jsp";
        }*/
    }

   
}
