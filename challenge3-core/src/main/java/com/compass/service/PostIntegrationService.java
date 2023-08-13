package com.compass.service;

import com.compass.clients.PostsClient;
import com.compass.clients.requests.CommentsDetails;
import com.compass.clients.requests.PostDetails;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor(onConstructor_= @Autowired)
public class PostIntegrationService {

    private final PostsClient postsClient;

    private final HistoryService historyService;

    @Async
    public CompletableFuture<Void> returnPostDetails(Long postId){
        return CompletableFuture.supplyAsync(() -> {
            historyService.postFindHistory(postId);
            historyService.postOKHistory(postId);
            return null;
        });
    }

    @Async
    public CompletableFuture<Void> returnCommentDetails(Long postId){
        return CompletableFuture.supplyAsync(() -> {
            historyService.commentsFindHistory(postId);
            historyService.commentsOKHistory(postId);
            return null;
        });
    }


}
