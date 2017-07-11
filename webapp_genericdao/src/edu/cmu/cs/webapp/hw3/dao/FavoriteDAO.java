/* @author HaryKrishnan Ramasubramanian.
 * 25 November 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw3.dao;

//import java.util.Arrays;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import edu.cmu.cs.webapp.hw3.databean.FavoriteBean;

public class FavoriteDAO extends GenericDAO<FavoriteBean> {
    public FavoriteDAO(ConnectionPool cp, String tableName) throws DAOException {
        super(FavoriteBean.class, tableName, cp);
    }

    public void createFavorite(FavoriteBean item) throws RollbackException {
        try {
            Transaction.begin();

            // Get item at bottom of list
            FavoriteBean[] a = match(MatchArg.max("position"));

            FavoriteBean bottomBean;
            if (a.length == 0) {
                bottomBean = null;
            } else {
                bottomBean = a[0];
            }

            int newPos;
            if (bottomBean == null) {
                // List is empty...just add it with position = 1
                newPos = 1;
            } else {
                // New item's position is one less than the top bean's position
                newPos = bottomBean.getPosition() + 1;
            }

            item.setPosition(newPos);

            // Create a new ItemBean in the database with the next id number
            // Note that GenericDAO.create() will use auto-increment when
            // the primary key field is an int or a long.
            create(item);

            Transaction.commit();
        } finally {
            if (Transaction.isActive())
                Transaction.rollback();
        }
    }

    public  FavoriteBean[] getUserFavorites(int id) throws RollbackException {

        // Calls GenericDAO's match() method.
        // This no match constraint arguments, match returns all the Item beans
        FavoriteBean[] items = match(MatchArg.equals("id",id));
        
        //Arrays.sort(items, (FavoriteBean i1, FavoriteBean i2) -> i1.getPosition()
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
}
