package team3.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import team3.entity.User;

public interface UserRepo extends JpaRepository<User, Integer> {
	boolean existsByEmail(String email);
	User findByToken(String token);
	User findByEmailAndPasswd(String email,String passwd);
}
