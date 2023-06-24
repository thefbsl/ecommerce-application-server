package com.server.ecommerce.service;

import com.server.ecommerce.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
	CategoryDto create(CategoryDto categoryDto);
	List<CategoryDto> getAllCategory();
	void deleteCategory(int categoryId);
	CategoryDto updateCategory(int categoryId,CategoryDto categoryDto);
	CategoryDto getCategoryById(int categoryId);
	
	
	

}
