package team3.response;

import team3.entity.CategoryEntity;

public interface TopSpendItem {
	int getCategoryId();
	
	String getCategoryName();
	
	double getMaxAmountKhr();

	double getMaxAmountUsd();
}
