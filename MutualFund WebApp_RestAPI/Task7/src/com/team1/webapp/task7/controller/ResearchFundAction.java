package com.team1.webapp.task7.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import com.team1.webapp.task7.databean.FundBean;
import com.team1.webapp.task7.databean.ResearchFundBean;
import com.team1.webapp.task7.model.FundDAO;
import com.team1.webapp.task7.model.FundPriceHistoryDAO;
import com.team1.webapp.task7.model.Model;
import com.team1.webapp.task7.utility.FundNameComparator;

public class ResearchFundAction extends Action {
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;

    public ResearchFundAction(Model model) {
    	fundDAO     = model.getFundDAO();
    	fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
    }
    
    public String getName() {
        return "researchFund.do";
    }
    public String perform(HttpServletRequest request) {
    	//System.out.println("Reached here");
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
            FundBean[] funds = fundDAO.getAllFunds();
            if (funds != null) {
            	Arrays.sort(funds, new FundNameComparator());
	            ResearchFundBean[] history = new ResearchFundBean[funds.length];
	            for (int i=0; i < history.length; i++) {
	            	ResearchFundBean h = new ResearchFundBean();
	            	h.setFundid(funds[i].getFundid());
	            	h.setName(funds[i].getName());
	            	h.setSymbol(funds[i].getSymbol());
	            	h.setHistory(fundPriceHistoryDAO.getFundPriceHistory(funds[i].getFundid()));
	            	history[i] = h;
	            }
	            request.setAttribute("hlist", history);
            }
            
            return "researchFund.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } 
    }
}
