package app.vrabia.authservice.kafka;

import app.vrabia.authservice.dto.kafka.UserDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {
    private final KafkaTemplate<String, UserDTO> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, UserDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(UserDTO savedUser) {
        kafkaTemplate.send("auth-topic", savedUser);
    }
}
