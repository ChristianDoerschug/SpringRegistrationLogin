package de.cd.user.outbound;

import de.cd.user.model.MessageProducer;
import de.cd.user.model.entities.Role;
import de.cd.user.inbound.DTO.UserCrucialInfosDTO;
import org.apache.qpid.server.SystemLauncher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MessageProducerTest {


    private static final SystemLauncher systemLauncher = new SystemLauncher();
    private boolean brokerRunning;
    private static final String QUEUE_NAME = "sweng.queue";

    @Value("${amqp.rabbitmq.testExchange}")
    private String exchange;

    @Value("${amqp.rabbitmq.testRouting}")
    private String routingKey;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private MessageProducer messageProducer;

    @BeforeEach
    public void startBroker() throws Exception {
        if (!brokerRunning) {
            Map<String, Object> attributes = new HashMap<>();
            URL initialConfig = MessageProducerTest.class.getClassLoader().getResource("initial-config.json");
            attributes.put("initialConfigurationLocation", initialConfig.toExternalForm());
            attributes.put("type", "Memory");
            attributes.put("startupLoggedToSystemOut", true);
            attributes.put("qpid.amqp_port", "5672");
            systemLauncher.startup(attributes);
            brokerRunning = true;

            CachingConnectionFactory cf = new CachingConnectionFactory("localhost", 5672);
            AmqpAdmin admin = new RabbitAdmin(cf);
            DirectExchange exchange = new DirectExchange(this.exchange);
            admin.declareExchange(exchange);
            Queue queue = new Queue(QUEUE_NAME, true);
            admin.declareQueue(queue);
            admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(this.routingKey));
            cf.destroy();
        }
    }

    @AfterAll
    public static void stopBroker() {
        systemLauncher.shutdown();
    }

    @Test
    public void sendMessageShouldWork() throws Exception {
        messageProducer.sendMyMessage("Ein Test");
        String message = (String) amqpTemplate.receiveAndConvert(QUEUE_NAME, 5000); //waitingTime
        Assertions.assertNotNull(message);
    }

    @Test
    public void sendMessageShouldWork2() throws Exception {
        UserCrucialInfosDTO userCrucialInfosDTO = new UserCrucialInfosDTO("ingo@fh-muenster.de", "Ingo", false, Role.USER);
        messageProducer.sendMessage(userCrucialInfosDTO);
        UserCrucialInfosDTO userCrucialInfosDTO1 = (UserCrucialInfosDTO) amqpTemplate.receiveAndConvert(QUEUE_NAME, 5000);
        Assertions.assertNull(userCrucialInfosDTO1);
    }
}