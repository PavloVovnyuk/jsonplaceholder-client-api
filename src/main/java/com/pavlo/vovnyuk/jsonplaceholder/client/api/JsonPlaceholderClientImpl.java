package com.pavlo.vovnyuk.jsonplaceholder.client.api;

import com.pavlo.vovnyuk.jsonplaceholder.client.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JsonPlaceholderClientImpl implements JsonPlaceholderClient {

    private final String url;

    private final RestTemplate restTemplate;

    public JsonPlaceholderClientImpl(RestTemplate restTemplate, @Value("${app.client.posts-url}") String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    @Retryable(
            retryFor = HttpServerErrorException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000, multiplier = 2.0)
    )
    public List<Post> fetchPosts() {
        log.info("Fetching posts from {}", url);
        final ResponseEntity<Post[]> response = restTemplate.getForEntity(url, Post[].class);
        return Optional.ofNullable(response.getBody()).map(Arrays::asList).orElse(List.of());
    }
}