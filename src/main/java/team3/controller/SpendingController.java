package team3.controller;

import team3.entity.CategoryEntity;
import team3.entity.SpendingEntity;
import team3.entity.User;
import team3.response.ExpenseAverage;
import team3.response.MaxExpense;
import team3.response.TopSpendItem;
import team3.service.CategoryService;
import team3.service.SpendingService;
import team3.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/expense")
public class SpendingController extends BaseController{
	@Autowired
	SpendingService spendingService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	UserService userService;
	
    @GetMapping("/getAllByDate")
    public ResponseEntity<List<SpendingEntity>> getExpenseByDate(@RequestParam Map<String, String> reqParam) throws ParseException{
    	if (!Authenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		String token = getCookieValue("token");
		User user = userService.getByToken(token);
		String spendDate = reqParam.get("spendDate");
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
		Date date = format2.parse(spendDate);
		String formettedSpendDate = format1.format(date);
		
		List<SpendingEntity> expenses = spendingService.getExpensesByDate(user.getId(), formettedSpendDate);
		
		for(int i = 0; i < expenses.size(); i++) {
			SpendingEntity e = expenses.get(i);
			
			CategoryEntity c = categoryService.selectCategory(e.getCategoryId());
			
			e.setCategory(c);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(expenses);
    }
    
    @PostMapping("/create")
    public ResponseEntity<SpendingEntity> createExpense(@RequestParam Map<String, String> reqParam) throws ParseException{
    	if (!Authenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		String token = getCookieValue("token");
		User user = userService.getByToken(token);
		
		int categoryId = Integer.parseInt(reqParam.get("categoryId"));
		double spendAmount = Double.parseDouble(reqParam.get("spendAmount"));
		String currencyType = reqParam.get("currencyType");
		String description = reqParam.get("description");
		String spendDate = reqParam.get("spendDate");
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
		Date date = format2.parse(spendDate);
		String formettedSpendDate = format1.format(date);
		
		CategoryEntity category = categoryService.selectCategory(categoryId);
		if(category == null) {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
		}
		if(category.getUserId() != user.getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		double amountKhr = 0;
		double amountUsd = 0;
		if(currencyType.equals("dollar")) {
			amountKhr = spendAmount * 4000;
			amountUsd = spendAmount;
 		}else if(currencyType.equals("riel")){
 			amountUsd = spendAmount / 4000;
 			amountKhr = spendAmount;
 		}else {
 			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
 		}
		
		SpendingEntity expense = spendingService.createExpense(new SpendingEntity(user.getId(),categoryId,amountKhr,amountUsd,description,formettedSpendDate,currencyType));
		return ResponseEntity.status(HttpStatus.OK).body(expense);
    }
    
    @DeleteMapping("/delete")
	public ResponseEntity<SpendingEntity> deleteExpense(@RequestParam Map<String, String> reqParam) {
		if (!Authenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		String token = getCookieValue("token");
		User user = userService.getByToken(token);
		
		int id = Integer.parseInt(reqParam.get("id"));
		SpendingEntity deleteExpense = spendingService.selectExpense(id);
		
		if(deleteExpense == null) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
		
		if(deleteExpense.getUserId() != user.getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		
		spendingService.deleteExpense(id);
		return ResponseEntity.status(HttpStatus.OK).body(deleteExpense);
	}

    @GetMapping("/findDateWithMaxExpense")
    public ResponseEntity<MaxExpense> findDateWithMaxExpense(@RequestParam Map<String, String> reqParam) throws ParseException{
    	if (!Authenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		String token = getCookieValue("token");
		User user = userService.getByToken(token);
		String fromDate = reqParam.get("fromDate");
		String toDate = reqParam.get("toDate");
		
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
		Date from_date = format2.parse(fromDate);
		String formettedFromDate = format1.format(from_date);
		
		Date to_date = format2.parse(toDate);
		String formettedToDate = format1.format(to_date);
		
		MaxExpense me = spendingService.findDateWithMaxExpense(user.getId(),formettedFromDate, formettedToDate);
		return ResponseEntity.status(HttpStatus.OK).body(me);
    }
    
    @GetMapping("/findThreeItemsMaxExpense")
    public ResponseEntity<List<TopSpendItem>> findThreeItemsMaxExpense(@RequestParam Map<String, String> reqParam) throws ParseException{
    	if (!Authenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		String token = getCookieValue("token");
		User user = userService.getByToken(token);
		String fromDate = reqParam.get("fromDate");
		String toDate = reqParam.get("toDate");
		
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
		Date from_date = format2.parse(fromDate);
		String formettedFromDate = format1.format(from_date);
		
		Date to_date = format2.parse(toDate);
		String formettedToDate = format1.format(to_date);
		
		List<TopSpendItem> items = spendingService.findThreeItemsMaxExpense(user.getId(),formettedFromDate, formettedToDate);
		return ResponseEntity.status(HttpStatus.OK).body(items);
    }
    
    
    @GetMapping("/findExpenseAverage")
    public ResponseEntity<ExpenseAverage> findExpenseAverage(@RequestParam Map<String, String> reqParam) throws ParseException{
    	if (!Authenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		String token = getCookieValue("token");
		User user = userService.getByToken(token);
		String fromDate = reqParam.get("fromDate");
		String toDate = reqParam.get("toDate");
		
		
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
		Date from_date = format2.parse(fromDate);
		String formettedFromDate = format1.format(from_date);
		
		Date to_date = format2.parse(toDate);
		String formettedToDate = format1.format(to_date);
		
		ExpenseAverage avg = spendingService.findExpenseAverage(user.getId(),formettedFromDate, formettedToDate);
		return ResponseEntity.status(HttpStatus.OK).body(avg);
    }
    
    @GetMapping("/getMaxSpendGroupByDate")
    public ResponseEntity<List<MaxExpense>> getMaxSpendGroupByDate(@RequestParam Map<String, String> reqParam) throws ParseException{
    	if (!Authenticated()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		String token = getCookieValue("token");
		User user = userService.getByToken(token);
		
		List<MaxExpense> expenses = spendingService.getMaxSpendGroupByDate(user.getId());
		return ResponseEntity.status(HttpStatus.OK).body(expenses);
    }
}
