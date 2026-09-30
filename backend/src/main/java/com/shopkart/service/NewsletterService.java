package com.shopkart.service;

import com.shopkart.exception.BadRequestException;
import com.shopkart.model.NewsletterSubscriber;
import com.shopkart.repository.NewsletterSubscriberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NewsletterService {

    private final NewsletterSubscriberRepository repository;

    public void subscribe(String email) {
        if (repository.existsByEmail(email)) {
            throw new BadRequestException("This email is already subscribed");
        }

        repository.save(
                NewsletterSubscriber.builder().email(email).build()
        );
    }
}
