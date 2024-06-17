package de.cd.user.inbound;

import de.cd.user.model.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageConsumer {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    @Autowired
    public MessageConsumer(UserService userService) {
        this.userService = userService;
    }

    @RabbitListener(queues = "${amqp.rabbitmq.test.queue}")
    @Transactional
    public void getMessage(String message) {
        LOGGER.info(message);
    }
}
