package de.cd.user.model;

import de.cd.user.inbound.DTO.UserCrucialInfosDTO;
import org.springframework.transaction.annotation.Transactional;

public interface MessageProducer {

    @Transactional
    void sendMessage(UserCrucialInfosDTO userCrucialInfosDTO);

    @Transactional
    void sendMyMessage(String message);
}
