package team3.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import team3.entity.User;
import team3.service.EmailService;
import team3.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	HttpServletRequest request;
	@Autowired
	UserService userService;
	@Autowired
	EmailService mail;

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestParam Map<String, String> reqParam) {
		User userResponse = new User();
		String name = reqParam.get("name");
		String email = reqParam.get("email");
		String passwd = reqParam.get("passwd");
		String confirmPasswd = reqParam.get("confirmPasswd");
		boolean error = false;

		if (name.equals("")) {
			userResponse.setName("The name field can not be empty!.");
			error = true;
		}
		if (email.equals("")) {
			userResponse.setEmail("The email field can not be empty!.");
			error = true;
		} else {
			if (!isValidEmailAddress(email)) {
				userResponse.setEmail("Email is not valid!.");
				error = true;
			} else {
				if (userService.userEmailExists(email)) {
					userResponse.setEmail("Email is already existed!.");
					error = true;
				}
			}
		}
		if (!passwd.equals(confirmPasswd)) {
			userResponse.setPasswd("Confirm password does not match!.");
			error = true;
		}
		if (passwd.length() < 8 || passwd.length() > 12) {
			userResponse.setPasswd("Password must be 8 to 12 digits!.");
			error = true;
		}
		if (error) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(userResponse);
		}

		User user = new User(name, email, passwd, null);
		mail.sendEmail(email, "Account Activation", "<a href=" + getBaseUrl() + "/activate?token=" + user.getToken()
				+ ">click here to activate your account (" + email + ")</a>");

		user = userService.registerUser(user);
		user.setPasswd("xxx$ECRETxxx");
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@PostMapping("/login")
	public ResponseEntity<User> loginUser(@RequestParam Map<String, String> reqParam) {
		User userResponse = new User();
		String email = reqParam.get("email");
		String passwd = reqParam.get("passwd");
		boolean error = false;
		if (email.equals("")) {
			userResponse.setEmail("The email field can not be empty!.");
			error = true;
		}
		if (passwd.equals("")) {
			userResponse.setPasswd("The password field can not be empty!.");
			error = true;
		}
		if (!email.equals("") && !userService.userEmailExists(email)) {
			userResponse.setEmail("Email is does not existed!.");
			error = true;
		}
		User user = null;
		if (!email.equals("") && !passwd.equals("")) {
			user = userService.authenticateUser(email, passwd);
			if (user == null) {
				userResponse.setPasswd("Password is not correct!.");
				error = true;
			}
			if (user != null && user.getStatus() == null) {
				userResponse.setEmail("Email has not been activated!.");
				error = true;
			}
		}
		if (error) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(userResponse);
		}
		user.setPasswd("xxx$ECRETxxx");
		HttpCookie cookie = ResponseCookie.from("token", user.getToken()).httpOnly(true).path("/").build();
		return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(user);
	}

	@PatchMapping("/activate")
	public ResponseEntity<String> activateUser(@RequestParam Map<String, String> reqParam) {
		String token = reqParam.get("token");
		User user = userService.getByToken(token);
		if (user == null) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Activation link is invalided!.");
		}
		if (user.getStatus() != null && user.getStatus().equals("activated")) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Account has already activated!.");
		}
		boolean activated = userService.activateUser(token);
		if (!activated) {
			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout, please try again!.");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Your account has succesfully activated.");
	}

	@PostMapping("/reset")
	public ResponseEntity<User> resetUserPasswd(@RequestParam Map<String, String> reqParam) {
		User userResponse = new User();
		String email = reqParam.get("email");
		boolean error = false;
		if (email.equals("")) {
			userResponse.setEmail("The email field can not be empty!.");
			error = true;
		}
		if (!email.equals("") && !userService.userEmailExists(email)) {
			userResponse.setEmail("Email is does not existed!.");
			error = true;
		}
		if (error) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(userResponse);
		}
		User user = userService.getByEmail(email);
		user.setPasswd("xxx$ECRETxxx");
		mail.sendEmail(email, "Reset Password", "<a href=" + getBaseUrl() + "/set?token=" + user.getToken()
				+ ">click here to set your account new password (" + email + ")</a>");
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	@GetMapping("/checkToken")
	public ResponseEntity<Boolean> checkUserEmail(@RequestParam Map<String, String> reqParam) {
		String token = reqParam.get("token");
		boolean exists = userService.userTokenExists(token);
		if (!exists) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exists);
		}
		return ResponseEntity.status(HttpStatus.OK).body(exists);
	}

	@PatchMapping("/set")
	public ResponseEntity<String> setUserPasswd(@RequestParam Map<String, String> reqParam) {
		String token = reqParam.get("token");
		String passwd = reqParam.get("passwd");
		String confirmPasswd = reqParam.get("confirmPasswd");
		boolean error = false;
		String errorText = "";
		if (!passwd.equals(confirmPasswd)) {
			errorText = "Confirm password does not match!.";
			error = true;
		}
		if (passwd.length() < 8 || passwd.length() > 12) {
			errorText = "Password must be 8 to 12 digits!.";
			error = true;
		}
		if (error) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(errorText);
		}
		boolean changed = userService.setUserPasswd(token, passwd);
		if (!changed) {
			return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("Request timeout, please try again!.");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Your password has succesfully changed.");
	}

	@GetMapping("/verify")
	public ResponseEntity<User> verifyUser() {
		User userResponse = new User();
		String token = getCookieValue("token");
		if (token == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userResponse);
		}
		if (!userService.userTokenExists(token)) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(userResponse);
		}
		User user = userService.getByToken(token);
		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
}
