package com.team1.webapp.task7.model;

import java.sql.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.RollbackException;

import com.team1.webapp.task7.databean.DateBean;

public class DateDAO extends GenericDAO<DateBean> {
    public DateDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(DateBean.class, tableName, cp);
    }
    
    public Date getLastDate() throws RollbackException {
    	DateBean[] db = match();
    	if (db.length == 0) {
    		return null;
    	}
    	return db[db.length - 1].getDate();
    }
}
