package com.team1.webapp.task7.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.team1.webapp.task7.databean.CustomerBean;
import com.team1.webapp.task7.databean.PendingBean;
import com.team1.webapp.task7.formbean.CheckRequestForm;
import com.team1.webapp.task7.model.CusDAO;
import com.team1.webapp.task7.model.Model;
import com.team1.webapp.task7.model.PendingDAO;

public class RequestCheckAction extends Action {
    private FormBeanFactory<CheckRequestForm> formBeanFactory = FormBeanFactory.getInstance(CheckRequestForm.class);
    private PendingDAO pendingDAO;
    private CusDAO customerDAO;
    private double balance;
    
    public RequestCheckAction(Model model) {
    	pendingDAO = model.getPendingDAO();
    	customerDAO = model.getCustomerDAO();
    }

    public String getName() {
        return "requestCheck.do";
    }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        DecimalFormat cashFormat = new DecimalFormat("#,##0.00");
        
        CustomerBean customer = (CustomerBean) session.getAttribute("customer");
        
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        List<String> messages = new ArrayList<>();
        request.setAttribute("messages", messages);
        
        try {
            CheckRequestForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            // in case that the db has been updated
            int customerid = customer.getCustomerid();
			customer = customerDAO.getCustomerById(customerid);
			double cash = customer.getCash();
			
            // calculate customer's available balance
        	PendingBean[] pbs = pendingDAO.getPendingByCustomerid(customer.getCustomerid());
        	if (pbs != null) {
				for (int i = 0; i < pbs.length; i++) {
					PendingBean temp = pbs[i];
					String transType = temp.getTranstype();
					if (transType.equals("Buy") || transType.equals("Request Check")) {
						cash -= temp.getAmount();
					}
            		
            	}
        	} 
            request.setAttribute("cash", cashFormat.format(cash));
            
            if (!form.isPresent()) {
                return "requestCheck.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "requestCheck.jsp";
            }
            
            if (form.getAction().equals("Request")) {
            	balance = cash - Double.parseDouble(form.getAmount());
            	if (balance < 0) {
            		errors.add("Not enough balance");
            		return "requestCheck.jsp";
            	}
            	
            	PendingBean pb = new PendingBean();
            	pb.setAmount(Double.parseDouble(form.getAmount()));
            	pb.setCustomerid(customer.getCustomerid());
            	pb.setTranstype("Request Check");
            	Transaction.begin();
            	pendingDAO.create(pb);
            	Transaction.commit();
            }
            
            messages.add("Your transaction order (Check Request) has been queued up!");
            return "customerHome.jsp";
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
