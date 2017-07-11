
package com.team1.webapp.task7.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import com.team1.webapp.task7.databean.CustomerBean;
import com.team1.webapp.task7.databean.FundBean;
import com.team1.webapp.task7.databean.PendingBean;
//import com.team1.webapp.task7.databean.FundBean;
//import com.team1.webapp.task7.databean.PositionBean;
import com.team1.webapp.task7.databean.TransactionBean;
//import com.team1.webapp.task7.model.CusDAO;
import com.team1.webapp.task7.model.FundDAO;
import com.team1.webapp.task7.model.FundPriceHistoryDAO;
import com.team1.webapp.task7.model.Model;
import com.team1.webapp.task7.model.PendingDAO;
import com.team1.webapp.task7.model.PositionDAO;
import com.team1.webapp.task7.model.TransactionDAO;

public class ViewTransactionHistoryAction extends Action {

	// private CusDAO customerDAO;
	private PositionDAO positionDAO;
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	private TransactionDAO transactionDAO;
	private PendingDAO pendingDAO;

	public ViewTransactionHistoryAction(Model model) {
		// customerDAO = model.getCustomerDAO();
		positionDAO = model.getPositionDAO();
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
		transactionDAO = model.getTransactionDAO();
		pendingDAO = model.getPendingDAO();
	}

	public String getName() {
		return "viewTransactionHistory.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		List<List<String>> TransactionList = new ArrayList<>();
		DecimalFormat sharesFormat = new DecimalFormat("#,##0.000");
		DecimalFormat priceFormat = new DecimalFormat("#,##0.00");

		try {
			int customerid = customer.getCustomerid();
			TransactionBean[] completedTransactions = transactionDAO.getTransByCusId(customerid);

			PendingBean[] pendingTransactions = pendingDAO.getPendingByCustomerid(customerid);

			if (pendingTransactions == null && completedTransactions == null) {
				errors.add("No transaction history!");
				return "viewTransactionHistory.jsp";
			}

			if (pendingTransactions != null) {

				for (int i = pendingTransactions.length - 1; i >= 0; i--) {
					List<String> transactionHistory = new ArrayList<>();

					String operationType = pendingTransactions[i].getTranstype();
					int fundid = pendingTransactions[i].getFundid();
					FundBean fb = fundDAO.getFundById(fundid);
					String fundName = "--";
					String fundSymbol = "--";
					if (fb != null) {
						fundName = fb.getName();
						fundSymbol = fb.getSymbol();
					}
					double fundShares = pendingTransactions[i].getShares();

					double amount = pendingTransactions[i].getAmount();

					transactionHistory.add("Pending"); // Date
					transactionHistory.add(operationType);
					if (operationType.equals("Request Check") || operationType.equals("Deposit")) {

						transactionHistory.add("--"); // FundName
						transactionHistory.add("--"); // FundSymbol
						transactionHistory.add("--"); // # of shares
						transactionHistory.add("--"); // share price
					} else {
						transactionHistory.add(fundName);
						transactionHistory.add(fundSymbol);

						if (operationType.equals("Buy")) {
							transactionHistory.add("--"); // # of shares
						} else {
							transactionHistory.add(sharesFormat.format(fundShares));
						}

						transactionHistory.add("--"); // Transition Day Price
					}

					if (operationType.equals("Sell")) {
						transactionHistory.add("--"); // amount
					} else {
						transactionHistory.add(priceFormat.format(amount));
					}

					TransactionList.add(transactionHistory);
				}
			}

			if (completedTransactions != null) {

				for (int i = completedTransactions.length - 1; i >= 0; i--) {
					List<String> transactionHistory = new ArrayList<>();
					SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
					String transactionDate = sdf.format(completedTransactions[i].getExecutedate());
					String operationType = completedTransactions[i].getTranstype();
					if (operationType.equals("Request Check") || operationType.equals("Deposit")) {
						transactionHistory.add(transactionDate); // date
						transactionHistory.add(operationType); // type
						transactionHistory.add("--"); // name
						transactionHistory.add("--"); // symbol
						transactionHistory.add("--"); // shares
						transactionHistory.add("--"); // price
						transactionHistory.add(priceFormat.format(completedTransactions[i].getAmount())); // amount

						TransactionList.add(transactionHistory);
					} else {
						int fundid = completedTransactions[i].getFundid();
						FundBean fb = fundDAO.getFundById(fundid);
						String fundName = "--";
						String fundSymbol = "--";
						if (fb != null) {
							fundName = fb.getName();
							fundSymbol = fb.getSymbol();
						}

						double fundShares = completedTransactions[i].getShares();
						double price = fundPriceHistoryDAO.getPrice(fundid, completedTransactions[i].getExecutedate());
						double amount = completedTransactions[i].getAmount();

						transactionHistory.add(transactionDate);
						transactionHistory.add(operationType);
						transactionHistory.add(fundName);
						transactionHistory.add(fundSymbol);
						transactionHistory.add(sharesFormat.format(fundShares));
						transactionHistory.add(priceFormat.format(price));
						transactionHistory.add(priceFormat.format(amount));
						TransactionList.add(transactionHistory);
					}

				}

			}
			request.setAttribute("transactionHistoryList", TransactionList);

			return "viewTransactionHistory.jsp";
		} catch (RollbackException e) {
			errors.add(e.getMessage());
			return "error.jsp";
		}
	}
}
