package com.team1.webapp.task7.model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.RollbackException;

import com.team1.webapp.task7.databean.EmployeeBean;

public class Model {
	private EmployeeDAO employeeDAO;
	private CusDAO customerDAO;
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;
	private PositionDAO positionDAO;
	private TransactionDAO transactionDAO;
	private PendingDAO pendingDAO;
	private DateDAO dateDAO;
	
	public Model(ServletConfig config) throws ServletException {
		try {
			String jdbcDriver = config.getInitParameter("jdbcDriverName");
			String jdbcURL    = config.getInitParameter("jdbcURL");
			
			ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);
			
			employeeDAO  = new EmployeeDAO(pool, "employee");
			customerDAO = new CusDAO(pool, "customer");
			fundDAO = new FundDAO(pool, "fund");
			fundPriceHistoryDAO = new FundPriceHistoryDAO(pool, "fund_price_history");
			positionDAO = new PositionDAO(pool, "position");
			transactionDAO = new TransactionDAO(pool, "transaction");
			pendingDAO = new PendingDAO(pool, "pending_transaction");
			dateDAO = new DateDAO(pool, "date");
			
			generateInitEmployee();
		} catch (DAOException e) {
			throw new ServletException(e);
		} 
	}

	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}

	public CusDAO getCustomerDAO() {
		return customerDAO;
	}
	
	public FundDAO getFundDAO() {
		return fundDAO;
	}

	public FundPriceHistoryDAO getFundPriceHistoryDAO() {
		return fundPriceHistoryDAO;
	}

	public PositionDAO getPositionDAO() {
		return positionDAO;
	}

	public TransactionDAO getTransactionDAO() {
		return transactionDAO;
	}
	
	public PendingDAO getPendingDAO() {
		return pendingDAO;
	}
	
	public DateDAO getDateDAO() {
		return dateDAO;
	}
	
	private void generateInitEmployee() {
		try {
			EmployeeBean[] employees = employeeDAO.match();
			if (employees.length == 0) {
				EmployeeBean initEmployee = new EmployeeBean();
				initEmployee.setFirstname("Admin");
				initEmployee.setLastname("Root");
				initEmployee.setUsername("admin");
				initEmployee.setPassword("admin");
				employeeDAO.create(initEmployee);
			}
		} catch (RollbackException e) {
			
		}
	}
}
