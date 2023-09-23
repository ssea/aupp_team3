package team3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import team3.entity.SpendingEntity;
import team3.response.ExpenseAverage;
import team3.response.MaxExpense;
import team3.response.TopSpendItem;

@Service
public interface SpendingService {
	public List<SpendingEntity> getExpensesByDate(int userId, String date);
	public SpendingEntity createExpense(SpendingEntity s);
	public void deleteExpense(int id);
	public SpendingEntity selectExpense(int id);
	public boolean categoryIdExists(int categoryId);
	public MaxExpense findDateWithMaxExpense(int userId, String fromDate,String toDate);
	public List<TopSpendItem> findThreeItemsMaxExpense(int userId, String fromDate,String toDate);
	public ExpenseAverage findExpenseAverage(int userId, String fromDate,String toDate);
}
