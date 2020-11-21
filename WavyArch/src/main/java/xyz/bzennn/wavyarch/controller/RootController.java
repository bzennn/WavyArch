package xyz.bzennn.wavyarch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Controller that redirects from '/'
 *
 * @author bzennn
 * @version 1.0
 */
@Controller
@RequestMapping("/")
public class RootController {

	@RequestMapping(method = RequestMethod.GET)
	public View handleRootMapping() {
		RedirectView redirect = new RedirectView("audios");
		redirect.setExposeModelAttributes(false);
		
		return redirect;
	}
	
}
