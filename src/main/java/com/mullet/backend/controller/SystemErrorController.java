package com.mullet.backend.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SystemErrorController {

	@RequestMapping("/error/500")
	public String error500(HttpServletRequest request) {
		Exception ex = (Exception)request.getAttribute("exception");
		request.setAttribute("message", ex.getMessage());
		return "error/500";
	}

	@RequestMapping("/error/404")
	public String error404() {
		return "error/404";
	}

	@RequestMapping("/error/403")
	public String error403() {
		return "error/403";
	}

	@RequestMapping("/error/405")
	public String error405() {
		return "error/405";
	}

	@RequestMapping("/error/401")
	public String error401() {
		return "error/401";
	}

	@RequestMapping("/error/400")
	public String error400() {
		return "error/400";
	}

	@RequestMapping("/error/440")
	public String error440() {
		return "error/440";
	}

}
