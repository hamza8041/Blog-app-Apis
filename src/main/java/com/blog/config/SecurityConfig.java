package com.blog.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blog.security.CustomUserDetailService;
import com.blog.security.JWTAuthenticationEntryPoint;
import com.blog.security.JWTAuthenticationFilter;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig   {

	
	public static final String[] PUBLIC_URLS= {
			"/api/v1/auth/**",
			"/v3/api-docs/**",
			"/v2/api-docs",
			"/swagger-resources/**",
			"/swagger-ui/**",
			"/webjars/**"
	};
	
	
	@Autowired
	private CustomUserDetailService cuds;
	
	
	@Autowired
	private JWTAuthenticationEntryPoint jwtauthentry;
	
	
	@Autowired
	private JWTAuthenticationFilter filter;
	
	@Bean
	public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception
	{
		http.csrf().disable()
		.authorizeHttpRequests()
		.requestMatchers(PUBLIC_URLS).permitAll()
		.requestMatchers(HttpMethod.GET).permitAll()
		.anyRequest().
		authenticated()
		.and()
		.exceptionHandling()
		.authenticationEntryPoint(this.jwtauthentry)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.filter, UsernamePasswordAuthenticationFilter.class);
		
		http.authenticationProvider(daoauthprovider());
	
		DefaultSecurityFilterChain filterChain = http.build();
		
		return filterChain;
		
	}
	
	
	@Bean
	public AuthenticationManager authmanagerbean(AuthenticationConfiguration conf) throws Exception
	{
		return conf.getAuthenticationManager();
	}
	
	@Bean
	public DaoAuthenticationProvider daoauthprovider()
	{
		
		DaoAuthenticationProvider dao=new DaoAuthenticationProvider();
		
		dao.setUserDetailsService(this.cuds);
		dao.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		
		return dao;
		
		
	}
	
	
	@Bean
	public PasswordEncoder passwordencoder()
	{
		return NoOpPasswordEncoder.getInstance();
	}
	

}
