package team3.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import team3.entity.CategoryEntity;

public interface CategoryRepo extends JpaRepository<CategoryEntity, Integer> {
}
