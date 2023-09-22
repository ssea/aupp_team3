package team3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import team3.entity.CategoryEntity;

@Service
public interface CategoryService {
	public boolean categoryNameExists(String name);
	
	public CategoryEntity createCategory(CategoryEntity c);
	
	public List<CategoryEntity> getCategories(int userId);
	
	public void deleteCategory(int id);
	
	public CategoryEntity selectCategory(int id);
}
