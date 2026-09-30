package com.shopkart.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewsletterRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    private String email;
}
