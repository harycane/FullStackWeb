package com.team1.webapp.task7.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.team1.webapp.task7.databean.CustomerBean;
import com.team1.webapp.task7.databean.EmployeeBean;
import com.team1.webapp.task7.formbean.ChangeMyPasswordForm;
import com.team1.webapp.task7.model.CusDAO;
import com.team1.webapp.task7.model.EmployeeDAO;
import com.team1.webapp.task7.model.Model;

public class ChangeMyPasswordAction extends Action {
	private FormBeanFactory<ChangeMyPasswordForm> formBeanFactory = FormBeanFactory
			.getInstance(ChangeMyPasswordForm.class);

	private CusDAO customerDAO;
	private EmployeeDAO employeeDAO;
	
	public ChangeMyPasswordAction(Model model) {
		customerDAO = model.getCustomerDAO();
		employeeDAO = model.getEmployeeDAO();
	}

	public String getName() {
		return "changeMyPassword.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
        List<String> messages = new ArrayList<>();
        request.setAttribute("messages", messages);
        
		try {
			ChangeMyPasswordForm form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			if (!form.isPresent()) {
				return "changeMyPassword.jsp";
			}
			
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "changeMyPassword.jsp";
			}
			
			if (form.getAction().equals("Change")) {
				if (session.getAttribute("customer") != null) {
					CustomerBean customer = (CustomerBean) session.getAttribute("customer");
					customer.setPassword(form.getPassword());
					Transaction.begin();
					customerDAO.update(customer);
					Transaction.commit();
					messages.add("Your password has been changed!");
					return "customerHome.jsp"; 
				} else if (session.getAttribute("employee") != null) {
					EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
					employee.setPassword(form.getPassword());
					Transaction.begin();
					employeeDAO.update(employee);
					Transaction.commit();
					messages.add("Your password has been changed!");
					return "employeeHome.jsp"; 
				}
			}
            return "error.jsp";

		} catch (RollbackException e) {
			errors.add(e.toString());
			return "error.jsp";
		} catch (FormBeanException e) {
			errors.add(e.toString());
			return "error.jsp";
		} finally {
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
}
