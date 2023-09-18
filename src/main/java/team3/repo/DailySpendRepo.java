package team3.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import team3.entity.DailySpendingEntity;

public interface DailySpendRepo extends JpaRepository<DailySpendingEntity, Integer> {
}
