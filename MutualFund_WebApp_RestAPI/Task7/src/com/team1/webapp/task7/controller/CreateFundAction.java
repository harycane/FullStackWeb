package com.team1.webapp.task7.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import com.team1.webapp.task7.databean.FundBean;
import com.team1.webapp.task7.formbean.CreateFundForm;
import com.team1.webapp.task7.model.FundDAO;
import com.team1.webapp.task7.model.Model;
import com.team1.webapp.task7.utility.FundNameComparator;

public class CreateFundAction extends Action {
    private FormBeanFactory<CreateFundForm> formBeanFactory = FormBeanFactory.getInstance(CreateFundForm.class);
    private FundDAO fundDAO;
    
    public CreateFundAction(Model model) {
    	fundDAO = model.getFundDAO();
    }

    public String getName() {
        return "createFund.do";
    }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
            CreateFundForm form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            FundBean[] funds = fundDAO.getAllFunds();
			if (funds != null) {
				Arrays.sort(funds, new FundNameComparator());
			}
            request.setAttribute("funds", funds);
            
            if (!form.isPresent()) {
                return "createFund.jsp";
            }

            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "createFund.jsp";
            }
            
            if (form.getAction().equals("Create")) {
            	if (fundDAO.getFundBySymbol(form.getSymbol()) != null) {
            		errors.add("Fund symbol already exists");
            		return "createFund.jsp";
            	}
            	if (fundDAO.getFundByName(form.getName()) != null) {
            		errors.add("Fund name already exists");
            		return "createFund.jsp";
            	}
            }
            FundBean fb = new FundBean();
            fb.setName(form.getName());
            fb.setSymbol(form.getSymbol());
            Transaction.begin();
            fundDAO.create(fb);
            Transaction.commit();
            List<String> messages = new ArrayList<>();
            messages.add("New Fund: " + form.getName() + " (" + form.getSymbol() + ") has been created!" );
            request.setAttribute("messages", messages);
            return "employeeHome.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        } finally {
        	if(Transaction.isActive())
        		Transaction.rollback();
        }
    }
}
