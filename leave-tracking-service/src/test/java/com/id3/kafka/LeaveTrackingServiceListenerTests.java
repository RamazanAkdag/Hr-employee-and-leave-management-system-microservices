package com.id3.kafka;


import static org.mockito.ArgumentMatchers.any;


/*@SpringBootTest(classes = LeaveTrackingServiceApplication.class)
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