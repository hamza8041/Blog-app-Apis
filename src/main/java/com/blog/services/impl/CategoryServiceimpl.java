package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.CategoryRepo;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.models.Category;
import com.blog.payloads.CategoryDto;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceimpl implements CategoryService {
	
	@Autowired
	private CategoryRepo crpo;

	@Autowired
	private ModelMapper modelmapper;
	
	
	@Override
	public CategoryDto createCategory(CategoryDto cdto) {
		
		
		Category category=this.dtotoCategory(cdto);
		
		Category savedcategory=this.crpo.save(category);
		
		
		return this.categorytodto(savedcategory);
		
	}
	
	
	

	@Override
	public CategoryDto updateCategory(CategoryDto cdto, int cid) {
		Category category=this.crpo.findById(cid).
				orElseThrow(()->new ResourceNotFoundException("Category", "categoryid", cid));
		
		
		category.setCategoryTitle(cdto.getCategoryTitle());
		category.setCategoryDescription(cdto.getCategoryDescription());
		
		Category updcategory=this.crpo.save(category);
		
		CategoryDto cdto1=this.categorytodto(updcategory);
		return cdto1;
	}
	
	

	@Override
	public void deleteCategory(int cid) {
		this.crpo.deleteById(cid);
		
		
	}

	@Override
	public CategoryDto getCategory(int cid) {
		
		Category category=this.crpo.findById(cid).
				orElseThrow(()->new ResourceNotFoundException("Category", "categoryid", cid));
		
		CategoryDto cdto=this.categorytodto(category);
		
		return cdto;
	}
	

	@Override
	public List<CategoryDto> getallCategories() {
		
		
		List<Category>categories=this.crpo.findAll();
		
		List<CategoryDto>cdtos=categories.stream().map(C->this.categorytodto(C)).collect(Collectors.toList());
		return cdtos;
	}
	
	
	
	
	
	
	private Category dtotoCategory(CategoryDto cdto)
	{
		Category category=this.modelmapper.map(cdto, Category.class);
		return category;
	}
	
	
	
	private CategoryDto categorytodto(Category category)
	{
		CategoryDto cdto=this.modelmapper.map(category, CategoryDto.class);
		
		return cdto;
	}
	
	
	

}
