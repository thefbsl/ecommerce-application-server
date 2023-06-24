package com.server.ecommerce.service.implementation;

import com.server.ecommerce.dto.CategoryDto;
import com.server.ecommerce.exception.ResourceNotFoundException;
import com.server.ecommerce.model.Category;
import com.server.ecommerce.repository.CategoryRepository;
import com.server.ecommerce.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImp implements CategoryService {
	@Autowired
    private CategoryRepository categoryRepository;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public CategoryDto create(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		Category saveCategory = categoryRepository.save(category);
		return modelMapper.map(saveCategory,CategoryDto.class);
	}
	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> findAll = categoryRepository.findAll();
		return findAll.stream().map((cat) -> modelMapper.map(cat,CategoryDto.class)).collect(Collectors.toList());
	}

	@Override
	public void deleteCategory(int categoryId) {
		Category cat = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not Found"));
		categoryRepository.delete(cat);
	}

	@Override
	public CategoryDto updateCategory(int categoryId, CategoryDto categoryDto) {
		Category category = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found"));
		category.setTitle(categoryDto.getTitle());
		Category save = categoryRepository.save(category);
		return modelMapper.map(save,CategoryDto.class);
	}

	@Override
	public CategoryDto getCategoryById(int categoryId) {
		Category findById = categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found") );
		return modelMapper.map(findById,CategoryDto.class);
	}

}
