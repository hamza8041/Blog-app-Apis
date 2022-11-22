package com.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {
	
	
	
	private int cid;
	
	
	
	@NotBlank
	@Size(min = 5,max = 50,message = "Title should be of specific size")
	private String categoryTitle;
	
	private String categoryDescription;
	

}
