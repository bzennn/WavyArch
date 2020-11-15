package xyz.bzennn.wavyarch.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Advice Controller that adds uri, url and viewName to other controllers {@link Model} 
 *
 * @author bzennn
 * @version 1.0
 */
@ControllerAdvice
public class URLControllerAdvice {
	
	@ModelAttribute
	public void addURIAttribute(HttpServletRequest request, Model model) {
		model.addAttribute("uri", request.getRequestURI());
	}
	
	@ModelAttribute
	public void addURLAttribute(HttpServletRequest request, Model model) {
		model.addAttribute("url", request.getRequestURL());
	}
	
	@ModelAttribute
	public void addViewNameAttribute(HttpServletRequest request, Model model) {
		String[] uriParts = request.getRequestURI().replaceFirst("/WavyArch/", "").split("/");
		
		String viewName = "";
		if (uriParts.length > 0) {
			viewName = uriParts[0];
		}
		
		model.addAttribute("viewName", viewName);
	}
}
