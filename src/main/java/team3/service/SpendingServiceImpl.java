package team3.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import team3.entity.SpendingEntity;
import team3.repo.SpendingRepo;

@Service
public class SpendingServiceImpl implements SpendingService {
	@Autowired
	SpendingRepo spendingRepo;

	@Override
	public List<SpendingEntity> getExpensesByDate(int userId, String date) {
		return spendingRepo.findByUserIdAndSpendDate(userId, date);
	}

	@Override
	public SpendingEntity createExpense(SpendingEntity s) {
		return spendingRepo.save(s);
	}

	@Override
	public void deleteExpense(int id) {
		spendingRepo.deleteById(id);
	}
	
	
	public SpendingEntity selectExpense(int id) {
		return spendingRepo.findById(id).orElse(null);
	}

	@Override
	public boolean categoryIdExists(int categoryId) {
		return spendingRepo.existsByCategoryId(categoryId);
	}

}
