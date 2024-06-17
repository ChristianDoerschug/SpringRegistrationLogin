package de.cd.user.model.util;

import de.cd.user.inbound.DTO.*;
import de.cd.user.model.entities.User;
import org.mapstruct.Mapper;

/**
 * Interface to generate MapStructMapperImpl
 */

@Mapper(componentModel = "spring")

public interface MapStructMapper {

    UserGetDto userGetDto(User user);

    UserCrucialInfosDTO userCrucialInfosDTO(User user);

    User adminRegistration(AdminRegistrationDTO adminRegistrationDTO);

    User userRegistration(UserRegistrationDTO userRegistrationDTO);

    User updateUser(UpdateUserDTO updateUserDTO);
}
