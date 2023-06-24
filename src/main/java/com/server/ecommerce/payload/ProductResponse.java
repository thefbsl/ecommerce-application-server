package com.server.ecommerce.payload;

import com.server.ecommerce.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
	private List<ProductDto> content;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	private boolean isLastPage;
	private int pageNumber;
}
