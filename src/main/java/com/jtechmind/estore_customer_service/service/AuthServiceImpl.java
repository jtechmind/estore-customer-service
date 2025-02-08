package com.jtechmind.estore_customer_service.service;

import com.jtechmind.estore_customer_service.config.JwtService;
import com.jtechmind.estore_customer_service.dto.UserLoginRequest;
import com.jtechmind.estore_customer_service.dto.UserLoginResponse;
import com.jtechmind.estore_customer_service.dto.UserRegistrationDTO;
import com.jtechmind.estore_customer_service.entity.User;
import com.jtechmind.estore_customer_service.exception.DuplicateEmailException;
import com.jtechmind.estore_customer_service.exception.DuplicateUsernameException;
import com.jtechmind.estore_customer_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @Override
    public UserLoginResponse login(UserLoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return new UserLoginResponse(token,"Login successful",true);

    }

    @Override
    @Transactional
    public void resister(UserRegistrationDTO registrationRequest) {
        User user = new User();

        // Check for duplicate username
        if (userRepository.existsByUsername(registrationRequest.getUsername())) {
            throw new DuplicateUsernameException("The username is taken. Try another.");
        }

        // Check for duplicate email
        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            throw new DuplicateEmailException("Email already in use. Try another.");
        }

        user.setUsername(registrationRequest.getUsername());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        userRepository.save(user);
    }
}
