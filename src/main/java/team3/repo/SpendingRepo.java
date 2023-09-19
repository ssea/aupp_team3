package team3.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import team3.entity.SpendingEntity;

public interface SpendingRepo extends JpaRepository<SpendingEntity, Integer> {
}
