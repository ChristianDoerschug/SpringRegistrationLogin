package de.cd.user.outbound;

import de.cd.user.inbound.DTO.UserCrucialInfosDTO;
import de.cd.user.model.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
public class MessageProducerImpl implements MessageProducer {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private AmqpTemplate amqpTemplate;

    private final RabbitTemplate rabbitTemplate;

    @Value("${amqp.rabbitmq.exchange}")
    private String exchange;

    @Value("${amqp.rabbitmq.routing}")
    private String routingKey;

    @Value("${amqp.rabbitmq.testExchange}")
    private String testExchange;

    @Value("${amqp.rabbitmq.testRouting}")
    private String testRoutingKey;

    @Autowired
    public MessageProducerImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostConstruct
    public void init() {
        this.rabbitTemplate.setChannelTransacted(true);
    }


    @Override
    @Transactional
    public void sendMessage(UserCrucialInfosDTO userCrucialInfosDTO) {
        rabbitTemplate.convertAndSend(exchange, routingKey, userCrucialInfosDTO);
        LOGGER.info("Sent user according to UserCrucialInfosDTO");
    }

    @Override
    @Transactional
    public void sendMyMessage(String message) {
        rabbitTemplate.convertAndSend(testExchange, testRoutingKey, message);
        LOGGER.info("Test message sent");
    }
}
