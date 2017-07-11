/* @author HaryKrishnan Ramasubramanian.
 * 13 December 2016.
 * 08-672. */
package edu.cmu.cs.webapp.hw4.model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

import org.mybeans.factory.BeanTable;



public class Model {
	private FavoriteDAO favoriteDAO;
	private UserDAO userDAO;

	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);
			
			userDAO  = new UserDAO(pool, "hramasub_user");
			favoriteDAO = new FavoriteDAO(pool, "hramasub_favorite");
			BeanTable.useJDBC(jdbcDriver,jdbcURL);
			
		} catch (DAOException e) {
			throw new ServletException(e);
		} 
	}
	
	public FavoriteDAO getFavoriteDAO()  { return favoriteDAO; }
	public UserDAO getUserDAO()  { return userDAO; }
}
