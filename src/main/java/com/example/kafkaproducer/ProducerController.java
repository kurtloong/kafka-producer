package com.example.kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kurt.loong
 */
@RestController
public class ProducerController {
    @Autowired
    private ProducerTask producerTask;
    @PostMapping("/sendMessage")
    public void sendMessage(@RequestParam(name = "number") int number){
        for (int i = 0; i < number; i++) {
            producerTask.sendVideoMessage();
        }

    }
}
