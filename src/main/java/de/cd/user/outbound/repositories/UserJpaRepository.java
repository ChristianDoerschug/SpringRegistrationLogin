package de.cd.user.outbound.repositories;

import de.cd.user.model.UserRepository;
import de.cd.user.model.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository to access the user table
 */
@Repository
public interface UserJpaRepository extends CrudRepository<User, Long>, UserRepository, CustomUserRepository {

    /**
     * Query to find a User by Username
     *
     * @param username username of the user
     * @return User
     */
    @Query("Select u from User u WHERE u.username = :username")
    User findByUserName(@Param("username") String username);

    /**
     * Query to find a User by email
     *
     * @param email email of the user
     * @return User
     */
    @Query("Select u from User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    /**
     * Query to activate a User
     *
     * @param email email of the User
     * @return Integer
     */
    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.activated = TRUE WHERE u.email = :email")
    int activateUser(@Param("email") String email);
}
