package com.server.ecommerce.service.implementation;


import com.server.ecommerce.dto.ProductDto;
import com.server.ecommerce.exception.ResourceNotFoundException;
import com.server.ecommerce.model.Category;
import com.server.ecommerce.model.Product;
import com.server.ecommerce.payload.ProductResponse;
import com.server.ecommerce.repository.CategoryRepository;
import com.server.ecommerce.repository.ProductRepository;
import com.server.ecommerce.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryRepository catRepo;
	@Override
	public ProductDto createProduct(ProductDto productDto,int categoryId) {
		Category cat =  catRepo.findById(categoryId).orElseThrow(() ->
				new ResourceNotFoundException("'" + categoryId + "' is not Found"));
		Product toEntity=  mapper.map(productDto,Product.class);
		toEntity.setCategory(cat);

		Product saveEntity =  productRepository.save(toEntity);
		return   mapper.map(saveEntity,ProductDto.class);
	}

	@Override
	public ProductResponse getAllProducts(int PageNumber,int PageSize,String sortBy, String sortDir) {
		Pageable pageable=PageRequest.of(PageNumber, PageSize);
		Page<Product> page=productRepository.findAll(pageable);
		List<Product> allProducts = page.getContent();
		
		List<ProductDto> collect = allProducts.stream()
				.map((each) -> mapper.map(each,ProductDto.class)).collect(Collectors.toList());
		ProductResponse response=new ProductResponse();
		response.setContent(collect);
		response.setPageNumber(page.getNumber());
		response.setLastPage(page.isLast());
		response.setPageSize(page.getSize());
		response.setTotalElements(page.getTotalElements());
		response.setTotalPages(page.getTotalPages());
		return response;
	}

	@Override
	public ProductDto getProduct(int productId) {
	Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("User id is not present here"));
		return  mapper.map(product, ProductDto.class);
	}

	@Override
	public int deleteProduct(int productId) {
		Product p=productRepository.findById(productId).get();
		productRepository.delete(p);
		return 0;
	}

	

	@Override
	public ProductDto updateProduct(int productId, ProductDto newProductDto) {
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("'"+productId+"' this is not here"));
        product.setProductName(newProductDto.getProductName());
        product.setProductDesc(newProductDto.getProductDesc());
        product.setProductPrice(newProductDto.getProductPrice());
        product.setStock(newProductDto.isStock());
        product.setImageName(newProductDto.getImageName());
        Product save =  productRepository.save(product);
		return  mapper.map(save,ProductDto.class);
	}

	@Override
	public ProductResponse getProductByCategory(int categoryId, int pageSize, int pageNumber) {

		Category category = catRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("not Found Category Id"));
		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		Page<Product> page=productRepository.findByCategory(category,pageable);
		              List<Product> content = page.getContent();

	    List<ProductDto> dto = content.stream()
				.map((cat) -> mapper.map(cat, ProductDto.class)).collect(Collectors.toList());

	    ProductResponse response = new ProductResponse();
        response.setContent(dto);
        response.setLastPage(page.isLast());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
		return response;
	}

	@Override
	public List<ProductDto> findProduct(String productName) {
		List<Product> product = productRepository.findByProductNameContaining(productName);
		List<ProductDto> productDto = product.stream().
				map((each)-> mapper.map(each,ProductDto.class)).collect(Collectors.toList());
		return productDto;
	}

}
