package com.compass.producers;

import com.compass.util.rabbitMQ.RabbitProducer;
import org.springframework.stereotype.Component;

@Component
public class CreatedProducer extends RabbitProducer<PostMessage> {

    public static final String QUEUE_NAME = "CREATED_QUEUE";

    @Override
    protected String getQueueName() {
        return QUEUE_NAME;
    }

}
