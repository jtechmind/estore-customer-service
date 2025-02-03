package com.jtechmind.estore_customer_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoleDTO {
    @NotBlank(message = "Role name is required")
    @Size(min = 3, max = 50, message = "Role name must be between 3 and 50 characters")
    private String name;
}
