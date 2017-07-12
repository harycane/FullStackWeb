package com.team1.webapp.task7.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.team1.webapp.task7.databean.CustomerBean;
import com.team1.webapp.task7.databean.FundBean;
import com.team1.webapp.task7.databean.PendingBean;
import com.team1.webapp.task7.databean.PositionBean;
import com.team1.webapp.task7.formbean.SellFundForm;
import com.team1.webapp.task7.model.FundDAO;
import com.team1.webapp.task7.model.FundPriceHistoryDAO;
import com.team1.webapp.task7.model.Model;
import com.team1.webapp.task7.model.PendingDAO;
import com.team1.webapp.task7.model.PositionDAO;
import com.team1.webapp.task7.utility.FundListComparator;

public class SellFundAction extends Action {

	private FormBeanFactory<SellFundForm> sellFundFormFactory = FormBeanFactory.getInstance(SellFundForm.class);

	private PendingDAO pendingDAO;
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;

	public SellFundAction(Model model) {
		pendingDAO = model.getPendingDAO();
		fundDAO = model.getFundDAO();
		positionDAO = model.getPositionDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}

	public String getName() {
		return "sellFund.do";
	}

	public String perform(HttpServletRequest request) {
		DecimalFormat sharesFormat = new DecimalFormat("#,##0.000");
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
			// Fetch the items now, so that in case there is no form or there
			// are errors
			// We can just dispatch to the JSP to show the item list (and any
			// errors)
			CustomerBean customer = (CustomerBean) request.getSession().getAttribute("customer");

			SellFundForm form = sellFundFormFactory.create(request); // SellFundForm
																		// need
																		// to be
																		// implemented
			int customerid = customer.getCustomerid();
			PositionBean[] positions = positionDAO.getPositionsOfCustomer(customerid);
			List<List<String>> fundsList = new ArrayList<>();

			if (positions != null) {
				for (int i = 0; i < positions.length; i++) {
					List<String> fundInfo = new ArrayList<>();
					int fundid = positions[i].getFundid();
					String fundSymbol = fundDAO.getFundsSymbol(fundid);
					double fundShares = positionDAO.getSharesOfPosition(customerid, fundid);
					double price = fundPriceHistoryDAO.getPriceOfLastDay(fundid);
					double value = fundShares * price;

					fundInfo.add(fundSymbol);
					fundInfo.add(sharesFormat.format(fundShares));
					fundInfo.add(sharesFormat.format(value));
					fundsList.add(fundInfo);
				}
			}
			Collections.sort(fundsList, new FundListComparator());
			request.setAttribute("fundsList", fundsList);

			if (!form.isPresent()) {
				return "sellFund.jsp";
			}
			request.setAttribute("form", form);
			errors.addAll(form.getValidationErrors());

			if (errors.size() > 0) {
				return "sellFund.jsp";
			}

			if (form.getAction().equals("Sell")) {
				String symbol = request.getParameter("symbol");
				FundBean fund = fundDAO.getFundBySymbol(symbol); // FundDAO
																			// should
																			// contain
																			// a
																			// getFund()
																			// method;
				if (fund == null) {
					errors.add("Fund not found");
					return "sellFund.jsp";
				}

				PositionBean[] p = positionDAO.getPositionsOfCustomer(customer.getCustomerid());
				if (p == null || p.length == 0) {
					errors.add("You don't own this fund");
					return "sellFund.jsp";
				}

				double formshares = 0.0;
				try {
					formshares = Double.parseDouble(form.getShares());
					if (formshares < 0.001) {
						errors.add("The minimum shares allowed is 0.001");
						return "sellFund.jsp";
					}
				} catch (Exception e) {
					errors.add("Shares should be specified as a number");
					return "sellFund.jsp";
				}

				double shares = positionDAO.getSharesOfPosition(customer.getCustomerid(), fund.getFundid());
				if (shares == 0.0) {
					errors.add("You don't own this fund");
					return "sellFund.jsp";
				}

				PendingBean[] pb = pendingDAO.getPendingByCustomerid(customer.getCustomerid());
				if (pb != null) {
					for (int j = 0; j < pb.length; j++) {
						if (pb[j].getTranstype().equals("Sell") && pb[j].getFundid() == fund.getFundid()) {
							shares -= pb[j].getShares();
						}
					}
				}
				double sharesRemain = shares - formshares;
				if (sharesRemain < 0) {
					errors.add("You don't have enough shares to sell");
					return "sellFund.jsp";
				} else {
					PendingBean bean = new PendingBean();
					bean.setCustomerid(customer.getCustomerid());
					bean.setTranstype("Sell");
					bean.setShares(formshares);
					bean.setFundid(fund.getFundid());
					Transaction.begin();
					pendingDAO.create(bean);
					Transaction.commit();
					List<String> messages = new ArrayList<String>();
					messages.add("Your transaction order has been queued up!");
					request.setAttribute("messages", messages);
				}
			}
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
