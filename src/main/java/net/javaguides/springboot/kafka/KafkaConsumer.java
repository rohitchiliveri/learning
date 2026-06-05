package net.javaguides.springboot.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {
	@KafkaListener(topics = "greetings", groupId = "demo")
    public void consume(String message) {
        System.out.println("Received message kafka: " + message);
    }
}
