package com.compass.service;

import com.compass.clients.PostsClient;
import com.compass.entity.Comment;
import com.compass.entity.History;
import com.compass.entity.PostRequisition;
import com.compass.entity.historyStatus.HistoryStatus;
import com.compass.producers.PostMessage;
import com.compass.producers.PostProducer;
import com.compass.repository.CommentRepository;
import com.compass.repository.HistoryRepository;
import com.compass.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;

    private final PostRepository postRepository;

    private final PostsClient postsClient;

    private final CommentService commentService;

    private final CommentRepository commentRepository;

    private final PostProducer postProducer;

    public void createHistory(Long postId){
        PostRequisition postRequisition = postRepository.save(new PostRequisition(postId));
        historyRepository.save(new History(Instant.now(), "CREATED", postRequisition));
        postRepository.save(postRequisition);

        final var message = PostMessage.builder()
                .postId(postId).build();
        postProducer.sendMessage(message);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void postFindHistory(long postId){

        try {
            var updatedPostRequisition = postRepository.findByExternalId(postId)
                    .map(postRequisition -> new PostRequisition(
                            postRequisition.getId(),
                            postRequisition.getExternalId(),
                            postsClient.getPostDetails(postId).getTitle(),
                            postsClient.getPostDetails(postId).getBody(),
                            postRequisition.getComment(),
                            postRequisition.getHistory()))
                    .orElseThrow(() -> new RuntimeException(""));


            historyRepository.save(new History(Instant.now(), "POST_FIND", updatedPostRequisition));
            postRepository.save(updatedPostRequisition);
        }
        catch (Exception e){
            failedHistory(postId);
            ResponseEntity.ok("Failed to find Post!");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void postOKHistory(Long postId){

        historyRepository.save(new History(Instant.now(), "POST_OK",
                postRepository.findByExternalId(postId).orElseThrow(() -> new RuntimeException(""))));

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public void commentsFindHistory(Long postId) {
        try {
            Set<Comment> commentSet = commentService.convertToCommentList(
                    postsClient.getCommentsDetails(postId));

            Set<Comment> existingComments = commentRepository.findByPostRequisitionExternalId(postId);

            commentSet.removeIf(comment -> existingComments.stream().anyMatch(existingComment ->
                    existingComment.getExternalId().equals(comment.getExternalId())));

            commentService.saveComments(commentSet);

            PostRequisition updatedPostRequisition = postRepository.findByExternalId(postId)
                    .map(postRequisition -> new PostRequisition(
                            postRequisition.getId(),
                            postRequisition.getExternalId(),
                            postsClient.getPostDetails(postId).getTitle(),
                            postsClient.getPostDetails(postId).getBody(),
                            commentSet,
                            postRequisition.getHistory()))
                    .orElseThrow(() -> new RuntimeException(""));

            History history = new History(Instant.now(), "COMMENTS_FIND", updatedPostRequisition);
            historyRepository.save(history);
            postRepository.save(updatedPostRequisition);
        }
        catch(Exception e){
            failedHistory(postId);
            ResponseEntity.ok("Failed to find Comments!");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void commentsOKHistory(Long postId){
        History history = new History(Instant.now(), "COMMENTS_OK", postRepository.findByExternalId(postId)
                .orElseThrow(() -> new RuntimeException("")));

        historyRepository.save(history);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void enabledHistory(Long postId){
        History history = new History(Instant.now(), "ENABLED", postRepository.findByExternalId(postId)
                .orElseThrow(() -> new RuntimeException("")));

        historyRepository.save(history);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void disabledHistory(Long postId){

        HistoryStatus lastHistoryStatus = findLastElementOnHistory(postId);

        if(lastHistoryStatus.equals(HistoryStatus.FAILED) || lastHistoryStatus.equals(HistoryStatus.ENABLED)){
            History history = new History(Instant.now(), "DISABLED", postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("")));

            historyRepository.save(history);
        }
        else{
            throw new RuntimeException("Object can't turn into Disabled!");
        }


    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updatingHistory(Long postId){

        HistoryStatus lastHistoryStatus = findLastElementOnHistory(postId);

        if(lastHistoryStatus.equals(HistoryStatus.DISABLED) || lastHistoryStatus.equals(HistoryStatus.ENABLED)){
            History history = new History(Instant.now(), "UPDATING", postRepository.findById(postId)
                    .orElseThrow(() -> new RuntimeException("")));

            historyRepository.save(history);

        }
        else{
            throw new RuntimeException("Can't update this Object!");
        }

    }

    public void failedHistory(Long postId){

        HistoryStatus lastHistoryStatus = findLastElementOnHistory(postId);


        if(lastHistoryStatus.equals(HistoryStatus.COMMENTS_FIND) || lastHistoryStatus.equals(HistoryStatus.POST_FIND)){
            History history = new History(Instant.now(), "FAILED", postRepository.findByExternalId(postId)
                    .orElseThrow(() -> new RuntimeException("")));

            historyRepository.save(history);
        }
        else{
            throw new RuntimeException("Object can't be considered failed!");
        }
        
    }

    public HistoryStatus findLastElementOnHistory(Long postId){

        PostRequisition postRequisition = postRepository.findById(postId).orElseThrow(() -> new RuntimeException(""));

        Set<History> historySet = postRequisition.getHistory();

        History lastHistoryElement =(History) historySet.toArray()[historySet.toArray().length - 1];

        return lastHistoryElement.getStatus();

    }

}
