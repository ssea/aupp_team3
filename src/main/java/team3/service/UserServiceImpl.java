package team3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team3.entity.User;
import team3.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepo userRepo;

	@Override
	public User registerUser(User u) {
		return userRepo.save(u);
	}

	@Override
	public boolean userTokenExists(String token) {
		return userRepo.existsByToken(token);
	}

	@Override
	public boolean userEmailExists(String email) {
		return userRepo.existsByEmail(email);
	}

	@Override
	public User authenticateUser(String email, String passwd) {
		return userRepo.findByEmailAndPasswd(email, passwd);
	}

	@Override
	public User updateUser(int id, User u) {
		User user = userRepo.findById(id).orElse(null);
		user.setName(u.getName());
		user.setEmail(u.getEmail());
		user.setPasswd(u.getPasswd());
		user.setToken();
		return userRepo.save(user);
	}

	@Override
	public boolean activateUser(String token) {
		User user = userRepo.findByToken(token);
		user.setStatus("activated");
		return userRepo.save(user) != null;
	}

	@Override
	public User getByToken(String token) {
		return userRepo.findByToken(token);
	}

	@Override
	public User getByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	@Override
	public boolean setUserPasswd(String token, String passwd) {
		User user = userRepo.findByToken(token);
		user.setPasswd(passwd);
		user.setToken();
		return userRepo.save(user) != null;
	}

}
