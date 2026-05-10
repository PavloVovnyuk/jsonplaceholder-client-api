package com.pavlo.vovnyuk.jsonplaceholder.client.api;

import com.pavlo.vovnyuk.jsonplaceholder.client.model.Post;

import java.util.List;

public interface JsonPlaceholderClient {
    List<Post> fetchPosts();
}
