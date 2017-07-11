/* 
 * @author Jianan Ding.
 */
package com.team1.webapp.task7.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import com.team1.webapp.task7.databean.CustomerBean;
import com.team1.webapp.task7.databean.PendingBean;
//import com.team1.webapp.task7.databean.FundBean;
import com.team1.webapp.task7.databean.PositionBean;
import com.team1.webapp.task7.model.CusDAO;
import com.team1.webapp.task7.model.FundDAO;
import com.team1.webapp.task7.model.FundPriceHistoryDAO;
import com.team1.webapp.task7.model.Model;
import com.team1.webapp.task7.model.PendingDAO;
import com.team1.webapp.task7.model.PositionDAO;
import com.team1.webapp.task7.model.TransactionDAO;
import com.team1.webapp.task7.utility.FundListComparator;

public class ViewCustomerAccountAction extends Action {
	
	private PendingDAO pendingDAO;
	private CusDAO customerDAO;
	private PositionDAO positionDAO;
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
    private TransactionDAO transactionDAO;
	public ViewCustomerAccountAction(Model model) {
    	customerDAO = model.getCustomerDAO();
    	positionDAO = model.getPositionDAO();
    	fundDAO     = model.getFundDAO();
    	fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
    	transactionDAO = model.getTransactionDAO();
    	pendingDAO = model.getPendingDAO();
	}
	

	public String getName() {
		return "viewacct.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");

        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        List<List<String>> fundsList = new ArrayList<>();
        DecimalFormat sharesFormat = new DecimalFormat("#,##0.000");
        DecimalFormat cashFormat = new DecimalFormat("#,##0.00");
    	
		try {
			// in case that the db has been updated
			int customerid = customer.getCustomerid();
			customer = customerDAO.getCustomerById(customerid);
        	PositionBean[] positions = positionDAO.getPositionsOfCustomer(customerid);
        	
        	// calculate customer's available balance
        	double cash = customer.getCash();
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
        	Date date = transactionDAO.getLastTradingDate(customerid);
        	if (date != null) {
	        	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	        	String lastdate = df.format(date);
	        	request.setAttribute("lastdate", lastdate); //last trading date
        	}
        	if (positions == null) {
        		return "viewacct.jsp";
        	}
        	
        	for(int i = 0; i < positions.length; i++) {
        		List<String> fundInfo = new ArrayList<>();
        		int fundid = positions[i].getFundid();
        		String fundSymbol = fundDAO.getFundsSymbol(fundid);
        		String fundName = fundDAO.getFundById(fundid).getName();
        		double fundShares = positionDAO.getSharesOfPosition(customerid, fundid);
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
        	return "viewacct.jsp";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        }
    }
}
