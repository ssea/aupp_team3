package team3.service;

import java.util.List;

import org.springframework.stereotype.Service;

import team3.entity.SpendingEntity;

@Service
public interface SpendingService {
	public List<SpendingEntity> getExpensesByDate(int userId, String date);
	public SpendingEntity createExpense(SpendingEntity s);
	public void deleteExpense(int id);
	public SpendingEntity selectExpense(int id);
	public boolean categoryIdExists(int categoryId);
}
