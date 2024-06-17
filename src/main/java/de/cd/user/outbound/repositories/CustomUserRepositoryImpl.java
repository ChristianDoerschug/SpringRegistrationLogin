package de.cd.user.outbound.repositories;

import de.cd.user.model.entities.Role;
import de.cd.user.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {

    @Autowired
    private EntityManager em;

    @Override
    @Transactional
    public void changeAuthorityToModerator(long id) {
        User user = em.find(User.class, id);
        if (user.getRole().equals(Role.USER)) {
            user.setRole(Role.MODERATOR);
        }
    }

}
