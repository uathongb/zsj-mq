package com.it.zsj;

import com.it.zsj.config.component.Producer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerTest {

    @Autowired
    private Producer producer;

    @Test
    public void contextLoads(){
        producer.send();
    }
}
