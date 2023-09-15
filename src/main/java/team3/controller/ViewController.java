package team3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import team3.entity.User;
import team3.service.UserService;

@Controller
public class ViewController {
	@Autowired
	HttpServletRequest request;
	@Autowired
	private UserService userService;
	@Autowired
	HttpSession session;

	@GetMapping("/")
	public String homePage() {
		String user_token = (String) request.getSession().getAttribute("user_token");
		if (user_token == null) {
			return "redirect:login";
		}
		return "index";
	}

	@GetMapping("/login")
	public String loginPage() {
		String user_token = (String) request.getSession().getAttribute("user_token");
		if (user_token != null) {
			return "redirect:";
		}
		return "pages/login";
	}

	@GetMapping("/logout")
	public String logoutPage() {
		return "redirect:login";
	}

	@GetMapping("/register")
	public String registerPage() {
		String user_token = (String) request.getSession().getAttribute("user_token");
		if (user_token != null) {
			return "redirect:";
		}
		return "pages/register";
	}

	@GetMapping("/activate/{token}")
	public String activatePage(@PathVariable String token) {
		String user_token = (String) request.getSession().getAttribute("user_token");
		if (user_token != null) {
			return "redirect:";
		}
		User user = userService.getUserByToken(token);
		if (user.getStatus() != null && user.getStatus().equals("activated")) {
			session.setAttribute("msg", "Your account has already activated.");
		}else {
			user.setStatus("activated");
			userService.updateUser(user.getId(), user);
			session.setAttribute("msg", "Your account has been activated.");
		}
		return "pages/activate";
	}
}
