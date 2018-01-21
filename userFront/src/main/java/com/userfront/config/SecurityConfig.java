package com.userfront.config;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserSecurityService userSercurityService;
	
	//use this parameter to encode my psswords :D
	private static final String Salt = "NissanMaxima2004:D";
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(16, new SecureRandom(Salt.getBytes()));
	}
	
	//list of path we like to access publicly without any restriction
	private static final String[] PUBLLIC_MATHERS = {
			"/webjars/**",
			"/css/**",
			"/js/**",
			"/images/**",
			"/",
			"/about/**",
			"/contact/**",
			"/error/**",
			"/console/**",
			"/signup",
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers(PUBLLIC_MATHERS)
			.permitAll()
			.anyRequest()
			.authenticated();
		
		http
			.csrf().disable().cors().disable()
			.formLogin().failureUrl("/index?error").defaultSuccessUrl("/userFront").loginPage("/index").permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/index?logout").deleteCookies("remember-me").permitAll()
			.and()
			.rememberMe();
		
	}
}



































