package com.it.zsj.component;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Consumer {

    private static Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "test")
    public void process(Message message, Channel channel) throws IOException {
        MessageProperties messageProperties = message.getMessageProperties();
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);
        logger.info("info =="+new String(message.getBody()));
    }
}
