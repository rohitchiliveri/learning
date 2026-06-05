package net.javaguides.springboot.controller;



import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class SecurityConfig {
	
	

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService) {
        return new JwtAuthenticationFilter(jwtService);
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123") // {noop} means plain text, for testing
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("user")
                .password("{noop}user123")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtFilter) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/authenticate").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}


//@Configuration
//public class SecurityConfig {
//	
//	//private final JwtAuthenticationFilter jwtAuthFilter;
//
//   
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService) {
//        return new JwtAuthenticationFilter();
//    }
//
//    
////    @Bean
////    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http
////            // Disable CSRF for APIs (important for Angular calls)
////            .csrf(csrf -> csrf.disable())
////            // Configure authorization rules
////            .authorizeHttpRequests(auth -> auth
////                .requestMatchers("/api/v1/**").permitAll() // allow all employee endpoints
////                .anyRequest().authenticated()             // secure everything else
////            );
////            // Optional: enable basic auth for testing
////            
////
////        return http.build();
////    }
//	
//	
//	 @Bean
//	    public UserDetailsService userDetailsService() {
//	        UserDetails admin = User.withDefaultPasswordEncoder()
//	                .username("admin")
//	                .password("admin123")
//	                .roles("ADMIN")
//	                .build();
//
//	        UserDetails user = User.withDefaultPasswordEncoder()
//	                .username("user")
//	                .password("user123")
//	                .roles("USER")
//	                .build();
//
//	        return new InMemoryUserDetailsManager(admin, user);
//	    }
////
////	    @Bean
////	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////	        http
////	            .csrf(csrf -> csrf.disable())
////	            .authorizeHttpRequests(auth -> auth
////	                .requestMatchers("/api/v1/employees/**").hasRole("ADMIN") // only ADMIN can manage employees
////	                .anyRequest().authenticated()
////	            ); // use basic authentication
////
////	        return http.build();
////	    }
//	    
//	 
//	 //working
//	   /*@Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	        http
//	            .csrf(csrf -> csrf.disable())
//	            .authorizeHttpRequests(auth -> auth
//	                .requestMatchers("/api/v1/**").hasRole("ADMIN") // only ADMIN can manage employees
//	                .anyRequest().authenticated()
//	            )
//	            .httpBasic(Customizer.withDefaults());  // <-- enable basic authentication
//
//	        return http.build();
//	    }*/
//	    
//	   
//	 //working
//	   /*
//	    @Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//	        http
//	            .csrf(csrf -> csrf.disable()) // disable CSRF for APIs
//	            .authorizeHttpRequests(auth -> auth
//	                .anyRequest().permitAll() // allow all requests without authentication
//	            );
//
//	        return http.build();
//	    }
//	    */
//	   
//	 /*@Bean
//	 public SecurityFilterChain securityFilterChain(HttpSecurity http,
//	                                                JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
//	     http
//	         .csrf(csrf -> csrf.disable())
//	         .authorizeHttpRequests(auth -> auth
//	             .requestMatchers("/api/v1/**").hasRole("ADMIN")
//	             .anyRequest().authenticated()
//	         )
//	         .httpBasic(Customizer.withDefaults())
//	         .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//
//	     return http.build();
//	 }*/
//	 
//	 @Bean
//	    public SecurityFilterChain securityFilterChain(HttpSecurity http,
//	                                                   JwtAuthenticationFilter jwtFilter) throws Exception { 
//		 
//		 System.out.println("Security Config");
//	        http.csrf(csrf -> csrf.disable())
//	            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//	            .authorizeHttpRequests(auth -> auth
//	                .requestMatchers("/authenticate").permitAll()
//	                //.requestMatchers("/employees/**").hasRole("ADMIN")
//	                .anyRequest().authenticated()
//	            );
//	            //.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//	        return http.build();
//	    }
//
//	 // ✅ Modern way to expose AuthenticationManager
//	    @Bean
//	    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
//	            throws Exception {
//	        return authenticationConfiguration.getAuthenticationManager();
//	    }
//	    
//	    @Bean
//	    public CorsConfigurationSource corsConfigurationSource() {
//	        CorsConfiguration configuration = new CorsConfiguration();
//	        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Angular dev server
//	        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//	        configuration.setAllowedHeaders(List.of("*"));
//	        configuration.setAllowCredentials(true);
//
//	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	        source.registerCorsConfiguration("/**", configuration);
//	        return source;
//	    }
//	    
//	    @Bean
//	    public WebMvcConfigurer corsConfigurer() {
//	        return new WebMvcConfigurer() {
//	            @Override
//	            public void addCorsMappings(CorsRegistry registry) {
//	                registry.addMapping("/**")
//	                    .allowedOrigins("http://localhost:4200")
//	                    .allowedMethods("GET","POST","PUT","DELETE")
//	                    .allowCredentials(true);
//	            }
//	        };
//	    }
//
//
//	 
//
//
//
//}