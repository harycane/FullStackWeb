/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package com.team1.webapp.task7.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import com.team1.webapp.task7.databean.CustomerBean;
//import com.team1.webapp.task7.databean.FundBean;
import com.team1.webapp.task7.databean.PositionBean;
import com.team1.webapp.task7.model.CusDAO;
import com.team1.webapp.task7.model.FundDAO;
import com.team1.webapp.task7.model.FundPriceHistoryDAO;
import com.team1.webapp.task7.model.Model;
import com.team1.webapp.task7.model.PositionDAO;
import com.team1.webapp.task7.model.TransactionDAO;
import com.team1.webapp.task7.utility.FundListComparator;

public class ViewCustAccountByEmpAction extends Action {
	
	private CusDAO customerDAO;
	private PositionDAO positionDAO;
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
    private TransactionDAO transactionDAO;
	public ViewCustAccountByEmpAction(Model model) {
    	customerDAO = model.getCustomerDAO();
    	positionDAO = model.getPositionDAO();
    	fundDAO     = model.getFundDAO();
	    fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	    transactionDAO = model.getTransactionDAO();
	}
	

	public String getName() { return "viewacctbyemp.do"; }

	public String perform(HttpServletRequest request) {
        // Set up the errors list
        List<String> errors = new ArrayList<String>();
        List<List<String>> fundsList = new ArrayList<>();
        DecimalFormat sharesFormat = new DecimalFormat("#,##0.000");
        DecimalFormat cashFormat = new DecimalFormat("#,##0.00");

		try {
			if(request.getParameter("customerId")!=null) {
				

				if(request.getParameter("customerId").trim()=="") {

				errors.add("Customer Does Not Exist");
				request.setAttribute("errors",errors);
				return "viewAcctByEmp.jsp";
			}
			}
			
			int id = Integer.parseInt(request.getParameter("customerId"));
		    CustomerBean customer = customerDAO.getCustomerById(id);
		    if(customer==null) {
				errors.add("Customer Does Not Exist");
				request.setAttribute("errors",errors);
				return "viewAcctByEmp.jsp";
			}
		    request.setAttribute("custViewed", customer);
			PositionBean[] positions = positionDAO.getPositionsOfCustomer(id);
	    	double cash = customer.getCash();
	    	String cashBalance = cashFormat.format(cash);
	    	request.setAttribute("cashBalance", cashBalance);
	    	
        	Date date = transactionDAO.getLastTradingDate(id);
        	if (date != null) {
	        	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	        	String lastdate = df.format(date);
	        	request.setAttribute("lastdate", lastdate); //last trading date
        	}
        	
        	if (positions == null) {
        		return "viewAcctByEmp.jsp";
        	}
        	
        	for(int i = 0; i < positions.length; i++) {
        		List<String> fundInfo = new ArrayList<>();
        		int fundid = positions[i].getFundid();
        		String fundSymbol = fundDAO.getFundsSymbol(fundid);
        		String fundName = fundDAO.getFundById(fundid).getName();
        		double fundShares = positionDAO.getSharesOfPosition(id, fundid);
        		double price = fundPriceHistoryDAO.getPriceOfLastDay(fundid);
        		double value = fundShares * price;
        		
        		fundInfo.add(fundName);
	        	fundInfo.add(fundSymbol);
	        	fundInfo.add(sharesFormat.format(fundShares));
	        	fundInfo.add(sharesFormat.format(value));
	        	fundsList.add(fundInfo);
        	}
        	Collections.sort(fundsList, new FundListComparator());
 	      	request.setAttribute("fundsList", fundsList);
 	        
        	return "viewAcctByEmp.jsp";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (NumberFormatException e) {
        	errors.add("Customer not found");
        	return "error.jsp";
        }
    }
}
