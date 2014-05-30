package com.queueexample.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class GAEJSignupSubscriberServlet extends HttpServlet {
	private static final Logger _logger = Logger
			.getLogger(GAEJSignupSubscriberServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		String strCallResult = "";
		resp.setContentType("text/plain");
		try {
			String strEmailId = req.getParameter("emailid");
			_logger.info("Got a Signup Subscriber Request for Email ID : "
					+ strEmailId);
			//
			// PUT YOUR TASK CODE HERE
			//
			strCallResult = "SUCCESS: Subscriber Signup";
			_logger.info(strCallResult);
			resp.getWriter().println(strCallResult);
		} catch (Exception ex) {
			strCallResult = "FAIL: Subscriber Signup : " + ex.getMessage();
			_logger.info(strCallResult);
			resp.getWriter().println(strCallResult);
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}