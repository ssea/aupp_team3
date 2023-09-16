package team3.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import team3.service.UserService;

public class BaseController {
	@Autowired
	HttpServletRequest request;
	@Autowired
	HttpServletResponse response;
	@Autowired
	UserService userService;

	protected boolean Authenticated() {
		String token = getCookieValue("token");
		if (token == null) {
			return false;
		}
		if (!userService.userTokenExists(token)) {
			return false;
		}
		return true;
	}

	protected String getCookieValue(String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(cookieName))
					return c.getValue();
			}
		}
		return null;
	}

	protected void removeCookie(String cookieName) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : request.getCookies()) {
				if (c.getName().equals(cookieName)) {
					c.setPath("/");
					c.setHttpOnly(true);
					c.setMaxAge(0);
					response.addCookie(c);
				}
			}
		}
		
	}

	public String getBaseUrl() {
		String scheme = request.getScheme() + "://";
		String serverName = request.getServerName();
		String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
		String contextPath = request.getContextPath();
		return scheme + serverName + serverPort + contextPath;
	}

	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}
}
