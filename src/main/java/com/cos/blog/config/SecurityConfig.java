package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


//@EnableGlobalMethodSecurity(prePostEnabled = true)어느 진입단계에 이 필터를 적용 시킬지 설정해준다
//@EnableWebSecurity  이렇게 해줘야 시큐리티 인걸 알다준다.
//@Configuration메모리에 뜬다.
@Configuration   
@EnableWebSecurity    
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Bean    // 이렇게 붙여놓으면, 스프링이 관리해서  나중에 쓸때 DI 해서 쓰면된다.
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	
	  @Override
	   protected void configure(HttpSecurity http) throws Exception { //모든 요청을 받는다. 
		  http.csrf().disable();
		  
	      http.authorizeRequests()
	         .antMatchers(
	        		 "/user/profile/**",
	        		 "/post/write/**",
	        		 "/post/update/**",
	        		 "/post/delete/**",
	        		 "/post/detail/**").authenticated()
	         .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
	         .anyRequest().permitAll()
	      .and() //안드로이드 앱 같은 경우는 폼 로그인이 아니다. 웹에서는 폼 로그인방식을 많이 쓴다.
	         .formLogin()
	         .loginPage("/user/login") //post 만 낚아챔// 그냥 주소로 가고싶으면 defaultSuccessUrl()로 가면 되고, 그 전에 무언갈 수행하고싶으면successHandler을 사용하여 new 할수 있다.
	         .loginProcessingUrl("/user/login") // POST만 낚아 챔!!!!!!!!!! Get 방식은 안낚아챈다.
	         .defaultSuccessUrl("/");// successHamdler를 사용할 수 있음.
	      
	   }
	}