package com.brendanrussell.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.brendanrussell.constants.JSPLocation;

@ControllerAdvice
@EnableWebMvc
@Controller
public class ErrorController {

	final static Logger logger = Logger.getLogger(ErrorController.class);

	@ExceptionHandler(value = Exception.class)
	@RequestMapping(value = "/errors", method = RequestMethod.GET)
	public ModelAndView goToErrorPage(HttpServletRequest httpRequest, Model model) {
		logger.debug("Reached Endpoint");
		// Everything below for printing the error on web page
		String errorMsg = "";
		int httpErrorCode = getErrorCode(httpRequest);

		switch (httpErrorCode) {
		case 400: {
			errorMsg = "Http Error Code: 400. Bad Request";
			break;
		}
		case 401: {
			errorMsg = "Http Error Code: 401. Unauthorized";
			break;
		}
		case 404: {
			return new ModelAndView(JSPLocation.PAGE_NOT_FOUND);
		}
		case 500: {
			errorMsg = "Http Error Code: 500. Internal Server Error";
			break;
		}
		}
		model.addAttribute("error-message", errorMsg);

		return new ModelAndView(JSPLocation.errorPage);
	}

	/**
	 * gets the error code for the web page
	 * 
	 * @param httpRequest
	 * @return
	 */
	private int getErrorCode(HttpServletRequest httpRequest) {
		return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
	}

}
