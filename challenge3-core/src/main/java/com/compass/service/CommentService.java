package com.compass.service;

import com.compass.clients.requests.CommentsDetails;
import com.compass.entity.Comment;
import com.compass.entity.PostRequisition;
import com.compass.repository.CommentRepository;
import com.compass.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public Set<Comment> convertToCommentList(Set<CommentsDetails> commentsDetailsSet){
        return commentsDetailsSet.stream()
                .map(this::convertToComment)
                .collect(Collectors.toSet());
    }

    private Comment convertToComment(CommentsDetails commentsDetails) {
        Long postId = commentsDetails.getPostId();

        Comment comment = new Comment();

        PostRequisition postRequisition = postRepository.findByExternalId(postId).orElseThrow(() ->
                new RuntimeException("Couldn't find any post with given ID"));

        comment.setExternalId(commentsDetails.getId());
        comment.setEmail(commentsDetails.getEmail());
        comment.setBody(commentsDetails.getBody());
        comment.setName(commentsDetails.getName());
        comment.setPostRequisition(postRequisition);
        return comment;
    }

    public void saveComments(Set<Comment> comments) {
        commentRepository.saveAll(comments);
    }


}
