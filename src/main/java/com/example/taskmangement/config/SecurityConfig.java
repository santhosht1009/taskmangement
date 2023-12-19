package com.example.taskmangement.config;




import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.web.filter.CorsFilter;

import com.example.taskmangement.dto.Role;
import com.example.taskmangement.security.CustomUserDetailsService;
import com.example.taskmangement.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



import lombok.RequiredArgsConstructor;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity 
@RequiredArgsConstructor
public class SecurityConfig {

	

	    private final JwtAuthenticationFilter authFilter; 
	  
		 
		 
		 private final CustomUserDetailsService customUserDetailsService;
		 

	    // Configuring HttpSecurity 
	    @Bean
	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { 


	    	http.csrf(AbstractHttpConfigurer::disable)
	    	   .authorizeHttpRequests(req ->
	    	   
               req.requestMatchers("/api/v1/auth/**")
                       .permitAll()
                       .requestMatchers("/swagger-ui/**","/v3/api-docs/**")
                       .permitAll()
//                       .requestMatchers(HttpMethod.GET)
//       				.permitAll()	  
//                       .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
//                       .requestMatchers("/api/users/**").hasAuthority("USER")
                       
                       .requestMatchers("/api/v1/admin/**").hasAuthority(Role.ADMIN.name())
                       .requestMatchers("/api/v1/users/**").hasAuthority(Role.USER.name())
                       .anyRequest()
                       .authenticated()
       )
	    	
	    	   .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	    	   .authenticationProvider(authenticationProvider())
	    	.addFilterBefore(authFilter,UsernamePasswordAuthenticationFilter.class);
	    	
return http.build();
	    } 
	    
	    
	    
	    @Bean
	    public AuthenticationProvider authenticationProvider() { 
	        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider(); 
	        authenticationProvider.setUserDetailsService(customUserDetailsService);
	        authenticationProvider.setPasswordEncoder(passwordEncoder());
	        return authenticationProvider; 
	    } 


	    // Password Encoding 
	    @Bean
	    public PasswordEncoder passwordEncoder() { 
	        return new BCryptPasswordEncoder(); 
	    } 


	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { 
	        return config.getAuthenticationManager(); 
	    } 

	    
	    @Bean
	    public FilterRegistrationBean coresFilter() {
	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

	        CorsConfiguration corsConfiguration = new CorsConfiguration();
	        corsConfiguration.setAllowCredentials(true);
	        corsConfiguration.addAllowedOriginPattern("*");
	        corsConfiguration.addAllowedHeader("Authorization");
	        corsConfiguration.addAllowedHeader("Content-Type");
	        corsConfiguration.addAllowedHeader("Accept");
	        corsConfiguration.addAllowedMethod("POST");
	        corsConfiguration.addAllowedMethod("GET");
	        corsConfiguration.addAllowedMethod("DELETE");
	        corsConfiguration.addAllowedMethod("PUT");
	        corsConfiguration.addAllowedMethod("OPTIONS");
	        corsConfiguration.setMaxAge(3600L);

	        source.registerCorsConfiguration("/**", corsConfiguration);

	        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));

	        bean.setOrder(-110);

	        return bean;
	    }

	
}
