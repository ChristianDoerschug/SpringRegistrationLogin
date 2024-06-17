package de.cd.user.model;

import de.cd.user.model.entities.Token;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TokenRepository {


    /**
     * Set confirmedAt field of Token
     *
     * @param token       String of the token
     * @param confirmedAt LocalDateTime
     * @return Integer
     */
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
     * Find all tokens by the field user_id
     *
     * @param id user the Token belongs to
     * @return List of Users
     */
    List<Token> findByUserId(Long id);

    Token save(Token token);

    Optional<Token> findById(long id);

}
