package team3.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team3.entity.User;
import team3.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserRepo repo;

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

	@Override
	public User registerUser(User u) {
		u.setToken(getMd5(u.getEmail() + u.getName() + u.getPasswd()));
		return repo.save(u);
	}

	@Override
	public boolean emailExists(String email) {
		return repo.existsByEmail(email);
	}

	@Override
	public User getUserByToken(String token) {
		return repo.findByToken(token);
	}

	@Override
	public User updateUser(int id, User u) {
		User user = repo.findById(id).orElse(null);
		user.setName(u.getName());
		user.setEmail(u.getEmail());
		user.setPasswd(u.getPasswd());
		user.setToken(getMd5(u.getEmail() + u.getName() + u.getPasswd()));
		return repo.save(user);
	}

	@Override
	public User logUser(String email, String passwd) {
		return repo.findByEmailAndPasswd(email, passwd);
	}
}
