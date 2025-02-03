package com.jtechmind.estore_customer_service.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "username must be between 3 and 50 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    //Custom validation to check if password matches confirmPassword
    @AssertTrue(message = "Passwords must match")
    private boolean isPasswordMatching() {
        return password != null && password.equals(confirmPassword);
    }

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    private String email;
}
