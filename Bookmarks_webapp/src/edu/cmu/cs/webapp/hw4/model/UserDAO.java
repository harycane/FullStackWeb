/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw4.model;

//import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

//import databeans.User;
import edu.cmu.cs.webapp.hw4.databean.UserBean;

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
	        
	        public UserBean[] getUsers() throws RollbackException {
	            UserBean[] users = match();
	          //  Arrays.sort(users); // We want them sorted by last and first names (as per User.compareTo());
	            return users;
	        }

	        public UserBean setPassword(String email , String password) throws RollbackException {
	            try {
	                Transaction.begin();
	                UserBean dbUser = readExistingUser(email);

	                if (dbUser == null) {
	                    throw new RollbackException("User " + email + " no longer exists");
	                }

	                dbUser.encodePassword(password);

	                update(dbUser);
	                Transaction.commit();
	                
	                return dbUser;
	            } finally {
	                if (Transaction.isActive()) Transaction.rollback();
	            }
	        }

}
