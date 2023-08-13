package com.compass.service;

import com.compass.dto.PostRequisitionDTO;
import com.compass.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostRepository postRepository;

    private final HistoryService historyService;

    public void processPost(Long postId){
        try {
            historyService.postFindHistory(postId);
            historyService.postOKHistory(postId);
            historyService.commentsFindHistory(postId);
            historyService.commentsOKHistory(postId);
            historyService.enabledHistory(postId);
        }catch(Exception e){
            log.error(e.toString());
            throw e;
        }
    }

    public void disablePost(Long postId){
        try {
            historyService.disabledHistory(postId);
        }
        catch(Exception e){
            log.error(e.toString());
            throw e;
        }
    }

    public void reprocessPost(Long postId){

        try{
            historyService.updatingHistory(postId);
        }
        catch(Exception e){
            log.error(e.toString());
            throw e;
        }
    }


    public List<PostRequisitionDTO> queryPosts(){
        return postRepository.findAll().stream().map(PostRequisitionDTO::new).toList();
    }

    public boolean checkIfIsOnDB(Long postId){
        return postRepository.findByExternalId(postId).isEmpty();
    }

    public Long findPostById(Long postId){
        return postRepository.findById(postId).get().getExternalId();
    }

}
