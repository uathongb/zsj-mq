package com.it.zsj.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqConfiguration {

    private static Logger logger = LoggerFactory.getLogger(RabbitMqConfiguration.class);

    @Value("${spring.rabbitmq.host}")
    private String host;
    @Value("${spring.rabbitmq.username}")
    private String userName;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.port}")
    private Integer port;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private Boolean publisherConfirm;

    @Value("${spring.rabbitmq.publisher-returns}")
    private Boolean publisherReturn;

    @Value("${spring.rabbitmq.listener.simple.concurrency}")
    private String concurrency;

    @Value("${spring.rabbitmq.cache.connection.mode}")
    private CachingConnectionFactory.CacheMode acknowledgeMode;

    @Value("${spring.rabbitmq.listener.simple.max-concurrency}")
    private Integer maxConcurrency;

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPassword(password);
        connectionFactory.setUsername(userName);
        connectionFactory.setPublisherConfirms(publisherConfirm);
        connectionFactory.setPublisherReturns(publisherReturn);
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public Queue queue(){
        return new Queue("test");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback((correlationData,ack,cause)->{
            if (ack){
                logger.info("已经经过确认！");
            }else {
                logger.info("发送到exchange失败，失败原因为：{}",cause);
            }
        });
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            logger.info("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);

        });
        return rabbitTemplate;
    }


}
