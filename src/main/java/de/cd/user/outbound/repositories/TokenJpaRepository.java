package de.cd.user.outbound.repositories;

import de.cd.user.model.TokenRepository;
import de.cd.user.model.entities.Token;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository to access the Token table
 */
public interface TokenJpaRepository extends CrudRepository<Token, Long>, TokenRepository {

    /**
     * Query to set confirmedAt field of Token
     *
     * @param token       String of the token
     * @param confirmedAt LocalDateTime
     * @return Integer
     */
    @Transactional
    @Modifying
    @Query("UPDATE Token t " +
            "SET t.confirmedAt = ?2 " +
            "WHERE t.token = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);

    /**
     * Method to find a Token by String
     *
     * @param token String
     * @return Token
     */
    Optional<Token> findByToken(String token);

    /**
     * Query to find all tokens by the field user_id
     *
     * @param id user the Token belongs to
     * @return List of Users
     */
    @Query("SELECT t from Token t WHERE t.user.id = ?1")
    List<Token> findByUserId(Long id);
}
