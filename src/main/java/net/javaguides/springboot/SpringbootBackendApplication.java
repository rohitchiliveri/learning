package net.javaguides.springboot;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class SpringbootBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBackendApplication.class, args);
	}
	
//	@Bean
//    public NewTopic myTopic() {
//        return TopicBuilder.name("greetings")
//                .partitions(3)
//                .replicas(1)
//                .build();
//    }
	
//	@Bean
//	ApplicationRunner runner(KafkaTemplate<String, String> template) {
//		return args -> template.send("greetings","Hello Kafka! SBT");
//	}
	
//	@KafkaListener(topics = "greetings", groupId = "demo")
//    public void consume(String message) {
//        System.out.println("Received message kafka: " + message);
//    }

}
