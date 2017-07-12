/**
 * Author: Jianan Ding
 * Team: 1
 */
package com.team1.webapp.task7.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import com.team1.webapp.task7.databean.CustomerBean;
import com.team1.webapp.task7.databean.DateBean;
import com.team1.webapp.task7.databean.FundBean;
import com.team1.webapp.task7.databean.FundPriceHistoryBean;
import com.team1.webapp.task7.databean.PendingBean;
import com.team1.webapp.task7.databean.PositionBean;
import com.team1.webapp.task7.databean.TransactionBean;
import com.team1.webapp.task7.model.CusDAO;
import com.team1.webapp.task7.model.DateDAO;
import com.team1.webapp.task7.model.FundDAO;
import com.team1.webapp.task7.model.FundPriceHistoryDAO;
import com.team1.webapp.task7.model.Model;
import com.team1.webapp.task7.model.PendingDAO;
import com.team1.webapp.task7.model.PositionDAO;
import com.team1.webapp.task7.model.TransactionDAO;
import com.team1.webapp.task7.utility.FundNameComparator;

public class TransitionAction extends Action {
	private CusDAO customerDAO;
	private PendingDAO pendingDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	private PositionDAO positionDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private Date date;
	private Date lastDate;
	private DateDAO dateDAO;
	
	Map<Integer, Double> map;
	
	public TransitionAction(Model model) {
		customerDAO = model.getCustomerDAO();
		pendingDAO = model.getPendingDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
		positionDAO = model.getPositionDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		dateDAO = model.getDateDAO();
		
		map = new HashMap<>();
	}
	
	@Override
	public String getName() {
		return "transitionDay.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		List<String> messages = new ArrayList<>();
		List<String> errors = new ArrayList<>();
		List<Integer> fundIds = new ArrayList<>();
		
		request.setAttribute("errors", errors);
		request.setAttribute("messages", messages);
		
		try {
			// get all the funds to update their price
			FundBean[] funds = fundDAO.getAllFunds();
			if (funds != null) {
				Arrays.sort(funds, new FundNameComparator());
				request.setAttribute("funds", funds);
			}
			
			lastDate = dateDAO.getLastDate();
			if (lastDate != null) {
				request.setAttribute("lastDate",(new SimpleDateFormat("MM/dd/yyyy").format(lastDate)));
			}
			if (request.getParameter("action") == null) {
				return "transitionDay.jsp";
			}
			String stringDate = (String) request.getParameter("date");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date startDate = sdf.parse(stringDate);
			date = new java.sql.Date(startDate.getTime());  
			
			// compare the last transaction date and input date
			if (lastDate != null && date.compareTo(lastDate) <= 0) {
				errors.add("Date must be subsequent to the last transition date");
				return "transitionDay.jsp";
			}
				
			if (funds != null) {
				for (FundBean fb : funds) {
					fundIds.add(fb.getFundid());
				}
			}
			// get request parameters with fundid, 
			// which is the name of every entry in the form
			for (int fundid : fundIds) {
				String price = (String) request.getParameter(Integer.toString(fundid));
				if (price.equals("") || price.trim().equals("")) {
					errors.add("All price fields are required");
					return "transitionDay.jsp";
				}
				Double checkPrice = Double.parseDouble(price);
				if (checkPrice < 0.1) {
					errors.add("The minimum price allowed is $ 0.1");
					return "transitionDay.jsp";
				}
				map.put(fundid, Double.parseDouble((price)));
			}
			
			// store the date of this transition day in db
			DateBean thisDate = new DateBean();
			thisDate.setDate(date);
			Transaction.begin();
			dateDAO.create(thisDate);
			Transaction.commit();
			
			processDeposit();
			processSell();
			processBuy();
			processCheckRequest();
			processFundPrice();
			
			
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		} catch (java.text.ParseException e) {
			errors.add("Date is required");
			return "transitionDay.jsp";
		} catch (NullPointerException e) {
			errors.add("All fields are required");
			return "transitionDay.jsp";}
		catch (NumberFormatException e) {
			errors.add("Price has to be a number");
			return "transitionDay.jsp";
		} finally {
			if(Transaction.isActive())
				Transaction.rollback();
		}
		messages.add("All transactions have been processed!");
		return "employeeHome.jsp";
	}
	
	/**
	 * This method processes all the check deposit requests.
	 * @throws RollbackException
	 */
	private void processDeposit() throws RollbackException {
		PendingBean[] pbs = pendingDAO.getPendingByType("Deposit");
		if (pbs == null) {
			return;
		}
		for (PendingBean deposit : pbs) {
			int customerid = deposit.getCustomerid();
			double amount = deposit.getAmount();
			CustomerBean customer = customerDAO.getCustomerById(customerid);
			
			// update the customer database
			customer.setCash(customer.getCash() + amount);
			Transaction.begin();
			customerDAO.update(customer);
			Transaction.commit();
			// insert an entry in transaction table
			TransactionBean trans = new TransactionBean();
			trans.setAmount(deposit.getAmount());
			trans.setCustomerid(customerid);
			trans.setTranstype("Deposit");
			trans.setStatus("Processed");
			trans.setExecutedate(date);
			Transaction.begin();
			transactionDAO.create(trans);
			Transaction.commit();
			
			Transaction.begin();
			pendingDAO.delete(deposit.getPendingid());
			Transaction.commit();
		}
	}
	
