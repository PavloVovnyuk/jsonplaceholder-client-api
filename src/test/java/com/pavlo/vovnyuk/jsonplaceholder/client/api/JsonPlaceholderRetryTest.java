package com.pavlo.vovnyuk.jsonplaceholder.client.api;

import com.pavlo.vovnyuk.jsonplaceholder.client.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class JsonPlaceholderRetryTest {

    @MockitoBean
    private RestTemplate restTemplate;

    @Autowired
    private JsonPlaceholderClient client;

    @Test
    void shouldRetryThreeTimes() {
        //given
        AtomicInteger counter = new AtomicInteger();
        when(restTemplate.getForEntity(anyString(), eq(Post[].class)))
                .thenAnswer(inv -> {
                    counter.incrementAndGet();
                    throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
                });
        //when
        assertThrows(HttpServerErrorException.class, () -> client.fetchPosts());
        //then
        verify(restTemplate, atLeast(3)).getForEntity(anyString(), eq(Post[].class));
    }
}