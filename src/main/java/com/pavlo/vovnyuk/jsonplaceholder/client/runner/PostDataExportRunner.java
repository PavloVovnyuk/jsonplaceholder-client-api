package com.pavlo.vovnyuk.jsonplaceholder.client.runner;

import com.pavlo.vovnyuk.jsonplaceholder.client.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostDataExportRunner implements CommandLineRunner {

    private final PostService postService;

    @Override
    public void run(String... args) {
        log.info("App started");
        postService.exportPostsToFiles();
        log.info("App finished");
    }
}
