package com.team1.webapp.task7.model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import com.team1.webapp.task7.databean.PendingBean;

public class PendingDAO extends GenericDAO<PendingBean> {
    public PendingDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(PendingBean.class, tableName, cp);
    }
    
    public PendingBean[] getPendingByCustomerid(int id) throws RollbackException {
    	PendingBean[] pb = match(MatchArg.equals("customerid", id));
    	return pb.length == 0 ? null : pb;
    }
    
    public PendingBean[] getPendings() throws RollbackException {
    	PendingBean[] pb = match();
    	return pb.length == 0 ? null : pb;
    }
    
    public PendingBean[] getPendingByType(String transtype) throws RollbackException {
    	PendingBean[] pb = match(MatchArg.equals("transtype", transtype));
    	return pb.length == 0 ? null : pb;
    }
}
