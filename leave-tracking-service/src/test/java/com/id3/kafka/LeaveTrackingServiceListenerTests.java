/*package com.id3.kafka;


import com.id3.LeaveTrackingServiceApplication;
import com.id3.leaverequestservice.model.LeaveRequestMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



@SpringBootTest(classes = LeaveTrackingServiceApplication.class)
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(locations = "classpath*:/application.properties")
public class LeaveTrackingServiceListenerTests {

    @Container
    public static KafkaContainer kafka = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:5.4.3")
    );

    @Autowired
    private KafkaTemplate<String, LeaveRequestMessage> kafkaTemplate;

    @Autowired
    private LeaveRequestListener leaveRequestListener;

    @BeforeEach
    void setUp() {
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(kafka.getBootstrapServers());
        ProducerFactory<String, LeaveRequestMessage> pf = new DefaultKafkaProducerFactory<>(producerProps);
        kafkaTemplate = new KafkaTemplate<>(pf);
    }

    @Test
    public void givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() throws Exception {
        // Arrange
        LeaveRequestMessage message = new LeaveRequestMessage(
                "John", "Doe", "john.doe@example.com", null, new Date(), new Date(), "Vacation", "ANNUAL", "PENDING"
        );

        // Act
        kafkaTemplate.send("leave-request-topic", message);

        // Wait for the message to be consumed
        TimeUnit.SECONDS.sleep(2);

        // Assert
        verify(leaveRequestListener, times(1)).listen(any(LeaveRequestMessage.class));
    }
}*/