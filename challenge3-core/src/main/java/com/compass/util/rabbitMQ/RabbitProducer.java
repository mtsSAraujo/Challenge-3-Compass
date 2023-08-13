package com.compass.util.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AbstractExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

@Slf4j
public abstract class RabbitProducer <T>{

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public static String DEFAULT_EXCHANGE_NAME = "amq.direct";

    protected abstract String getQueueName();

    public AbstractExchange getExchange(){
        return new DirectExchange(DEFAULT_EXCHANGE_NAME);
    }

    public Binding getBinding(){
        return new Binding(getQueueName(),
                Binding.DestinationType.QUEUE,
                getExchange().getName(),
                getQueueName(),
                null);
    }

    @Async
    public void sendMessage(T forwardMessage){
        try{
            rabbitTemplate.convertAndSend(getQueueName(),
                    forwardMessage);
            log.info("[{}]Message sent successfully", getQueueName());
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

}