	/**
	 * This method processes all the check requests
	 * @throws RollbackException
	 */
	private void processCheckRequest() throws RollbackException {
		PendingBean[] pbs = pendingDAO.getPendingByType("Request Check");
		if (pbs == null) {
			return;
		}
		for (PendingBean pb : pbs) {
			int customerid = pb.getCustomerid();
			double amount = pb.getAmount();
			CustomerBean customer = customerDAO.getCustomerById(customerid);
			
			TransactionBean trans = new TransactionBean();
			trans.setAmount(pb.getAmount());
			trans.setCustomerid(customerid);
			trans.setTranstype("Request Check");
			trans.setExecutedate(date);
			
			// if the request makes the cash balance below 0, reject it
			if (customer.getCash() - amount < 0) {
				trans.setStatus("Rejected");
			} else {
				customer.setCash(customer.getCash() - amount);
				// update the customer database
				Transaction.begin();
				customerDAO.update(customer);
				Transaction.commit();
				trans.setStatus("Processed");
			}
			Transaction.begin();
			transactionDAO.create(trans);
			Transaction.commit();
			
			Transaction.begin();
			pendingDAO.delete(pb.getPendingid());
			Transaction.commit();
		}
	}
	
	/**
	 * This method
	 * @throws RollbackException
	 */
	private void processBuy() throws RollbackException {
		PendingBean[] pbs = pendingDAO.getPendingByType("Buy");
		if (pbs == null) {
			return;
		}
		for (PendingBean pb : pbs) {
			int customerid = pb.getCustomerid();
			int fundid = pb.getFundid();
			double amount = pb.getAmount();
			CustomerBean customer = customerDAO.getCustomerById(customerid);
			
			TransactionBean trans = new TransactionBean();
			trans.setCustomerid(customerid);
			trans.setTranstype("Buy");
			trans.setExecutedate(date);
			
			// if the request makes the cash balance below 0, reject it
			if (customer.getCash() - amount < 0) {
				trans.setStatus("Rejected");
			} else {
				customer.setCash(customer.getCash() - amount);
				trans.setStatus("Processed");
				// update the customer database
				Transaction.begin();
				customerDAO.update(customer);
				Transaction.commit();
				// update the position database
				double sharesBought = amount / map.get(fundid);
				trans.setShares(sharesBought);
				trans.setAmount(amount);
				trans.setFundid(fundid);
				PositionBean position = positionDAO.getPositionById(customerid, fundid);
				if (position != null) {
					position.setShares(position.getShares() + sharesBought);
					Transaction.begin();
					positionDAO.update(position);
					Transaction.commit();
				} else {
					position = new PositionBean();
					position.setCustomerid(customerid);
					position.setFundid(fundid);
					position.setShares(sharesBought);
					Transaction.begin();
					positionDAO.create(position);
				Transaction.commit();
				}
			}
			Transaction.begin();
			transactionDAO.create(trans);
			Transaction.commit();
			
			Transaction.begin();
			pendingDAO.delete(pb.getPendingid());
			Transaction.commit();
		}
	}
	
	/**
	 * This method processes all the sell requests
	 * @throws RollbackException
	 */
	private void processSell() throws RollbackException {
		PendingBean[] pbs = pendingDAO.getPendingByType("Sell");
		if (pbs == null) {
			return;
		}
		for (PendingBean pb : pbs) {
			int customerid = pb.getCustomerid();
			int fundid = pb.getFundid();
			double shares = pb.getShares();
			double price = map.get(fundid);
			
			CustomerBean customer = customerDAO.getCustomerById(customerid);
			PositionBean position = positionDAO.getPositionById(customerid, fundid);
			
			TransactionBean trans = new TransactionBean();
			trans.setCustomerid(customerid);
			trans.setTranstype("Sell");
			trans.setFundid(fundid);
			trans.setShares(shares);
			trans.setExecutedate(date);
			
			// if the customer does not own this much shares, reject it
			if (position.getShares() - shares < 0) {
				trans.setStatus("Rejected");
			} else {
				double cashEarned = shares * price;
				
				customer.setCash(customer.getCash() + cashEarned);
				trans.setStatus("Processed");
				trans.setAmount(cashEarned);
				// update the customer database
				Transaction.begin();
				customerDAO.update(customer);
				Transaction.commit();
				// update the position database
				position.setShares(position.getShares() - shares);
				Transaction.begin();
				positionDAO.update(position);
				Transaction.commit();

			}
			Transaction.begin();
			transactionDAO.create(trans);
			Transaction.commit();
			
			Transaction.begin();
			pendingDAO.delete(pb.getPendingid());
			Transaction.commit();
		}
	}
	
	/**
	 * This method maintains fund_price_history database
	 * @throws RollbackException
	 */
	private void processFundPrice() throws RollbackException {
		for (int i : map.keySet()) {
			FundPriceHistoryBean fb = new FundPriceHistoryBean();
			fb.setFundid(i);
			fb.setPrice(map.get(i));
			fb.setPricedate(date);
			Transaction.begin();
			fundPriceHistoryDAO.create(fb);
		Transaction.commit();
		}
	}
}
