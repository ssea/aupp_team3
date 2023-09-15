package team3.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import team3.entity.User;
import team3.service.EmailService;
import team3.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private EmailService mail;

	@Autowired
	HttpServletRequest request;
	HttpServletResponse response;
	
	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User u) {
		User user = new User();
		boolean error = false;
		if (userService.emailExists(u.getEmail())) {
			user.setEmail("Email is already existed.");
			error = true;
		}
		if (u.getPasswd().length() < 8 || u.getPasswd().length() > 12) {
			user.setPasswd("Password must be 8 to 12 digits.");
			error = true;
		}
		if (error) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(user);
		}
		mail.sendEmail(u.getEmail(), "Account Activation",
				"<a href=" + getBaseUrl() + "/activate/" + getMd5(u.getEmail() + u.getName() + u.getPasswd())
						+ ">click here to activate your account with " + u.getEmail() + " </a>");

		user = userService.registerUser(u);
		user.setPasswd("xxx$ECRETxxx");
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<User> logUser(@RequestParam String email, @RequestParam String passwd) {
		User user = new User();
		User logUser = new User();
		boolean error = false;
		if (!userService.emailExists(email)) {
			user.setEmail("Email is not existed.");
			error = true;
		}
		if (!error) {
			logUser = userService.logUser(email, passwd);
			if (logUser == null) {
				user.setPasswd("Password is not correct.");
				error = true;
			}
		}
		if (error) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(user);
		}
		
		HttpCookie cookie = ResponseCookie.from("token", logUser.getToken())
		        .path("/")
		        .build();
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(logUser);
	}

	public String getMd5(String input) {
		try {
			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public String getBaseUrl() {
		String scheme = request.getScheme() + "://";
		String serverName = request.getServerName();
		String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
		String contextPath = request.getContextPath();
		return scheme + serverName + serverPort + contextPath;
	}
}
