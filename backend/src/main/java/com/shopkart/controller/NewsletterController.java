package com.shopkart.controller;

import com.shopkart.dto.NewsletterRequest;
import com.shopkart.service.NewsletterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/newsletter")
@RequiredArgsConstructor
public class NewsletterController {

    private final NewsletterService newsletterService;

    @PostMapping("/subscribe")
    public ResponseEntity<Map<String, String>> subscribe(@Valid @RequestBody NewsletterRequest request) {
        newsletterService.subscribe(request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Subscribed successfully"));
    }
}
