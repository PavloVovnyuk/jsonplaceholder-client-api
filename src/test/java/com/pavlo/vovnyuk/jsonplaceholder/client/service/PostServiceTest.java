package com.pavlo.vovnyuk.jsonplaceholder.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavlo.vovnyuk.jsonplaceholder.client.api.JsonPlaceholderClient;
import com.pavlo.vovnyuk.jsonplaceholder.client.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostServiceTest {

    private JsonPlaceholderClient client;

    private PostServiceImpl service;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        client = mock(JsonPlaceholderClient.class);
        ObjectMapper mapper = new ObjectMapper();
        service = new PostServiceImpl(client, mapper, tempDir.toString());
    }

    @Test
    void shouldExportPostsToFiles() {
        // given
        when(client.fetchPosts()).thenReturn(createPosts());
        // when
        service.exportPostsToFiles();
        // then
        File file1 = tempDir.resolve("1.json").toFile();
        File file2 = tempDir.resolve("2.json").toFile();

        assertThat(file1).exists();
        assertThat(file2).exists();
    }

    @Test
    void shouldNotCreateFilesWhenNoPosts() {
        // given
        when(client.fetchPosts()).thenReturn(List.of());
        // when
        service.exportPostsToFiles();
        // then
        assertThat(tempDir.toFile().listFiles()).isEmpty();
    }

    private List<Post> createPosts() {
        return Arrays.asList(
                new Post(1, 1, "title1", "body1"),
                new Post(2, 2, "title2", "body2")
        );
    }
}