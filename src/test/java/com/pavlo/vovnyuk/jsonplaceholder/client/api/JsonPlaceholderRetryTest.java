package com.pavlo.vovnyuk.jsonplaceholder.client.api;

import com.pavlo.vovnyuk.jsonplaceholder.client.model.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

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
        when(restTemplate.getForEntity(anyString(), eq(Post[].class)))
                .thenThrow(HttpServerErrorException.InternalServerError.class);
        //when
        try {
            client.fetchPosts();
        } catch (Exception ignored) {
        }
        //then
        verify(restTemplate, atLeast(3)).getForEntity(anyString(), eq(Post[].class));
    }
}