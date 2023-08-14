package com.compass.consumer;

import com.compass.producers.CreatedProducer;
import com.compass.producers.PostMessage;
import com.compass.producers.PostProducer;
import com.compass.repository.PostRepository;
import com.compass.service.HistoryService;
import com.compass.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostConsumer {

    private final HistoryService historyService;

    private final PostService postService;

    private final PostRepository postRepository;

    private final PostProducer postProducer;

    @RabbitListener(queues = CreatedProducer.QUEUE_NAME)
    public void onCreation(final PostMessage message){
        try {
            var initialTime = Instant.now().toEpochMilli();
            historyService.createHistory(message.getPostId());
            log.info("[{}] Took {} to process", CreatedProducer.QUEUE_NAME, String.valueOf((Instant.now().toEpochMilli() - initialTime)));
        }
        catch(Exception e){
            log.error(e.getMessage());
        }
    }

    @RabbitListener(queues = PostProducer.QUEUE_NAME)
    @Async
    public void onProcess(final PostMessage message){
        try{
            var initialTime = Instant.now().toEpochMilli();
            postService.processPost(message.getPostId());
            log.info("[{}] Took {} to process", PostProducer.QUEUE_NAME, String.valueOf((Instant.now().toEpochMilli() - initialTime)));
        }
        catch(Exception e){
            log.error(e.getMessage());
        }
    }


}
