package com.brendanrussell.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.brendanrussell.constants.JSPLocation;
import com.brendanrussell.constants.URLLocation;

/**
 * Controller for the common end points of the web app
 * 
 * @author Thomas Rokicki
 *
 */
@Controller
public class DashboardController {

	final static Logger logger = Logger.getLogger(DashboardController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcomePage() {
		logger.debug("Reached Endpoint");

		// TODO remove reference to qcc organization
		return "redirect:" + URLLocation.DASHBOARD;
	}

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboardPage() {
		return new ModelAndView(JSPLocation.HOME_PAGE);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView(JSPLocation.LOGIN);
	}

}