package com.jwt.spring_security_jwtcasestudy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jwt.spring_security_jwtcasestudy.JpaRepository.UserRepository;
import com.jwt.spring_security_jwtcasestudy.dto.ProductResponseDTO;
import com.jwt.spring_security_jwtcasestudy.dto.UserRequestDTO;
import com.jwt.spring_security_jwtcasestudy.dto.UserResponseDTO;
import com.jwt.spring_security_jwtcasestudy.entity.Product;
import com.jwt.spring_security_jwtcasestudy.entity.User;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String register(UserRequestDTO request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userRepository.save(user);

        return "User Registered";
    }
    
    
    public List<UserResponseDTO> getAllUsers() {

        List<User> users =
                userRepository.findAll();

        List<UserResponseDTO> responseList =
                new ArrayList<>();

        for (User user : users) {

        	UserResponseDTO response =
                    new UserResponseDTO(
                    		user.getName(),
                            user.getEmail()
                    );

            responseList.add(response);
        }

        return responseList;
    }
}