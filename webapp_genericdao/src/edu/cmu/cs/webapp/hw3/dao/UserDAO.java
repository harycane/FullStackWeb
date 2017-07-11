/* @author HaryKrishnan Ramasubramanian.
 * 25 November 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw3.dao;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import edu.cmu.cs.webapp.hw3.databean.UserBean;

public class UserDAO extends GenericDAO<UserBean> {
    public UserDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(UserBean.class, tableName, cp);
    }
    
    public void createNewUser(UserBean u) throws RollbackException {
 try{
        Transaction.begin();
        create(u);
        Transaction.commit();
    
 } finally {
        if (Transaction.isActive())
            Transaction.rollback();
    }
}
    
    public UserBean readExistingUser(String email) throws RollbackException {
        UserBean[] a = match(MatchArg.equals("email", email));
        if(a.length>0)
            return a[0];
        else
            return null;
    }
    
}