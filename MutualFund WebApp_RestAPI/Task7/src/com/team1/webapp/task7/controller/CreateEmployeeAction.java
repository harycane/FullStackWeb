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

import com.team1.webapp.task7.databean.EmployeeBean;
import com.team1.webapp.task7.formbean.CreateEmployeeForm;
import com.team1.webapp.task7.model.EmployeeDAO;
import com.team1.webapp.task7.model.Model;
import com.team1.webapp.task7.utility.EmpNameComparator;

public class CreateEmployeeAction extends Action {
    private FormBeanFactory<CreateEmployeeForm> formBeanFactory = FormBeanFactory.getInstance(CreateEmployeeForm.class);

    private EmployeeDAO employeeDAO;

    public CreateEmployeeAction(Model model) {
    	employeeDAO = model.getEmployeeDAO();
    }

    public String getName() {
        return "createEmployee.do";
    }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
            CreateEmployeeForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            EmployeeBean[] empList = employeeDAO.getEmployees();
			if (empList != null) {
				Arrays.sort(empList, new EmpNameComparator());
			}
            request.setAttribute("empList", empList);
            
            // If no params were passed, return with no errors so that the form will be
            // presented (we assume for the first time).
            if (!form.isPresent()) {
                return "createEmployee.jsp";
            }

            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "createEmployee.jsp";
            }

            if (form.getAction().equals("Create")) {
            	EmployeeBean[] employees = employeeDAO.match(MatchArg.equals("username", form.getUsername()));
            	if (employees.length != 0) {
            		errors.add("Username already exits");
            		return "createEmployee.jsp";
            	}
                EmployeeBean newEmployee = new EmployeeBean();
                newEmployee.setUsername(form.getUsername());
                newEmployee.setFirstname(form.getFirstname());
                newEmployee.setLastname(form.getLastname());
                newEmployee.setPassword(form.getPassword());
                Transaction.begin();
                employeeDAO.create(newEmployee);
                Transaction.commit();
            }
            List<String> messages = new ArrayList<>();
            messages.add("New Employee account has been created!");
            request.setAttribute("messages", messages);

            return "employeeHome.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "createEmployee.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "createEmployee.jsp";
        } finally {
        	if(Transaction.isActive())
        		Transaction.rollback();
        }
    }
}
