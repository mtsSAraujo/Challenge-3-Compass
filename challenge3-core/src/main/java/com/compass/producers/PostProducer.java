package com.compass.producers;

import com.compass.util.rabbitMQ.RabbitProducer;
import org.springframework.stereotype.Component;

@Component
public class PostProducer extends RabbitProducer<PostProducerMessage> {

    public static final String QUEUE_NAME = "POST_QUEUE";

    @Override
    protected String getQueueName() {
        return QUEUE_NAME;
    }
}
