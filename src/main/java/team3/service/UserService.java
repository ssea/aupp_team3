package team3.service;

import org.springframework.stereotype.Service;

import team3.entity.User;

@Service
public interface UserService {
	public User registerUser(User u);
	public boolean emailExists(String email);
	public User getUserByToken(String token);
	public User updateUser(int id, User u);
	public User logUser(String email,String passwd);
}
