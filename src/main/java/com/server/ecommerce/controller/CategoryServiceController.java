package com.server.ecommerce.controller;

import com.server.ecommerce.dto.CategoryDto;
import com.server.ecommerce.payload.ApiResponse;
import com.server.ecommerce.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cat")
@CrossOrigin("*")
public class CategoryServiceController {
	@Autowired
	private CategoryService categoryService;
    @PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto ) {
		return new ResponseEntity<>(categoryService.create(categoryDto),HttpStatus.CREATED);
	}
    @PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	ResponseEntity<CategoryDto> Update(@RequestBody CategoryDto categoryDto ,@PathVariable int id){
		return new ResponseEntity<>(categoryService.updateCategory(id, categoryDto), HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/")
	ResponseEntity<List<CategoryDto>> getAll(){
		return new ResponseEntity<>(categoryService.getAllCategory(), HttpStatus.OK) ;
	}
	
	@GetMapping("/single/{id}")
	ResponseEntity<CategoryDto>getSingle(@Valid @PathVariable int id){
		return new ResponseEntity<>(categoryService.getCategoryById(id),HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	ResponseEntity<ApiResponse>delete(@Valid @PathVariable int id){
		categoryService.deleteCategory(id);
		return new ResponseEntity<>(new ApiResponse("User Delete" + id,true),HttpStatus.OK);
	}

}
