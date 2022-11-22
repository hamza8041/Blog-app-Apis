package com.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	
	
	@Autowired
	private CategoryService cs;
	
	
	
	//create
	
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto>createCategory(@Valid @RequestBody CategoryDto cdto)
	{
		CategoryDto createcategory=this.cs.createCategory(cdto);
		
		return new ResponseEntity<CategoryDto>(createcategory,HttpStatus.CREATED);
	}
	
	
	
	//update
	
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto>updateCategory(@Valid @RequestBody CategoryDto cdto, @PathVariable Integer categoryId)
	{
		CategoryDto updatedCategory=this.cs.updateCategory(cdto, categoryId);
		
		return new ResponseEntity<CategoryDto>(updatedCategory,HttpStatus.OK);
	}
	
	
	
	//delete
	
	
	
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse>deleteCategory(@PathVariable Integer categoryId)
	{
		this.cs.deleteCategory(categoryId);
		
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
	}
	
	
	
	
	
	
	
	//get
	
	@GetMapping("/{categoryId}")
	
	public ResponseEntity<CategoryDto>getcategorybyId(@PathVariable Integer categoryId)
	{
		return ResponseEntity.ok(this.cs.getCategory(categoryId));
	}
	
	
	
	
	//getall
	
	@GetMapping("/")
	
	
	public ResponseEntity<List<CategoryDto>>getallCategories()
	{
		return ResponseEntity.ok(this.cs.getallCategories());
	}
	
	
	
	

}
