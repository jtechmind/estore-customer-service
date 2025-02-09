package com.jtechmind.estore_customer_service.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserLoginRequest {
    private String username;
    private String password;
}
