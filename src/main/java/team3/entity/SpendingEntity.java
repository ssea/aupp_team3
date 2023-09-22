package team3.entity;

import lombok.*;
import team3.repo.CategoryRepo;
import team3.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "tbl_spending")
public class SpendingEntity {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "user_id") int userId;
    @Column(name = "category_id") int categoryId;
    @Column(name = "amount_khr") double amountKhr;
    @Column(name = "amount_usd") double amountUsd;
    @Column(name = "description") String description;
    @Column(name = "spend_date") String spendDate;
    @Column(name = "type") String type;
    
    
    @Transient
    CategoryEntity category;
    
    public SpendingEntity() {}
	public SpendingEntity(int userId, int categoryId, double amountKhr, double amountUsd, String description,
			String spendDate, String type) {
		super();
		this.userId = userId;
		this.categoryId = categoryId;
		this.amountKhr = amountKhr;
		this.amountUsd = amountUsd;
		this.description = description;
		this.spendDate = spendDate;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public double getAmountKhr() {
		return amountKhr;
	}
	public void setAmountKhr(double amountKhr) {
		this.amountKhr = amountKhr;
	}
	public double getAmountUsd() {
		return amountUsd;
	}
	public void setAmountUsd(double amountUsd) {
		this.amountUsd = amountUsd;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSpendDate() {
		return spendDate;
	}
	public void setSpendDate(String spendDate) {
		this.spendDate = spendDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public CategoryEntity getCategory() {
		return category;
	}
	public void setCategory(CategoryEntity category) {
		this.category = category;
	}
}
