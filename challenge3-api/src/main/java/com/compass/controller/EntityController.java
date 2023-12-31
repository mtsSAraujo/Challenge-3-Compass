package com.compass.controller;

import com.compass.entity.historyStatus.HistoryStatus;
import com.compass.producers.CreatedProducer;
import com.compass.producers.PostMessage;
import com.compass.service.HistoryService;
import com.compass.service.PostService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor(onConstructor_= @Autowired)
@Validated
public class EntityController {

    private final HistoryService historyService;

    private final PostService postService;

    private final CreatedProducer createdProducer;


    @PostMapping("/{postId}")
    public ResponseEntity<Object> postRequest(@PathVariable("postId")@NotNull(message = "The ID can't be null!")
                                              @Min(value = 1, message = "The ID must be greater than or equal to 1")
                                              @Max(value = 100, message = "The ID must be lesser than or equal to 100")
                                              Long postId){

        if(postService.checkIfIsOnDB(postId)) {

            final var message = PostMessage.builder()
                    .postId(postId).build();

            createdProducer.sendMessage(message);

            return ResponseEntity.ok("Post request accepted!");
        }
        else{
            return ResponseEntity.ok("Post external ID already exists in Data Base!");
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Object> disableRequest(@PathVariable("postId")@NotNull(message = "The ID can't be null!")
                                                 @Min(value = 1, message = "The ID must be greater than or equal to 1")
                                                 @Max(value = 100, message = "The ID must be lesser than or equal to 100")
                                                 Long postId){

        if(!postService.checkIfIsOnDB(postId)) {
            postService.disablePost(postId);
            return ResponseEntity.ok("Post disabled in progress!");
        }
        else{
            return ResponseEntity.ok("Post ID doesn't exist on Data Base!");
        }

    }

    @GetMapping
    public ResponseEntity<Object> listAll(){

        return ResponseEntity.ok(postService.queryPosts());
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Object> updatePost(@PathVariable("postId")@NotNull(message = "The ID can't be null!")
                                             @Min(value = 1, message = "The ID must be greater than or equal to 1")
                                             @Max(value = 100, message = "The ID must be lesser than or equal to 100")
                                             Long postId) {

        if(!postService.checkIfIsOnDB(postId)) {
            postService.reprocessPost(postId);

            HistoryStatus lastHistoryStatus = historyService.findLastElementOnHistory(postId);

            if (lastHistoryStatus.equals(HistoryStatus.UPDATING)) {
                postService.processPost(postId);
            }

            return ResponseEntity.ok("Post is being reprocessed!");
        }
        else{
            return ResponseEntity.ok("Post ID doesn't exist on Data Base!");
        }

    }

}