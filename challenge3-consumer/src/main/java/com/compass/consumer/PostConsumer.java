package com.compass.consumer;

import com.compass.producers.PostProducer;
import com.compass.producers.PostProducerMessage;
import com.compass.repository.PostRepository;
import com.compass.service.HistoryService;
import com.compass.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostConsumer {

    private final HistoryService historyService;

    private final PostService postService;

    private final PostRepository postRepository;

    private final PostProducer postProducer;

    @RabbitListener(queues = PostProducer.QUEUE_NAME)
    public void onMessage(final PostProducerMessage message){
        log.info(message.toString());
    }


}
