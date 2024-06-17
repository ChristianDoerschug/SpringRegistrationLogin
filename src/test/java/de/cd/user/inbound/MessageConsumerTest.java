package de.cd.user.inbound;

import de.cd.user.inbound.DTO.UserMediaDTO;
import de.cd.user.model.services.UserService;
import org.apache.qpid.server.SystemLauncher;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class MessageConsumerTest {

    private static final SystemLauncher systemLauncher = new SystemLauncher();
    private boolean brokerRunning;
    private static final String EXCHANGE_NAME = "sweng.direct";
    private static final String ROUTING_KEY = "sweng.routingkey";
    private static final String QUEUE_NAME = "sweng.user";

    @Value("${amqp.rabbitmq.queue}")
    private static String queue;


    @Autowired
    private AmqpTemplate amqpTemplate;

    @SpyBean
    private UserService userService;

    UserMediaDTO userMediaDTO;

    @BeforeEach
    public void startBroker() throws Exception {
        this.userMediaDTO = new UserMediaDTO();
        userMediaDTO.setUserId(123L);
        userMediaDTO.setNumberOfPosts(1);
        if (!brokerRunning) {
            Map<String, Object> attributes = new HashMap<>();
            URL initialConfig = MessageConsumerTest.class.getClassLoader().getResource("initial-config.json");
            attributes.put("initialConfigurationLocation", initialConfig.toExternalForm());
            attributes.put("type", "Memory");
            attributes.put("startupLoggedToSystemOut", true);
            attributes.put("qpid.amqp_port", "5672");
            systemLauncher.startup(attributes);
            brokerRunning = true;

            CachingConnectionFactory cf = new CachingConnectionFactory("localhost", 5672);
            AmqpAdmin admin = new RabbitAdmin(cf);
            DirectExchange exchange = new DirectExchange(EXCHANGE_NAME);
            admin.declareExchange(exchange);
            Queue queue = new Queue(QUEUE_NAME, true);
            Queue queue1 = new Queue("test.test.user", true);
            admin.declareQueue(queue);
            admin.declareQueue(queue1);
            admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY));
            admin.declareBinding(BindingBuilder.bind(queue1).to(exchange).with(ROUTING_KEY));
            cf.destroy();
        }
    }

    @AfterAll
    public static void stopBroker() {
        systemLauncher.shutdown();
    }

    @Test
    public void receiveMessageShouldWork() throws Exception {
        UserMediaDTO message = this.userMediaDTO;
        System.out.println(userMediaDTO.getUserId());
        doNothing().when(userService).changeUserMedia(userMediaDTO);
        amqpTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, message);
        Thread.sleep(5000);
        //verify(amqpTemplate).convertAndSend(any(),any(),message);
    }
}
