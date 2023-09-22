package team3.controller;

import jakarta.servlet.http.HttpServletRequest;
import team3.entity.CategoryEntity;
import team3.entity.User;
import team3.service.CategoryService;
import team3.service.SpendingService;
import team3.service.UserService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController {
	@Autowired
	HttpServletRequest request;
	@Autowired
	CategoryService categoryService;
	@Autowired
	UserService userService;
	@Autowired
	SpendingService spendingService;

	@GetMapping("/getCategories")
	public ResponseEntity<List<CategoryEntity>> getCategories() {
		if (!Authenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		String token = getCookieValue("token");
		User user = userService.getByToken(token);
		List<CategoryEntity> cates = categoryService.getCategories(user.getId());
		return ResponseEntity.status(HttpStatus.OK).body(cates);
	}

	@PostMapping("/create")
	public ResponseEntity<CategoryEntity> createCategory(@RequestParam Map<String, String> reqParam) {
		if (!Authenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		String token = getCookieValue("token");
		User user = userService.getByToken(token);

		String name = reqParam.get("name");

		CategoryEntity categoryResponse = new CategoryEntity();
		boolean error = false;

		if (name.equals("")) {
			categoryResponse.setName("The name field can not be empty!.");
			error = true;
		}
		
		if(categoryService.categoryNameExists(name)) {
			categoryResponse.setName("The name is already existed!.");
			error = true;
		}
		if (error) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(categoryResponse);
		}
		
		CategoryEntity category = categoryService.createCategory(new CategoryEntity(name, user.getId()));
		return ResponseEntity.status(HttpStatus.OK).body(category);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<CategoryEntity> deleteCategory(@RequestParam Map<String, String> reqParam) {
		if (!Authenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		String token = getCookieValue("token");
		User user = userService.getByToken(token);
		
		int id = Integer.parseInt(reqParam.get("id"));
		CategoryEntity deleteCategory = categoryService.selectCategory(id);
		if(spendingService.categoryIdExists(id)) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
		
		if(deleteCategory == null) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
		
		if(deleteCategory.getUserId() != user.getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		
		categoryService.deleteCategory(id);
		return ResponseEntity.status(HttpStatus.OK).body(deleteCategory);
	}
}
