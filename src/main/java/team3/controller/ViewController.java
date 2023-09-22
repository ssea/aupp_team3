package team3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ViewController extends BaseController {
	@Autowired
	HttpServletRequest request;
	
	@GetMapping("/")
	public String index() {
		if (!Authenticated()) {
			return "redirect:login";
		}
		return "pages/home";
	}
	
	@GetMapping("/login")
	public String login() {
		if (Authenticated()) {
			return "redirect:";
		}
		return "pages/login";
	}
	
	@GetMapping("/logout")
	public String logout() {
		removeCookie("token");
		return "redirect:login";
	}
	
	@GetMapping("/register")
	public String register() {
		if (Authenticated()) {
			return "redirect:";
		}
		return "pages/register";
	}
	
	@GetMapping("/activate")
	public String activate() {
		if (Authenticated()) {
			return "redirect:";
		}
		return "pages/activate";
	}
	
	@GetMapping("/reset")
	public String reset() {
		if (Authenticated()) {
			return "redirect:";
		}
		return "pages/reset";
	}
	
	@GetMapping("/set")
	public String set() {
		if (Authenticated()) {
			return "redirect:";
		}
		return "pages/set";
	}
	
	@GetMapping("/category")
	public String category() {
		if (!Authenticated()) {
			return "redirect:login";
		}
		return "pages/category";
	}
}
