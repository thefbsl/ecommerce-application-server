package com.server.ecommerce.service;

import com.server.ecommerce.dto.ProductDto;
import com.server.ecommerce.payload.ProductResponse;

import java.util.List;

public interface ProductService {
	
	ProductDto createProduct(ProductDto productDto,int categoryId);
	ProductResponse getAllProducts(int PageNumber,int pageSize,String sortBy,String sortDir);
	ProductDto getProduct(int productId);
	int deleteProduct(int productId);
	ProductDto updateProduct(int productId, ProductDto newProductDto);
	ProductResponse getProductByCategory(int categoryId, int pageSize, int pageNumber);
	List<ProductDto> findProduct(String productName);


}
