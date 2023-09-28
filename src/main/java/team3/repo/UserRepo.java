package team3.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import team3.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	boolean existsByToken(String token);

	boolean existsByEmail(String email);

	User findByEmailAndPasswd(String email, String passwd);
	
	User findByToken(String token);
	
	User findByEmail(String email);
}
