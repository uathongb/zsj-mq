package com.it.zsj.config.component;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    public void send(){
        for (int i = 0; i < 10000; i++) {
            String msg = "hello，序号="+i;
            User user = new User();
            user.setUserName(msg);
            System.out.println("Producer, " + msg);
            rabbitTemplate.convertAndSend("message.test","message",user);
        }
    }

    class User{
        private String userName;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }
}
