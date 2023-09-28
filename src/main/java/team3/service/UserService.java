package team3.service;

import org.springframework.stereotype.Service;

import team3.entity.User;

@Service
public interface UserService {
	public User registerUser(User u);

	public boolean userTokenExists(String token);

	public boolean userEmailExists(String email);

	public User authenticateUser(String email, String passwd);
	
	public boolean activateUser(String token);
	
	public User updateUser(int id, User u);
	
	public User getByToken(String token);
	
	public User getByEmail(String email);
	
	public boolean setUserPasswd(String token, String passwd);
}
