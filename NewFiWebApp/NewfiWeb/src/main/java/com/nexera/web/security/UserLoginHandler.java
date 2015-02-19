package com.nexera.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.nexera.web.constants.JspLookup;

public class UserLoginHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private static final Logger LOG = LoggerFactory.getLogger(UserLoginHandler.class);

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authUser) throws IOException,
			ServletException {
		LOG.info("Inside onAuthenticationSuccess controller");
		HttpSession session = request.getSession(false);
		/*
		 * TODO: Handle chat session login
		 */
		
		String redirectTo = null;

		if (session != null) {
			redirectTo = (String) request.getSession().getAttribute("url_prior_login");
			LOG.info("Session Check: " + redirectTo);
		}

		if (redirectTo != null && !redirectTo.contains(JspLookup.LOGIN)) {
			response.sendRedirect(redirectTo);
			LOG.info("Url Check: " + redirectTo);
		}
		else {
			response.sendRedirect("./" + JspLookup.CUSTOMER_VIEW + ".do");
			LOG.info("Final Check: " + "./" + JspLookup.CUSTOMER_VIEW + ".do");
		}
	}
}