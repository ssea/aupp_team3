package team3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team3.entity.CategoryEntity;
import team3.repo.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Override
	public List<CategoryEntity> getCategories(int userId) {
		return categoryRepo.findByUserId(userId);
	}

	@Override
	public CategoryEntity createCategory(CategoryEntity c) {
		return categoryRepo.save(c);
	}

	@Override
	public boolean categoryNameExists(String name) {
		return categoryRepo.existsByName(name);
	}

	@Override
	public void deleteCategory(int id) {
		categoryRepo.deleteById(id);
	}

	@Override
	public CategoryEntity selectCategory(int id) {
		return categoryRepo.findById(id).orElse(null);
	}

}
