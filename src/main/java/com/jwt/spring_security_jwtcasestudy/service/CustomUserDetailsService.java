package com.jwt.spring_security_jwtcasestudy.service;

import com.jwt.spring_security_jwtcasestudy.JpaRepository.UserRepository;
import com.jwt.spring_security_jwtcasestudy.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Override
	/*
	 * spring security calls this class during login/authentication
	 */
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		/*
		 * this is used by authentication manager(auth manager is internally provided by
		 * spring) to match user details with db .This is where userEmail is fetched from
		 * DB and matched The username/email password matching happens here and done by
		 * automatically authmanager CustomUserDetailsService (DB FETCH HAPPENS HERE)
		 * This is where DB is accessed
		 * User is loaded here
		 */
		User user = repo.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		//return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
			//	new ArrayList<>());
		/*
		 * it tells spring  security this logged-in user has these roles/permissions
		 * it load user from db give spring security email,pwd,roles
		 *in below code its returning user details to spring security like email
		 *pwd and its role
		 */
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				List.of(new SimpleGrantedAuthority(user.getRole())));
	}
}
