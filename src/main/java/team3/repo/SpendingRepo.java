package team3.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import team3.entity.SpendingEntity;

public interface SpendingRepo extends JpaRepository<SpendingEntity, Integer> {
	List<SpendingEntity> findByUserIdAndSpendDate(int userId, String date);
	boolean existsByCategoryId(int categoryId);
}
