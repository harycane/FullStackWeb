
/* @author HaryKrishnan Ramasubramanian.
 * 25 November 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw3;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import edu.cmu.cs.webapp.hw3.dao.FavoriteDAO;
import edu.cmu.cs.webapp.hw3.dao.UserDAO;
import edu.cmu.cs.webapp.hw3.databean.FavoriteBean;
import edu.cmu.cs.webapp.hw3.databean.UserBean;
import edu.cmu.cs.webapp.hw3.formbean.FavoriteForm;
import edu.cmu.cs.webapp.hw3.formbean.LoginForm;
import edu.cmu.cs.webapp.hw3.formbean.RegisterForm;

public class HW3 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private FavoriteDAO favoriteDAO;
    private UserDAO userDAO;
   
    public void init() throws ServletException {
        String jdbcDriverName = getInitParameter("jdbcDriver");
        String jdbcURL = getInitParameter("jdbcURL");

        try {
            ConnectionPool cp = new ConnectionPool(jdbcDriverName, jdbcURL);
/*  
            cp.setDebugOutput(System.out);  // Print out the generated SQL
*/
            userDAO = new UserDAO(cp, "hramasub_user");
            favoriteDAO = new FavoriteDAO(cp, "hramasub_favorite");
        } catch (DAOException e) {
            throw new ServletException(e);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("hramasub_user") == null) {
            outputLoginPage(response, null, null);
        } else {
            outputToDoList(request,response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
              
        if (session.getAttribute("hramasub_user") == null) {
            if (request.getParameter("button")!=null) {
          

                login(request, response);
            }
            if (request.getParameter("button1")!=null) {
             
                outputRegisterPage(response,null,null);
            }
            if (request.getParameter("button2")!=null) {
                
                register(request,response);
            }
        } else {
            manageList(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<String>();

        LoginForm form = new LoginForm(request);
        
        errors.addAll(form.getValidationErrors());
        if (errors.size() != 0) {
            outputLoginPage(response, form, errors);
            return;
        }

        try {
           // UserBean user;
            
            
               // UserBean[] a = userDAO.match(MatchArg.equals("email", form.getEmail()));
               UserBean user = userDAO.readExistingUser(form.getEmail());
               
               if(user == null) {
                   errors.add("No such user");
                   outputLoginPage(response, form, errors);
                   return;
               }
               
               if (!form.getPassword().equals(user.getPassword())) {
                    errors.add("Incorrect password");
                    outputLoginPage(response, form, errors);
                    return;
                }
        
                else {

                    HttpSession session = request.getSession();
                    session.setAttribute("hramasub_user", user);
                  
                    outputToDoList(request,response);
                }
                 /*catch(ArrayIndexOutOfBoundsException e) {
                
                errors.add("No such user");
                outputLoginPage(response, form, errors);
                return;
                }*/
           
               

            
            } catch (RollbackException e) {
            errors.add(e.getMessage());
            outputLoginPage(response, form, errors);
            }
       
    }

    private void register(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<String>();

        RegisterForm form = new RegisterForm(request);

        errors.addAll(form.getValidationErrors());
        if (errors.size() != 0) {
            outputRegisterPage(response, form, errors);
            return;
        }

        try {
           
            //Transaction.begin();
            UserBean user;
            user = new UserBean();
            user.setEmail(form.getEmail());
            user.setFirstName(form.getFirstName());
            user.setLastName(form.getLastName());
            user.setPassword(form.getPassword());

            UserBean[] a = userDAO.match(MatchArg.equals("email", form.getEmail()));

                if(a.length > 0) {
                    errors.add("User with this email already exists");
                    outputRegisterPage(response, form, errors);
                    return;
                }
            
            userDAO.createNewUser(user);
            HttpSession session = request.getSession();
            session.setAttribute("hramasub_user", user);
            //Transaction.commit();
            outputToDoList(request,response);
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            outputRegisterPage(response, form, errors);
        }/*finally {
            if (Transaction.isActive())
                Transaction.rollback();
        }*/
       
    }


    private void manageList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Look at the action parameter to see what we're doing to the list
        
     try{
        if(request.getParameter("fid")!=null) {
            Integer fid = Integer.parseInt(request.getParameter("fid"));
            favoriteDAO.IncrementCount(fid);
            outputToDoList(request,response);
            return;
        }
        }catch (RollbackException e) {
            List<String> errors = new ArrayList<String>();

            errors.add(e.getMessage());
            outputToDoList(request,response, errors);
        }
        
        String action = request.getParameter("action");

        
        if (action == null) {
            outputToDoList(request,response, "No action specified.");
            return;
        }
        if (action.equals("Logout")) {
            request.getSession().setAttribute("hramasub_user",null);
            //count2 = 1;
            outputLoginPage(response,null,null);
            return;
        }
        if (action.equals("Add Favorite")) {
            processAdd(request, response, false);
            return;
        }
        

        outputToDoList(request,response, "Invalid action: " + action);
    }

    private void processAdd(HttpServletRequest request, HttpServletResponse response, boolean addToTop)
            throws ServletException, IOException {
        List<String> errors = new ArrayList<String>();

        FavoriteForm form = new FavoriteForm(request);

        errors.addAll(form.getValidationErrors());
        if (errors.size() > 0) {
            outputToDoList(request,response, errors);
            return;
        }

        try {
            FavoriteBean bean = new FavoriteBean();
            bean.setUrl(form.getUrl());
            bean.setComment(form.getComment());
            UserBean u = (UserBean) request.getSession().getAttribute("hramasub_user");
            bean.setId(u.getId());
            
            favoriteDAO.createFavorite(bean);
            
            outputToDoList(request,response, "Item Added");
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            outputToDoList(request,response, errors);
        }
    }

    // Methods that generate & output HTML
    
    private final String servletName = this.getClass().getSimpleName();

    private void generateHead(PrintWriter out) {
        out.println("  <head>");
        out.println("    <meta charset=\"utf-8\"/>");
        out.println("    <title>" + servletName + "</title>");
        out.println("  </head>");
    }

    private void outputLoginPage(HttpServletResponse response, LoginForm form, List<String> errors) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");

        generateHead(out);

        out.println("<body>");
        out.println("<h2>" + servletName + " Login</h2>");

       

        // Generate an HTML <form> to get data from the user
        out.println("<form method=\"POST\">");
        out.println("    <table style=\"border: 0.5px solid #000000; \">");
        out.println("        <tr>");
        out.println(
                "            <td style=\"font-size: x-large; border: 0.1px solid #000000;\">E-mail Address:</td>");
        out.println("            <td style=\"border: 0.1px solid #000000; \">");
        out.println("                <input type=\"text\" size=\"29\" name=\"email\"");
        if (form != null && form.getEmail() != null) {
            out.println(
                    "                    value=\"" + form.getEmail() + "\"");
        }
        out.println("                autofocus />");
        out.println("            </td>");
        out.println("        </tr>");
        out.println("        <tr>");
        out.println(
                "            <td style=\"font-size: x-large; border: 0.1px solid #000000;\">Password:</td>");
        out.println(
                "            <td style=\"border: 0.1px solid #000000; \"><input type=\"password\" size=\"30\" name=\"password\"/></td>");
        out.println("        </tr>");
        out.println("        <tr>");
        out.println(
                "            <td colspan=\"2\" style=\"text-align: center; border: 0.1px solid #000000;\">");
        out.println(
                "                <input type=\"submit\" name=\"button\" value=\"Login\" />");
        out.println(
                "                <input type=\"submit\" name=\"button1\" value=\"Register\"  style=\"background: none; border: none; padding: 0; display: inline; font: inherit; outline: none; color: blue; cursor: pointer; text-decoration: underline;\">");
        //out.println(
          //      " <a href="
            //    +  register() + "\"> Register </a></span> </td>");
        out.println("            </td>");
        out.println("        </tr>");
        out.println("    </table>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    
        
        if (errors != null && errors.size() > 0) {
            for (String error : errors) {
                out.println("<p style=\"font-size: large; color: red\">");
                out.println(error);
                out.println("</p>");
            }
        }
        
    }

    private void outputRegisterPage(HttpServletResponse response, RegisterForm form, List<String> errors) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();


        out.println("<!DOCTYPE html>");
        out.println("<html>");


        generateHead(out);


        out.println("<body>");
        out.println("<h2>" + servletName + " Register</h2>");


        
         
        


        // Generate an HTML <form> to get data from the user
        out.println("<form method=\"POST\">");
        out.println("    <table style=\"border: 0.5px solid #000000; \">");
        out.println("        <tr>");
        out.println(
                "            <td style=\"font-size: x-large; border: 0.1px solid #000000;\">E-mail Address:</td>");
        out.println("            <td style=\"border: 0.1px solid #000000; \">");
        out.println("                <input type=\"text\" size=\"29\" name=\"email\"");
        //  To retain values 
        if (form != null && form.getEmail() != null) {
            out.println(
                    "                    value=\"" + form.getEmail() + "\"");
        }
        
        out.println("                autofocus />");
        out.println("            </td>");
        out.println("        </tr>");
        //..........................................
        out.println("        <tr>");
        out.println(
                "            <td style=\"font-size: x-large; border: 0.1px solid #000000;\">First Name:</td>");
        out.println(
                "            <td style=\"border: 0.1px solid #000000; \"><input type=\"text\" size=\"29\" name=\"firstName\""); 
        
    //  To retain values 
        if (form != null && form.getFirstName() != null) {
            out.println(
                    "                    value=\"" + form.getFirstName() + "\"");
        }
        out.println(" /></td>");
        out.println("</tr>");
        //.........................................
        out.println("        <tr>");
        out.println(
                "            <td style=\"font-size: x-large; border: 0.1px solid #000000;\">Last Name:</td>");
        out.println(
                "            <td style=\"border: 0.1px solid #000000; \"><input type=\"text\" size=\"29\" name=\"lastName\""); 
        
    //  To retain values 
        if (form != null && form.getLastName() != null) {
            out.println(
                    "                    value=\"" + form.getLastName() + "\"");
        }
        out.println(" /></td>");
        out.println("</tr>");
        //............................................
        out.println("        <tr>");
        out.println(
                "            <td style=\"font-size: x-large; border: 0.1px solid #000000;\">Password:</td>");
        out.println(
                "            <td style=\"border: 0.1px solid #000000; \"><input type=\"password\" size=\"30\" name=\"password\" /></td>");
        out.println("        </tr>");
        //...........................................
        out.println("        <tr>");
        out.println(
                "            <td colspan=\"2\" style=\"text-align: center; border: 0.1px solid #000000;\">");
        //out.println(
          //      "                <input type=\"submit\" name=\"button\" value=\"Login\" />");
        out.println(
                "                <input type=\"submit\" name=\"button2\" value=\"Register\" />");
        out.println("            </td>");
        out.println("        </tr>");
        out.println("    </table>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    
        if (errors != null && errors.size() > 0) {
            for (String error : errors) {
                out.println("<p style=\"font-size: large; color: red\">");
                out.println(error);
                out.println("</p>");
            }
        }
   
    }



    
    private void outputToDoList(HttpServletRequest request,HttpServletResponse response)
            throws IOException {
        // Just call the version that takes a List passing an empty List
        List<String> list = new ArrayList<String>();
        outputToDoList(request,response, list);
    }

    private void outputToDoList(HttpServletRequest request,HttpServletResponse response, String message)
            throws IOException {
        // Put the message into a List and call the version that takes a List
        List<String> list = new ArrayList<String>();
        list.add(message);
        outputToDoList(request,response, list);
    }

    private void outputToDoList(HttpServletRequest request,HttpServletResponse response,
            List<String> messages) throws IOException {
        // Get the list of items to display at the end
        FavoriteBean[] beans;
        UserBean u = (UserBean)request.getSession().getAttribute("hramasub_user");
        try {
            beans = favoriteDAO.getUserFavorites(u.getId());
        } catch (RollbackException e) {
            // If there's an access error, add the message to our list of
            // messages
            messages.add(e.getMessage());
            beans = new FavoriteBean[0];
        }

        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");

        generateHead(out);
        
       
        out.println("<body>");
        out.println("<h2>Favorites for " + u.getFirstName() + " " +
                u.getLastName() + "</h2>");

        // Generate an HTML <form> to get data from the user
        out.println("<form method=\"POST\">");
        out.println("    <table style=\"border: 0.5px solid #000000; \">");
       // out.println("        <tr><td colspan=\"3\" style=\"border: 0.1px solid #000000;\"><hr/></td></tr>");
        out.println("        <tr>");
        out.println(
                "            <td style=\"font-size: large; border: 0.1px solid #000000;\">URL:</td>");
        out.println(
                "            <td colspan=\"2\" style=\"border: 0.1px solid #000000; \"><input type=\"text\" size=\"40\" name=\"url\" autofocus/></td>");
        out.println("        </tr>");
        
        out.println("        <tr>");
        out.println(
                "            <td style=\"font-size: large; border: 0.1px solid #000000;\">Comment:</td>");
        out.println(
                "            <td colspan=\"2\" style=\"border: 0.1px solid #000000; \"><input type=\"text\" size=\"40\" name=\"comment\" /></td>");
        out.println("        </tr>");
        out.println("        <tr>");
        out.println("            <td></td>");
        out.println(
                "            <td style=\"border: 0.1px solid #000000; \"><input type=\"submit\" name=\"action\" value=\"Add Favorite\"/></td>");
        out.println(
                "            <td style=\"border: 0.1px solid #000000; \"><input type=\"submit\" name=\"action\" value=\"Logout\"/></td>");
        //out.println(
          //      "            <td><input type=\"hidden\" name=\"logout\" value=\"logout\"/></td>");
        out.println("        </tr>");
       // out.println("        <tr><td colspan=\"3\" style=\"border: 0.1px solid #000000;\"><hr/></td></tr>");
        out.println("    </table>");
        out.println("</form>");

        for (String message : messages) {
            out.println("<p style=\"font-size: large; color: red\">");
            out.println(message);
            out.println("</p>");
        }

       
        out.println("<p style=\"font-size: x-large\">The list now has "
                + beans.length + " items.</p>");
        out.println("<table>");
        for (int i = 0; i < beans.length; i++) {
            
          
            
            out.println("        <td>");
            out.println("            <form name =\"fav" +beans[i].getFid()+ "\" method=\"POST\">");
            out.println(
                        "                <input type=\"hidden\" name=\"fid\"  value=\""
                                + beans[i].getFid() + "\" />");    
            out.println("    <tr>");
            //out.println("        <td></td>");
            out.println("        <td><span style=\"font-size: x-large\">"
                    + (i + 1) + ".</span><span style=\"font-size: x-large\"> <a href=\"#\" onClick =\"document.fav"+ beans[i].getFid()+".submit(); return false; \">"+  beans[i].getUrl() +"</a></span> </td>");
            out.println("    </tr>");
           
            out.println("    <tr>");
            out.println("        <td><span style=\"font-size: large\">"
                    + beans[i].getComment() + "</span> </td>");
            out.println("    </tr>");
            
            out.println("    <tr>");
            out.println("        <td><span style=\"font-size: large\">"
                    + beans[i].getClickCount() + " Clicks</span> </td>");
            out.println("    </tr>");
            
            out.println("    <tr><td></td></tr>");
            out.println("    <tr><td></td></tr>");
            
            out.println("</form>");   
        }
        
        out.println("</table>");

        out.println("</body>");
        out.println("</html>");
    }
}
