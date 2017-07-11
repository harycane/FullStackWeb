package com.team1.webapp.task7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.team1.webapp.task7.databean.CustomerBean;
import com.team1.webapp.task7.formbean.ResetCustomerPasswordForm;
import com.team1.webapp.task7.model.CusDAO;
import com.team1.webapp.task7.model.Model;

public class ResetCustomerPasswordAction extends Action {
	private FormBeanFactory<ResetCustomerPasswordForm> formBeanFactory = FormBeanFactory
			.getInstance(ResetCustomerPasswordForm.class);

	private CusDAO customerDAO;

	public ResetCustomerPasswordAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "resetCustomerPassword.do";
	}

	public String perform(HttpServletRequest request) {
		String username = request.getParameter("username");
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			ResetCustomerPasswordForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);
			if (username != null) {
				form.setUsername(username);
			}
			
			if (request.getParameter("action") == null) {
				return "resetCustomerPassword.jsp";
			}
			
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "resetCustomerPassword.jsp";
			}
			
			if (form.getAction().equals("Reset")) {
				CustomerBean customer = customerDAO.getCustomerByUserName(form.getUsername());
				if (customer == null) {
					errors.add("User does not exist");
					return "resetCustomerPassword.jsp";
				}

				customer.setPassword(form.getPassword());
				Transaction.begin();
				customerDAO.update(customer);
			Transaction.commit();
			}
            List<String> messages = new ArrayList<>();
            messages.add("Customer: " + form.getUsername() + "'s password has been reset!");
            request.setAttribute("messages", messages);
            return "employeeHome.jsp";

		} catch (RollbackException e) {
			errors.add(e.toString());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return "error.jsp";
		} finally {
			if(Transaction.isActive())
				Transaction.rollback();
		}
	}
}
