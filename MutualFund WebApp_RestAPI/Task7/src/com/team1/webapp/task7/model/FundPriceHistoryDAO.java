package com.team1.webapp.task7.model;

import java.sql.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import com.team1.webapp.task7.databean.FundPriceHistoryBean;

public class FundPriceHistoryDAO extends GenericDAO<FundPriceHistoryBean> {
	public FundPriceHistoryDAO(ConnectionPool cp, String tableName)
			throws DAOException {
		super(FundPriceHistoryBean.class, tableName, cp);
	}
	
	public double getPriceOfLastDay(int fundid) throws RollbackException {
		 FundPriceHistoryBean[] history = match(MatchArg.equals("fundid", fundid));
		 if (history.length == 0) {
	        	return 0.0;
	        }
	     return history[history.length - 1].getPrice();
	}
	
	public double getPrice(int fundid, Date pricedate) throws RollbackException {
		FundPriceHistoryBean[] history = match(MatchArg.equals("fundid", fundid),
				MatchArg.equals("pricedate", pricedate));
		return history.length == 0 ? 0.0 : history[0].getPrice();
	}
	
	public FundPriceHistoryBean[] getFundPriceHistory(int id) throws RollbackException {
		FundPriceHistoryBean[] history = match(MatchArg.equals("fundid", id));
		return history;
				
	}
	
	public Date getLastDate() throws RollbackException {
		FundPriceHistoryBean[] history = match();
		if (history.length != 0) {
			return history[history.length - 1].getPricedate();
		}
		return null;
	}
}
