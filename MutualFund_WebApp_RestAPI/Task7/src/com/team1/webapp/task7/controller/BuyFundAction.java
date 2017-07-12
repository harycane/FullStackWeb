package com.team1.webapp.task7.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.team1.webapp.task7.databean.CustomerBean;
import com.team1.webapp.task7.databean.FundBean;
import com.team1.webapp.task7.databean.PendingBean;
import com.team1.webapp.task7.formbean.BuyFundForm;
import com.team1.webapp.task7.model.CusDAO;
import com.team1.webapp.task7.model.FundDAO;
import com.team1.webapp.task7.model.Model;
import com.team1.webapp.task7.model.PendingDAO;
import com.team1.webapp.task7.utility.FundNameComparator;

public class BuyFundAction extends Action {
    private FormBeanFactory<BuyFundForm> formBeanFactory = FormBeanFactory.getInstance(BuyFundForm.class);
    private FundDAO fundDAO;
    private PendingDAO pendingDAO;
    private CusDAO customerDAO;
    private double balance;
    
    public BuyFundAction(Model model) {
    	fundDAO = model.getFundDAO();
    	pendingDAO = model.getPendingDAO();
    	customerDAO = model.getCustomerDAO();
    }

    public String getName() {
        return "buyFund.do";
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
            BuyFundForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            FundBean[] funds = fundDAO.getAllFunds();
			if (funds != null) {
				Arrays.sort(funds, new FundNameComparator());
			}
            
            request.setAttribute("funds", funds);
        	
            // in case that the db has been updated
            int customerid = customer.getCustomerid();
            customer = customerDAO.getCustomerById(customerid);
            double cash = customer.getCash();
            
        	// calculate customer's available balance
        	PendingBean[] pbs = pendingDAO.getPendingByCustomerid(customer.getCustomerid());
        	
        	if (pbs != null && pbs.length != 0) {
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
                return "buyFund.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "buyFund.jsp";
            }
            
            if (form.getAction().equals("Buy")) {
            	String symbol = request.getParameter("symbol");
            	
            	if (fundDAO.getFundBySymbol(symbol) == null) {
            		errors.add("Fund symbol does not exist");
            		return "buyFund.jsp";
            	}
     
            	balance = cash - Double.parseDouble(form.getAmount());
            	if (balance < 0) {
            		errors.add("Not enough balance");
            		return "buyFund.jsp";
            	}
            	
            	PendingBean pb = new PendingBean();
            	pb.setAmount(Double.parseDouble(form.getAmount()));
            	pb.setCustomerid(customer.getCustomerid());
            	pb.setTranstype("Buy");
            	pb.setFundid(fundDAO.getFundBySymbol(symbol).getFundid());
            	Transaction.begin();
            	pendingDAO.create(pb);
            	Transaction.commit();
            }
            
            messages.add("Your transaction order (Buy Fund) has been queued up!");
            return "customerHome.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } finally {
        	if (Transaction.isActive())
        		Transaction.rollback();
        }
       
    }
}
