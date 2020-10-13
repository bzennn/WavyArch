package xyz.bzennn.wavyarch.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/error")
public class ErrorController {

	@RequestMapping(method = RequestMethod.GET)
	public String showErrorPage(HttpServletRequest request, Model model) {
		int httpErrorCode = getErrorCode(request);
		String errorMessage = "";

		switch (httpErrorCode) {
			case 400:
				errorMessage = "Bad Request!";
				break;
			case 401:
				errorMessage = "Unauthorized!";
				break;
			case 404:
				errorMessage = "Page Not Found!";
				break;
			case 500:
				errorMessage = "Internal Server Error!";
				break;
			default:
				errorMessage = "Error!";
				break;
		}
		
		model.addAttribute("errorMessage", errorMessage);
		model.addAttribute("errorCode", httpErrorCode);
		
		return "error";
	}

	private int getErrorCode(HttpServletRequest request) {
		return (Integer) request.getAttribute("javax.servlet.error.status_code");
	}
}
