package de.cd.user.model.services;

import de.cd.user.model.TokenRepository;
import de.cd.user.model.UserRepository;
import de.cd.user.model.entities.Token;
import de.cd.user.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * TokenService Class
 */
@Service
@Transactional
public class TokenService {

    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository, UserRepository userRepository) {
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
    }

    /**
     * Method to save an Activation token in the Token Entity
     *
     * @param user            The user who the Token belongs to
     * @param activationToken String of the activation Token
     */
    public void saveToken(User user, String activationToken) {
        Token token = new Token(activationToken, user);
        tokenRepository.save(token);
    }

    /**
     * Method to receive a token
     *
     * @param token String of the activation Token
     * @return Returns a Token
     */
    public Optional<Token> getToken(String token) {
        return tokenRepository.findByToken(token);
    }

    /**
     * Method to set the confirmation Date of a token when it gets activated
     *
     * @param token String of the activation Token
     * @return Integer
     */
    public int setConfirmedAt(String token) {
        return tokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }


    /**
     * Method to receive a List of all tokens belonging to a single User
     *
     * @param id identifies the user
     * @return List of Tokens
     */
    public List<Token> getTokenByUserId(long id) {
        return tokenRepository.findByUserId(id);
    }

    /**
     * Method to receive a token
     *
     * @param id identifies the token
     * @return Token
     */
    public Optional<Token> getTokenById(long id) {
        return tokenRepository.findById(id);
    }
}
