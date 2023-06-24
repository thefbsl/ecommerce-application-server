package com.server.ecommerce.controller;

import com.server.ecommerce.config.AppConstants;
import com.server.ecommerce.dto.ProductDto;
import com.server.ecommerce.payload.ApiResponse;
import com.server.ecommerce.payload.ProductResponse;
import com.server.ecommerce.service.ProductService;
import com.server.ecommerce.service.implementation.FileUploadImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class ProductController {
	@Autowired
	private FileUploadImp fileUpload;
	@Autowired
	private	ProductService productService;
	@Value("${product.images.path}")
	private String imagePath;
	
// upload the product Images

	@PostMapping("products/images/{productId}")
	public ResponseEntity<?>uploadImageOfProduct(@PathVariable int productId,
											 @RequestParam("product_image") MultipartFile file ) throws Exception{

		System.out.println(productId);
		ProductDto product = productService.getProduct(productId);
	  
		String imageName = null;

		try {
			imageName = fileUpload.uploadFile(imagePath,file);
			product.setImageName(imageName);
			ProductDto updateProduct= productService.updateProduct(productId,product);
			return new ResponseEntity<>(updateProduct,HttpStatus.ACCEPTED);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return new ResponseEntity<>(Map.of("message","File not upload on server"),HttpStatus.INTERNAL_SERVER_ERROR);
			}
	}
	@GetMapping("/products/images/{productId}")
	public void downloadImage(@PathVariable int productId, HttpServletResponse response) throws IOException {
		ProductDto product = productService.getProduct(productId);
		String imageName = product.getImageName();
		String fullPath = imagePath + File.separator + imageName;
		InputStream resource = fileUpload.getResource(fullPath);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		OutputStream outputStream = response.getOutputStream();
		StreamUtils.copy(resource, outputStream);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("categories/{categoryId}/product/")
	public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, @PathVariable int categoryId) {
		return new ResponseEntity<>(productService.createProduct(productDto, categoryId),HttpStatus.CREATED);
	}
	
	@GetMapping("product/")
	public ProductResponse viewAllProduct(
			@RequestParam(value="pageNumber",defaultValue="1",required = false) int pageNumber,
			@RequestParam(value="pageSize",defaultValue="2",required = false) int pageSize,
			@RequestParam(value="sortBy",defaultValue=AppConstants.SORT_BY_STRING ,required=false) String sortBy,
			@RequestParam(value="sortDir",defaultValue = AppConstants.SORT_DIR_STRING,required =false)String sortDir
			){

		return productService.getAllProducts(pageNumber,pageSize,sortBy,sortDir);
	}
	
	@GetMapping("product/{product_id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable int product_id) {
		ProductDto productDto=productService.getProduct(product_id);
		return new ResponseEntity<>(productDto,HttpStatus.OK);
	}
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("product/{product_id}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable int product_id) {
		int a = productService.deleteProduct(product_id);
		return new ResponseEntity<>(new ApiResponse("Item Deleted",true),HttpStatus.OK);
	}
	
	
	@PutMapping("product/{productId}")
	public ResponseEntity<ProductDto> update(@PathVariable int productId, @RequestBody ProductDto newProduct) {
		return new ResponseEntity<>(productService.updateProduct(productId, newProduct) ,HttpStatus.OK);
	}
	
	
	@GetMapping("category/{categoryId}/product")
	public ProductResponse findByCategory(@PathVariable int categoryId,
			 @RequestParam(value="pageSize",defaultValue="2")	  int pageSize,
			 @RequestParam(value="pageNumber",defaultValue="1")   int pageNumber
			){
		return productService.getProductByCategory(categoryId, pageSize,pageNumber);
	}
	
	@GetMapping("product/search/{name}")
	public ResponseEntity<List<ProductDto>> findByName(@PathVariable String name ){
		return new ResponseEntity<>(productService.findProduct(name),HttpStatus.OK);
	}

}
