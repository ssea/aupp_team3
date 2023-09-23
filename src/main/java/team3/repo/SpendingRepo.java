package team3.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import team3.entity.SpendingEntity;
import team3.response.ExpenseAverage;
import team3.response.MaxExpense;
import team3.response.TopSpendItem;

public interface SpendingRepo extends JpaRepository<SpendingEntity, Integer> {
	List<SpendingEntity> findByUserIdAndSpendDate(int userId, String date);

	boolean existsByCategoryId(int categoryId);

	@Query(value = "SELECT DATE_FORMAT(spend_date, \"%d-%m-%Y\") as spendDate, SUM(amount_khr) maxAmountKhr, SUM(amount_usd) maxAmountUsd FROM tbl_spending s WHERE user_id = :userId AND s.spend_date >= :fromDate AND s.spend_date < :toDate + INTERVAL 1 DAY GROUP BY s.spend_date ORDER BY maxAmountKhr DESC LIMIT 1", nativeQuery = true)
	MaxExpense findSpendDateWithMaxExpense(int userId, String fromDate, String toDate);

	@Query(value = "SELECT s.category_id AS categoryId, c.name AS categoryName, SUM(s.amount_khr) maxAmountKhr, SUM(s.amount_usd) maxAmountUsd FROM tbl_spending s INNER JOIN tbl_category c ON s.category_id = c.id WHERE s.user_id = :userId AND s.spend_date >= :fromDate AND s.spend_date < :toDate + INTERVAL 1 DAY GROUP BY s.category_id ORDER BY maxAmountKhr DESC LIMIT 3", nativeQuery = true)
	List<TopSpendItem> findThreeItemsWithMaxExpense(int userId, String fromDate, String toDate);

	@Query(value = "SELECT IFNULL(SUM(amount_khr),0) sumAmountKhr, IFNULL(SUM(amount_usd),0) sumAmountUsd, IFNULL(AVG(amount_khr),0) avgAmountKhr, IFNULL(AVG(amount_usd),0) avgAmountUsd, COUNT(*) spendCount FROM tbl_spending s WHERE s.user_id = :userId AND s.spend_date >= :fromDate AND s.spend_date < :toDate + INTERVAL 1 DAY;", nativeQuery = true)
	ExpenseAverage findExpenseAverage(int userId, String fromDate, String toDate);
}
