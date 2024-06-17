package de.cd.user.model;

import de.cd.user.model.entities.User;
import org.springframework.data.repository.query.Param;

public interface UserRepository {


    /**
     * Find a User by Username
     *
     * @param username username of the user
     * @return User
     */
    User findByUserName(@Param("username") String username);

    /**
     * Find a User by email
     *
     * @param email email of the user
     * @return User
     */
    User findByEmail(@Param("email") String email);

    /**
     * Activate a User
     *
     * @param email email of the User
     * @return Integer
     */
    int activateUser(@Param("email") String email);

    /**
     * Changes the role of the User
     *
     * @param id Long
     */
    void changeAuthorityToModerator(long id);

    /**
     * Saves a user
     *
     * @param user User
     * @return User
     */
    User save(User user);

    /**
     * Finds User by Id
     *
     * @param id Long
     * @return User
     */
    User findById(long id);

    void deleteAll();

    void deleteById(long id);

    Iterable<User> findAll();
}

