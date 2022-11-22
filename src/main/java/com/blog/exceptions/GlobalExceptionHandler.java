package com.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse>resourceNotFoundExceptionHandler(ResourceNotFoundException ex)
	{
		String message=ex.getMessage();
		ApiResponse res=new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>>handlemethodargs(MethodArgumentNotValidException mae)
	{
		Map<String,String> resp=new HashMap<>();
		mae.getBindingResult().getAllErrors().forEach((error)->
		
		{
			String field = ((FieldError)error).getField();
			String errormessage = error.getDefaultMessage();
			
			resp.put(field, errormessage);
		}
		
				
				
				);
		
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(APIException.class)
	public ResponseEntity<ApiResponse>handleAPIException(APIException ex)
	{
		String message=ex.getMessage();
		ApiResponse res=new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(res,HttpStatus.BAD_REQUEST);
	}
	

	
	
}
