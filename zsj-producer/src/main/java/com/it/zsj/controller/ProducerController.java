package com.it.zsj.controller;

import com.it.zsj.config.component.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    Producer producer;

    @GetMapping(value = "/test")
    public void test(){
        producer.send();
    }
}
