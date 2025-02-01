package com.jtechmind.estore_customer_service.service;

import com.jtechmind.estore_customer_service.dto.UserDTO;
import com.jtechmind.estore_customer_service.dto.UserRegistrationDTO;

public interface UserService {
    UserDTO registerUser(UserRegistrationDTO userRegistrationDTO);
    UserDTO getUserById(Long userId);
    UserDTO getUserByUsername(String username);
}
