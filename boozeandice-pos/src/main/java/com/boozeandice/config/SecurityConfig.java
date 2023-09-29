package com.boozeandice.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

	private static final Logger logger = LogManager.getLogger(SecurityConfig.class);

	@Bean
	// authentication
	public UserDetailsService userDetailsServce() {
		logger.debug("userDetailsServce() -> ");
		return new UserDetailsServiceImpl();

//		UserDetails admin = User.withUsername("lxbordo")
//				.password(encoder.encode("lxbordo"))
//				.roles("ADMIN")
//				.build();
//		
//		UserDetails user = User.withUsername("lxbordo-user")
//				.password(encoder.encode("lxbordo"))
//				.roles("CASHIER")
//				.build();
//
//		return new InMemoryUserDetailsManager(admin,user);
	}

	@Bean
	// authorization
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf().disable().authorizeHttpRequests()
				.requestMatchers("/plugins/**", "/dist/**", "/custom/**", "/images/**", "/chart/**", "/login").permitAll().and()
				.authorizeHttpRequests().requestMatchers("/**").authenticated().and().formLogin().loginPage("/login")
				.defaultSuccessUrl("/").failureHandler(customAuthenticationFailureHandler()).and().exceptionHandling()
				.accessDeniedPage("/access_denied").and().build();

	}

	@Bean
	public AuthenticationFailureHandler customAuthenticationFailureHandler() {
		return new CustomAuthenticationFailureHandler();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsServce());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

}
