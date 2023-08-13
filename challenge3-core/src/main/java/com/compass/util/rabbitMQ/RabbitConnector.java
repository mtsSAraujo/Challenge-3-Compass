package com.compass.util.rabbitMQ;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor(onConstructor_ = @Autowired)
public class RabbitConnector {

    AmqpAdmin amqpAdmin;

    List<RabbitProducer<?>> rabbitProducerList;

    private Queue instantiateQueue(String queueName){
        return new Queue(queueName,true, false, false);
    }

    public <T> void createQueue(RabbitProducer<T> producer){
        var queue = producer.getQueueName();
        var exchange = producer.getExchange();
        var binding = producer.getBinding();

        amqpAdmin.declareQueue(instantiateQueue(queue));
        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareBinding(binding);

    }

    @PostConstruct
    public void initializeAllQueue(){
        rabbitProducerList.forEach(this::createQueue);
    }

}
