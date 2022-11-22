package com.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstants;
import com.blog.dao.RoleRepo;
import com.blog.models.Role;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication

public class BlogApplication implements CommandLineRunner  {
	
	
	
	@Autowired
	private RoleRepo rolereop;

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
	
	
	
	
	
	
	@Bean
	public ModelMapper modelmapper()
	{
		return new ModelMapper();
	}






	@Override
	public void run(String... args) throws Exception {
		
		try
		{
			Role role=new Role();
			
			role.setId(AppConstants.NORMAL_USER);
			role.setRole("NORMAL_USER");
			
			Role role1=new Role();
			
			role1.setId(AppConstants.ADMIN_USER);
			role1.setRole("ADMIN_USER");
			
			
			List<Role> roles = List.of(role,role1);
			
			List<Role> savedroles = this.rolereop.saveAll(roles);
			
			savedroles.forEach(r->
			{
				System.out.println(r.getRole());
			});
		}
		
		catch(Exception e)
		{
			
		}
		
		
	}
	
	


}
	


