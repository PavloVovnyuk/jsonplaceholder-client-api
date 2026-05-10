package com.pavlo.vovnyuk.jsonplaceholder.client.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pavlo.vovnyuk.jsonplaceholder.client.api.JsonPlaceholderClient;
import com.pavlo.vovnyuk.jsonplaceholder.client.model.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class PostServiceImpl implements PostService {

    private final String outputPath;

    private static final String JSON_EXTENSION = ".json";

    private final JsonPlaceholderClient client;

    private final ObjectMapper mapper;

    public PostServiceImpl(JsonPlaceholderClient client, ObjectMapper mapper,
                           @Value("${app.export.output-dir}") String outputPath) {
        this.client = client;
        this.mapper = mapper;
        this.outputPath = outputPath;
    }

    public void exportPostsToFiles() {
        log.info("Start exporting files");
        final List<Post> posts = getPostsFromPlaceholder();
        if (posts.isEmpty()) {
            log.warn("no posts to export");
            return;
        }
        posts.forEach(this::savePostToFile);
        log.info("count={} | posts were successfully wrote to files ", posts.size());
    }

    private List<Post> getPostsFromPlaceholder() {
        try {
            final List<Post> posts = client.fetchPosts();
            log.info("posts were fetched");
            return posts;

        } catch (Exception ex) {
            log.error("failed to fetch posts from api", ex);
            return List.of();
        }
    }

    private void savePostToFile(Post post) {
        try {
            mapper.writeValue(Paths.get(outputPath, post.id() + JSON_EXTENSION).toFile(), post);
        } catch (IOException e) {
            log.error("Exception occurred when writing post {} to file", post.id(), e);
        }
    }
}