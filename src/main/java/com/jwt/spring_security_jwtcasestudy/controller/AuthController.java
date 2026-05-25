package com.jwt.spring_security_jwtcasestudy.controller;

import com.jwt.spring_security_jwtcasestudy.JpaRepository.UserRepository;
import com.jwt.spring_security_jwtcasestudy.dto.*;
import com.jwt.spring_security_jwtcasestudy.entity.User;
import com.jwt.spring_security_jwtcasestudy.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
/*
 * authentication starts here flows to authentication manager which internally use CustomUserDetailService class
 * -> fetches user by email
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody AuthRequest request) {
    	/*
    	 * user will get registered here with email address and pwd(encoded via password encoder)
    	 */
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encoder.encode(request.getPassword()));
        /*
         * adding below for authorization we should not trust the role
         * coming from the client request so we set in controller instead of getting in 
         * request itself.
         */
        user.setRole("ROLE_USER");
       // user.setRole("ROLE_ADMIN");
        repo.save(user);

        return "User registered successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
    	/*
    	 * Controller ONLY forwards credentials it does not check db
    	 * AuthenticationManager (SPRING INTERNAL CORE- central entry point for authentication
    	 * centralized authentication logic)
    	 * This delegates to: DaoAuthenticationProvider
    	 * AuthenticationManager triggers UserDetailsService which calls repo
    	 * Spring Security internally calls: password matches() here with RawPasswd with encoded one 
    	 * Due to AuthenticationManager no manual password checking in controller
    	 * below code is verifying the email and pwd with respect to db values
    	 * (UseDetailSErvice + Authentication Manager)
    	 */
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                		request.getEmail(),
                        request.getPassword()
                        
                )
        );
        
        /*
         * token has username/email , roles(user/admin), expiry time
         * header.payload.signature
         */
        String token = jwtUtil.generateToken(request.getEmail());

        return new AuthResponse(token);
    }
}