package com.team1.webapp.task7.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutAction extends Action {

	@Override
	public String getName() {
		return "logout.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("customer", null);
		session.setAttribute("employee", null);
		return "welcome.do";
	}
}