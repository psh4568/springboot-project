package com.jwt.spring_security_jwtcasestudy.config;
import com.jwt.spring_security_jwtcasestudy.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	/*This class is a rule book used at setup time - runs only when app starts not executed per request
    	 * permitAll -- here for auth services because login and registration API's must be accessible before authentication
    	 * without jwt token
    	 * for instance : user wants to login post : /auth/Login-at this point user does not have token yet.
    	 * so these comes under public api's not protected
    	 */
    	/*
    	 * Which role can access which API checks here role based checking
    	 * Spring convert all rules mentioned below into Authorization rules inside the filter chain
    	 * Authorization filter uses security config rules
    	 */
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()
                        /*
                         * accessed without token
                         */
                        .requestMatchers("/h2-console/**").permitAll()
                        /*
                         * adding below lines for role based url by passing
                         * 
                         */
                        .requestMatchers(HttpMethod.GET, "/api/products/**")
                        .hasAnyRole("USER", "ADMIN")
                        /*
                         * if we use hasRole("ADMIN") spring internally checks ROLE_ADMIN so i db
                         * or UseDetails role should be ROLE_AMIN & ROLE_USER
                         *hasRole("ADMIN") internally becomes ROLE_ADMIN
                         */
                        .requestMatchers(HttpMethod.POST, "/api/products/**")
                        .hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
				/*
				 * .exceptionHandling(ex-> ex.accessDeniedHandler((request, response,
				 * accessDeniedException) -> { if(response.getStatus() == 403) {
				 * response.setContentType("text/plain");
				 * response.getWriter().write("Only Admin can perform this action"); } } ))
				 */
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}