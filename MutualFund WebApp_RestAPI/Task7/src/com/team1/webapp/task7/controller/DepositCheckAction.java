package com.team1.webapp.task7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.team1.webapp.task7.databean.CustomerBean;
import com.team1.webapp.task7.databean.PendingBean;
import com.team1.webapp.task7.formbean.DepositCheckForm;
import com.team1.webapp.task7.model.CusDAO;
import com.team1.webapp.task7.model.Model;
import com.team1.webapp.task7.model.PendingDAO;

public class DepositCheckAction extends Action {
    private FormBeanFactory<DepositCheckForm> formBeanFactory = FormBeanFactory.getInstance(DepositCheckForm.class);
    private PendingDAO pendingDAO;
    private CusDAO customerDAO;
    
    public DepositCheckAction(Model model) {
    	pendingDAO = model.getPendingDAO();
    	customerDAO = model.getCustomerDAO();
    }

    public String getName() {
        return "depositCheck.do";
    }

    public String perform(HttpServletRequest request) {
    	String username = request.getParameter("username");
    	
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        List<String> messages = new ArrayList<>();
        request.setAttribute("messages", messages);
    
        try {
            DepositCheckForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            if (username != null) {
            	form.setUsername(username);
            }
            
            if (request.getParameter("action") == null) {
                return "depositCheck.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "depositCheck.jsp";
            }
            
            if (form.getAction().equals("Deposit")) {
            	PendingBean pb = new PendingBean();
            	CustomerBean cb = new CustomerBean();
            	cb = customerDAO.getCustomerByUserName(username);
            	if (cb == null) {
            		errors.add("Customer does not exist");
            		return "depositCheck.jsp";
            	}
            	pb.setAmount(Double.parseDouble(form.getAmount()));
            	pb.setCustomerid(cb.getCustomerid());
            	pb.setTranstype("Deposit");
            	Transaction.begin();
            	pendingDAO.create(pb);
            	Transaction.commit();
            }
            messages.add("Your Transaction order (Check Deposit) has been queued up!");
            return "employeeHome.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } finally {
        	if(Transaction.isActive())
        		Transaction.rollback();
        }
    }
}
