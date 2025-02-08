package com.jtechmind.estore_customer_service.service;

import com.jtechmind.estore_customer_service.dto.UserLoginRequest;
import com.jtechmind.estore_customer_service.dto.UserLoginResponse;
import com.jtechmind.estore_customer_service.dto.UserRegistrationDTO;

public interface AuthService {
    public UserLoginResponse login(UserLoginRequest loginRequest);
    public void resister(UserRegistrationDTO registrationRequest);
}
