package com.pavlo.vovnyuk.jsonplaceholder.client.api;

import com.pavlo.vovnyuk.jsonplaceholder.client.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonPlaceholderClientTest {

    private RestTemplate restTemplate;

    private JsonPlaceholderClientImpl client;

    @BeforeEach
    void setUp() {
        restTemplate = mock(RestTemplate.class);
        String url = "http://test-url";
        client = new JsonPlaceholderClientImpl(restTemplate, url);
    }

    @Test
    void shouldFetchPosts() {
        // given
        Post[] response = createPosts();
        when(restTemplate.getForEntity(anyString(), eq(Post[].class))).thenReturn(ResponseEntity.ok(response));
        // when
        List<Post> posts = client.fetchPosts();
        // then
        assertThat(posts).hasSize(2);
        assertThat(posts.get(0).title()).isEqualTo("title1");
        assertThat(posts.get(1).title()).isEqualTo("title2");
    }

    @Test
    void shouldReturnEmptyListWhenResponseBodyIsNull() {
        //given
        when(restTemplate.getForEntity(anyString(), eq(Post[].class))).thenReturn(ResponseEntity.ok(null));
        //when
        List<Post> posts = client.fetchPosts();
        //then
        assertThat(posts).isEmpty();
    }

    private Post[] createPosts() {
        return new Post[]{
                new Post(1, 1, "title1", "body1"),
                new Post(2, 2, "title2", "body2")
        };
    }
}
