package com.compass.clients;


import com.compass.clients.requests.CommentsDetails;
import com.compass.clients.requests.PostDetails;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "${challenge3.clients.name}",
        url = "${challenge3.clients.url}",
        primary = false)
public interface PostsClient {

    @GetMapping("/posts/{postId}")
    PostDetails getPostDetails(@PathVariable("postId") @NotNull Long postId);

    @GetMapping("/posts/{postId}/comments")
    Set<CommentsDetails> getCommentsDetails(@PathVariable("postId") @NotNull Long postId);


}
