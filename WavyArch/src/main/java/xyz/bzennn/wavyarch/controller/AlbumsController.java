package xyz.bzennn.wavyarch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/albums")
public class AlbumsController {

	@RequestMapping(method = RequestMethod.GET)
	public String showAlbumsPage() {
		return "albums";
	}

}
