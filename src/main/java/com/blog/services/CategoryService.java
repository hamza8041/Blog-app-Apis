package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
	
	
	 CategoryDto createCategory(CategoryDto cdto);
	 
	 CategoryDto updateCategory(CategoryDto cdto,int cid);
	 
	 void deleteCategory(int cid);
	 
	 CategoryDto getCategory(int cid);
	 
	 
	 List<CategoryDto>getallCategories();
	 

}
