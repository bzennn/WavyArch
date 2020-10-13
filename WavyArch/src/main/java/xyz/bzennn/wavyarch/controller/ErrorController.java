package xyz.bzennn.wavyarch.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class ErrorController {

	public static final String DEFAULT_ERROR_VIEW = "error";
	public static final Map<Integer, String> DEFAULT_ERROR_MESSAGES = new HashMap<Integer, String>() {
		private static final long serialVersionUID = 8744982540209347028L;
	{
			put(400, "Bad Request");
			put(401, "Unauthorized");
			put(404, "Page Not Found");
			put(500, "Internal Server Error");
	}};
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showErrorPage(HttpServletRequest request) {
		ModelAndView errorMav = new ModelAndView(DEFAULT_ERROR_VIEW);
		
		if (request != null) {
			int httpErrorCode = getErrorCode(request);
			String errorMessage = "An Error Has Occured";
			if (DEFAULT_ERROR_MESSAGES.containsKey(httpErrorCode)) {
				errorMessage = DEFAULT_ERROR_MESSAGES.get(httpErrorCode);
			}
			
			errorMav.addObject("errorCode", httpErrorCode);
			errorMav.addObject("errorMessage", errorMessage);
			errorMav.addObject("url", request.getRequestURL());
		}
		
		errorMav.addObject("datetime", new Date());
		
		return errorMav;
	}

	private int getErrorCode(HttpServletRequest request) {
		return (Integer) request.getAttribute("javax.servlet.error.status_code");
	}
}
