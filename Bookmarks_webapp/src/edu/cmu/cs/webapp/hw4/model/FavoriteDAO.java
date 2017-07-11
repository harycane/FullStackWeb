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


import edu.cmu.cs.webapp.hw4.databean.FavoriteBean;

public class FavoriteDAO extends GenericDAO<FavoriteBean> {
	public FavoriteDAO(ConnectionPool cp, String tableName) throws DAOException {
		super(FavoriteBean.class, tableName, cp);
	}

	 public void createFavorite(FavoriteBean item) throws RollbackException {
	        try {
	            Transaction.begin();

	            // Get item at bottom of list
	           // FavoriteBean[] a = match(MatchArg.max("position"));

	            FavoriteBean[] a = match(MatchArg.equals("owner", item.getOwner()));
	            int maxPos = 0;
	            for (FavoriteBean p : a) {
	                if (p.getPosition() > maxPos) {
	                    maxPos = p.getPosition();
	                }
	            }
	            
	            
	            item.setPosition(maxPos + 1);
	            super.create(item);
	            Transaction.commit();
	        } finally {
	            if (Transaction.isActive()) Transaction.rollback();
	        }
	 }
	    public  FavoriteBean[] getUserFavorites(String owner) throws RollbackException {

	        // Calls GenericDAO's match() method.
	        // This no match constraint arguments, match returns all the Item beans
	        FavoriteBean[] items = match(MatchArg.equals("owner",owner));
	        
	     //   Arrays.sort(items);//(items, (FavoriteBean i1, FavoriteBean i2) -> i1.getPosition()
	          //      - i2.getPosition());

	        return items;
	    }

	    public  void IncrementCount(int fid) throws RollbackException {
	     try {
	                Transaction.begin();
	                FavoriteBean f  = read(fid); 
	                
	                Integer count = f.getClickCount();
	                count = count+1;
	                f.setClickCount(count);
	                update(f);
	                
	                Transaction.commit();
	         } finally {
	             if (Transaction.isActive())
	                 Transaction.rollback();
	         }
	    }

	    public void delete(int id, String owner) throws RollbackException {
	        try {
	            Transaction.begin();
	            FavoriteBean p = read(id);

	            if (p == null) {
	                throw new RollbackException("Favorite Link does not exist: id=" + id);
	            }

	            if (!owner.equals(p.getOwner())) {
	                throw new RollbackException("Favorite Link not owned by " + owner);
	            }

	            delete(id);
	            Transaction.commit();
	        } finally {
	            if (Transaction.isActive()) Transaction.rollback();
	        }
	    }

}
