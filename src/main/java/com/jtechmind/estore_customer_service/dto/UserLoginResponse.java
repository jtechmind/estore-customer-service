package com.jtechmind.estore_customer_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLoginResponse {
    private String token;
    private String message;
    private boolean success;
}
