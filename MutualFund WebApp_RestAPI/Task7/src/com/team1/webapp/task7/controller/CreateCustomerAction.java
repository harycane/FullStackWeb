package com.team1.webapp.task7.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.team1.webapp.task7.databean.CustomerBean;
import com.team1.webapp.task7.databean.EmployeeBean;
import com.team1.webapp.task7.databean.PendingBean;
import com.team1.webapp.task7.formbean.CreateCustomerForm;
import com.team1.webapp.task7.model.CusDAO;
import com.team1.webapp.task7.model.Model;
import com.team1.webapp.task7.model.PendingDAO;
import com.team1.webapp.task7.utility.CustomerNameComparator;
import com.team1.webapp.task7.utility.EmpNameComparator;


public class CreateCustomerAction extends Action {
    private FormBeanFactory<CreateCustomerForm> formBeanFactory = FormBeanFactory.getInstance(CreateCustomerForm.class);

    private CusDAO customerDAO;
    private PendingDAO pendingDAO;
    public CreateCustomerAction(Model model) {
        customerDAO = model.getCustomerDAO();
        pendingDAO = model.getPendingDAO();
    }

    public String getName() {
        return "createCustomer.do";
    }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
        	request.setAttribute("customerList", customerDAO.getCustomers());
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        }

        try {
            CreateCustomerForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);
             CustomerBean[] cusList = customerDAO.getCustomers();
			if (cusList != null) {
				Arrays.sort(cusList, new CustomerNameComparator());
			}
            request.setAttribute("cusList", cusList);

            // If no params were passed, return with no errors so that the form will be
            // presented (we assume for the first time).
            if (!form.isPresent()) {
                return "createCustomer.jsp";
            }

            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "createCustomer.jsp";
            }

            if (form.getAction().equals("Create")) {
            	CustomerBean[] customers = customerDAO.match(MatchArg.equals("username", form.getUsername()));
            	if (customers.length != 0) {
            		errors.add("Username already exits");
            		return "createCustomer.jsp";
            	}
                CustomerBean newCustomer = new CustomerBean();
                newCustomer.setUsername(form.getUsername());
                newCustomer.setFirstname(form.getFirstname());
                newCustomer.setLastname(form.getLastname());
                newCustomer.setAddrline1(form.getAddrline1());
                newCustomer.setAddrline2(form.getAddrline2());
                newCustomer.setCity(form.getCity());
                newCustomer.setState(form.getState());
                newCustomer.setZip(form.getZip());
                double d = Double.parseDouble(form.getCash()); 
               // newCustomer.setCash(d);
                newCustomer.setPassword(form.getPassword());
                Transaction.begin();
                customerDAO.create(newCustomer);
                Transaction.commit();
                PendingBean pb = new PendingBean();
            	
            	
            	pb.setAmount(d);
            	pb.setCustomerid(newCustomer.getCustomerid());
            	pb.setTranstype("Deposit");
            	Transaction.begin();
            	pendingDAO.create(pb);
            	Transaction.commit();
            }
            List<String> messages = new ArrayList<String>();
            messages.add("New Customer has been Created and Initial Deposit has been Queued up!");
            request.setAttribute("messages", messages);
            return "employeeHome.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "createCustomer.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "createCustomer.jsp";
        } finally {
        	if (Transaction.isActive())
        		Transaction.rollback();
        }
    }
}

