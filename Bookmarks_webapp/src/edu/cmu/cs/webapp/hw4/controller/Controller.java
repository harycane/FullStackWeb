/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw4.controller;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.genericdao.RollbackException;
import org.mybeans.factory.BeanTable;



import edu.cmu.cs.webapp.hw4.model.*;
import edu.cmu.cs.webapp.hw4.databean.*;
import edu.cmu.cs.webapp.hw4.controller.ChangePwdAction;



@SuppressWarnings("serial")
public class Controller extends HttpServlet {

    
    public void init() throws ServletException {
        Model model = new Model(getServletConfig());
       
        Action.add(new ChangePwdAction(model));
       Action.add(new AddAction(model));
        Action.add(new ListAction(model));
        Action.add(new LoginAction(model));
        Action.add(new LogoutAction(model));
        Action.add(new ManageAction(model));
        
        Action.add(new RegisterAction(model));
        Action.add(new RemoveAction(model));
        Action.add(new UpdateAction(model));
        
        BeanTable<UserBean> uTable = BeanTable.getInstance(UserBean.class,"hramasub_user");
        
        BeanTable<FavoriteBean> favTable = BeanTable.getInstance(FavoriteBean.class,"hramasub_favorite");
        if (uTable.exists()){
           
            try{
           if( model.getUserDAO().getCount()==0) {
                UserBean u1 = new UserBean();
                u1.setEmail("viratkohli@india.com");
                u1.setFirstName("Virat");
                u1.setLastName("Kohli"); 
                u1.encodePassword("anushka"); //this is the password for user1; 
                                              //pls note that the pwd is hashed in db 
                
                model.getUserDAO().createNewUser(u1);
                
                UserBean u2 = new UserBean();
                u2.setEmail("joeroot@england.com");
                u2.setFirstName("Joe");
                u2.setLastName("Root");
                u2.encodePassword("ashes"); //this is the password for user2; 
                                            //pls note that the pwd is hashed in db 
                
                model.getUserDAO().createNewUser(u2);
                
                UserBean u3 = new UserBean();
                u3.setEmail("stevesmith@australia.com");
                u3.setFirstName("Steve");
                u3.setLastName("Smith");
                u3.encodePassword("aussie"); //this is the password for user3; 
                                            //pls note that the pwd is hashed in db 
                
                model.getUserDAO().createNewUser(u3);
           }}catch (RollbackException e) {
               e.printStackTrace();
            }
            }
        
        if(favTable.exists()) {
            try{
                if( model.getFavoriteDAO().getCount()==0) {
            FavoriteBean f1 = new FavoriteBean();
            f1.setUrl("http://www.cricinfo.com");
            f1.setComment("Cricket Score");
            f1.setId(1); f1.setOwner("viratkohli@india.com");
            model.getFavoriteDAO().createFavorite(f1);
            
            FavoriteBean f2 = new FavoriteBean();
            f2.setUrl("http://www.quora.com");
            f2.setComment("Q & A");
            f2.setId(1); f2.setOwner("viratkohli@india.com");
            model.getFavoriteDAO().createFavorite(f2);
            
            FavoriteBean f3 = new FavoriteBean();
            f3.setUrl("http://www.amazon.com");
            f3.setComment("shopping");
            f3.setId(1);f3.setOwner("viratkohli@india.com");
            model.getFavoriteDAO().createFavorite(f3);
            
            FavoriteBean f4 = new FavoriteBean();
            f4.setUrl("http://www.geeksforgeeks.org");
            f4.setComment("CS Portal");
            f4.setId(1); f4.setOwner("viratkohli@india.com");
            model.getFavoriteDAO().createFavorite(f4);
            
            
            FavoriteBean f5 = new FavoriteBean();
            f5.setUrl("http://www.google.com/");
            f5.setComment("Search Engine");
            f5.setId(2); f5.setOwner("joeroot@england.com");
            model.getFavoriteDAO().createFavorite(f5);
            
            FavoriteBean f6 = new FavoriteBean();
            f6.setUrl("http://www.youtube.com/");
            f6.setComment("Videos");
            f6.setId(2); f6.setOwner("joeroot@england.com");
            model.getFavoriteDAO().createFavorite(f6);
            
            FavoriteBean f7 = new FavoriteBean();
            f7.setUrl("http://www.linkedin.com/");
            f7.setComment("Professional Network");
            f7.setId(2); f7.setOwner("joeroot@england.com");
            model.getFavoriteDAO().createFavorite(f7);
            
            FavoriteBean f8 = new FavoriteBean();
            f8.setUrl("http://leetcode.com/");
            f8.setComment("DS and Algo");
            f8.setId(2); f8.setOwner("joeroot@england.com");
            model.getFavoriteDAO().createFavorite(f8);
            
            FavoriteBean f9 = new FavoriteBean();
            f9.setUrl("http://www.apple.com/");
            f9.setComment("Laptop and Mobile");
            f9.setId(3); f9.setOwner("stevesmith@australia.com");
            model.getFavoriteDAO().createFavorite(f9);
            
            
            FavoriteBean f10 = new FavoriteBean();
            f10.setUrl("https://www.goodreads.com/");
            f10.setComment("Book Review");
            f10.setId(3); f10.setOwner("stevesmith@australia.com");
            model.getFavoriteDAO().createFavorite(f10);
            
            FavoriteBean f11 = new FavoriteBean();
            f11.setUrl("http://www.reddit.com/");
            f11.setComment("Front page of internet");
            f11.setId(3); f11.setOwner("stevesmith@australia.com");
            model.getFavoriteDAO().createFavorite(f11);
            
            FavoriteBean f12 = new FavoriteBean();
            f12.setUrl("http://www.hackerrank.com/");
            f12.setComment("Online Judge");
            f12.setId(3); f12.setOwner("stevesmith@australia.com");
            model.getFavoriteDAO().createFavorite(f12);
            
          
            
            
                }}catch (RollbackException e) {
            e.printStackTrace();
         } 
        }
    }

        
            
        
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nextPage = performTheAction(request);
        sendToNextPage(nextPage, request, response);
    }

    /*
     * Extracts the requested action and (depending on whether the user is
     * logged in) perform it (or make the user login).
     * 
     * @param request
     * 
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String servletPath = request.getServletPath();
        UserBean user = (UserBean) session.getAttribute("hramasub_user");
        String action = getActionName(servletPath);

        // System.out.println("servletPath="+servletPath+" requestURI="+request.getRequestURI()+"  user="+user);

        if (action.equals("register.do") || action.equals("login.do")||action.equals("update.do") || action.equals("list.do")) {
            // Allow these actions without logging in
            return Action.perform(action, request);
        }
       
        if (user == null) {
            // If the user hasn't logged in, direct him to the login page
            return Action.perform("login.do", request);
        }

        // Let the logged in user run his chosen action
        return Action.perform(action, request);
    }

    /*
     * If nextPage is null, send back 404 If nextPage ends with ".do", redirect
     * to this page. If nextPage ends with ".jsp", dispatch (forward) to the
     * page (the view) This is the common case
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    request.getServletPath());
            return;
        }

        if (nextPage.endsWith(".do")) {
            response.sendRedirect(nextPage);
            return;
        }

        if (nextPage.endsWith(".jsp")) {
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
                    + nextPage);
            d.forward(request, response);
            return;
        }
        if(nextPage.startsWith("http")){
            response.sendRedirect(nextPage);
            return;
        }

        /*if (nextPage.equals("image")) {
            RequestDispatcher d = request.getRequestDispatcher(nextPage);
            d.forward(request, response);
            return;
        }*/

        throw new ServletException(Controller.class.getName()
                + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

    /*
     * Returns the path component after the last slash removing any "extension"
     * if present.
     */
    private String getActionName(String path) {
        // We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }
}
