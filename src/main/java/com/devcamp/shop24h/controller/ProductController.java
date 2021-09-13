package com.devcamp.shop24h.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.devcamp.shop24h.model.Product;
import com.devcamp.shop24h.model.ProductLine;
import com.devcamp.shop24h.repository.ProductLineRepo;
import com.devcamp.shop24h.repository.ProductRepo;

@CrossOrigin
@RestController
public class ProductController {
	@Autowired
	ProductRepo productRepo;
	@Autowired
	ProductLineRepo productLineRepo;
	
//	lấy tất cả sản phẩm
	@GetMapping("/products")
	public ResponseEntity<Object> getAllProducts() {
		try {
			return new ResponseEntity<>(productRepo.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	đếm số lượng sản phẩm để phân trang
	@GetMapping("/products/counts")
	public ResponseEntity<Object> getCountTotalProduct() {
		try {
			return new ResponseEntity<>(productRepo.countTotalPoduct(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	lấy sản phẩm phân trang
	@GetMapping("/products/pages/{page}")
	public ResponseEntity<Object> getPageableProduct(@PathVariable int page) {
		try {
			return new ResponseEntity<>(productRepo.getProductPageable(PageRequest.of(page, 6)), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	lấy theo tên sản phẩm
	@GetMapping("/products/name/{name}")
	public ResponseEntity<Object> getProductByName(@PathVariable String name) {
		try {
			return new ResponseEntity<>(productRepo.getProductByName(name), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	lấy sản phẩm related
	@GetMapping("/products/related")
	public ResponseEntity<Object> getRelatedProduct() {
		try {
			return new ResponseEntity<>(productRepo.getRelatedProduct(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	lấy product by id
	@GetMapping("/products/{productId}")
	public ResponseEntity<Object> getProductById(@PathVariable Integer productId) {
		try {
			Optional<Product> productFound = productRepo.findById(productId);
			if (productFound.isPresent()) {
				return new ResponseEntity<>(productFound.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	lọc sản phẩm theo loại
	@GetMapping("product-lines/{productLineId}/products")
	public ResponseEntity<Object> getLatestProduct(@PathVariable int productLineId) {
		try {
			return new ResponseEntity<>(productRepo.getByProductLineId(productLineId), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	lọc sản phẩm theo giá
	@GetMapping("products/price")
	public ResponseEntity<Object> getProductByPrice(@RequestParam int minPrice
			, @RequestParam int maxPrice) {
		try {
			return new ResponseEntity<>(productRepo.getProductByPrice(minPrice, maxPrice), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	tạo mới product
	@PostMapping("/product-lines/{productLineId}/products")
	public ResponseEntity<Object> createProduct(@Valid @RequestBody Product newProduct,
			@PathVariable Integer productLineId) {
		try {
			if (productLineId != 0) {
				Optional<ProductLine> productLineFound = productLineRepo.findById(productLineId);
				newProduct.setProductLineId(productLineFound.get());
				return new ResponseEntity<>(productRepo.save(newProduct), HttpStatus.CREATED);	
			} else {
				return new ResponseEntity<>(productRepo.save(newProduct), HttpStatus.CREATED);				
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	sửa product
	@PutMapping("/products/{productId}")
	public ResponseEntity<Object> updateProduct(@PathVariable Integer productId, @Valid @RequestBody Product newProduct) {
		try {
			Optional<Product> productFound = productRepo.findById(productId);
			if (productFound.isPresent()) {
				Product updateProduct = productFound.get();
				updateProduct.setBuyPrice(newProduct.getBuyPrice());
				updateProduct.setProductCode(newProduct.getProductCode());
				updateProduct.setProductDescription(newProduct.getProductDescription());
				updateProduct.setProductName(newProduct.getProductName());
				updateProduct.setProductScale(newProduct.getProductScale());
				updateProduct.setProductVendor(newProduct.getProductVendor());
				updateProduct.setQuantityInStock(newProduct.getQuantityInStock());
				updateProduct.setUrlImage(newProduct.getUrlImage());
				updateProduct.setIsRelated(newProduct.getIsRelated());
				return new ResponseEntity<>(productRepo.save(updateProduct), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	xóa product by id
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<Object> deleteProductById(@PathVariable Integer productId) {
		try {
			Optional<Product> productFound = productRepo.findById(productId);
			if (productFound.isPresent()) {
				productRepo.deleteById(productId);
				return new ResponseEntity<>( HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	xóa tất cả product
	@DeleteMapping("/products")
	public ResponseEntity<Object> deleteAllProduct() {
		try {
			productRepo.deleteAll();
			return new ResponseEntity<>( HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
