package com.jtechmind.estore_customer_service.controller;

import com.jtechmind.estore_customer_service.dto.UserDTO;
import com.jtechmind.estore_customer_service.dto.UserLoginRequest;
import com.jtechmind.estore_customer_service.dto.UserLoginResponse;
import com.jtechmind.estore_customer_service.dto.UserRegistrationDTO;
import com.jtechmind.estore_customer_service.exception.DuplicateEmailException;
import com.jtechmind.estore_customer_service.exception.DuplicateUsernameException;
import com.jtechmind.estore_customer_service.exception.ErrorResponse;
import com.jtechmind.estore_customer_service.exception.UsernameNotFoundException;
import com.jtechmind.estore_customer_service.service.AuthService;
import com.jtechmind.estore_customer_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginRequest loginRequest) {
        try{
            return ResponseEntity.ok(authService.login(loginRequest));
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationDTO registerRequest) {
        try {
            authService.resister(registerRequest);
            return ResponseEntity.ok("User registered successfully");
        }catch (DuplicateUsernameException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (DuplicateEmailException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}
