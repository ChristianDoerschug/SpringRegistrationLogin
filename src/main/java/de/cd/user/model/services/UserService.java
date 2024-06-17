package de.cd.user.model.services;

import de.cd.user.model.TokenRepository;
import de.cd.user.model.UserRatingClient;
import de.cd.user.model.UserRepository;
import de.cd.user.model.entities.Role;
import de.cd.user.model.entities.Token;
import de.cd.user.model.entities.User;
import de.cd.user.inbound.DTO.UpdateUserDTO;
import de.cd.user.inbound.DTO.UserCrucialInfosDTO;
import de.cd.user.inbound.DTO.UserMediaDTO;
import de.cd.user.model.exceptions.AlreadyExistsException;
import de.cd.user.model.exceptions.ExpiredException;
import de.cd.user.model.exceptions.PasswordNotValidException;
import de.cd.user.model.exceptions.ResourceNotFoundException;
import de.cd.user.model.util.MapStructMapperImpl;
import de.cd.user.outbound.MessageProducerImpl;
import feign.RetryableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * UserService Class
 */
@Service
@Transactional
public class UserService {

    @Value("${ratingForMod}")
    private int userRatingforMod;

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final TokenService tokenService;
    private final EmailService emailService;
    @Value("${server.name}")
    private String server;
    private final PasswordEncoder passwordEncoder;
    private final UserRatingClient userRatingClient;
    private final MessageProducerImpl messageProducerImpl;
    private final MapStructMapperImpl mapStructMapper;

    @Autowired
    public UserService(UserRepository userRepository, TokenRepository tokenRepository, AuthenticationManager authenticationManager, TokenService tokenService, EmailService emailService, PasswordEncoder passwordEncoder, UserRatingClient userRatingClient, MessageProducerImpl messageProducerImpl, MapStructMapperImpl mapStructMapper) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
        this.tokenService = tokenService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.userRatingClient = userRatingClient;
        this.messageProducerImpl = messageProducerImpl;
        this.mapStructMapper = mapStructMapper;
    }


    /**
     * Delete all users
     */
    public String deleteAllUsers() {
        userRepository.deleteAll();
        return "Success";
    }

    /**
     * Delete User with id
     *
     * @param id Long
     */
    public void deleteUserById(long id) {
        UserCrucialInfosDTO userCrucialInfosDTO = mapStructMapper.userCrucialInfosDTO(userRepository.findById(id));
        messageProducerImpl.sendMessage(userCrucialInfosDTO);
        userRepository.deleteById(id);
    }

    /**
     * Find all Users
     *
     * @return List of Users
     */
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    /**
     * Find User with id
     *
     * @param id Long
     * @return User
     */
    public User getUserById(long id) {
        return userRepository.findById(id);
    }

    /**
     * Find User with username
     *
     * @param username String
     * @return User
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    /**
     * Persists User in database
     *
     * @param user User
     */
    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Find User with email
     *
     * @param email String
     * @return User
     */
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Registers a user. Checks if username or email is already taken. Then saves the user in the Database. If user has the role USER a random String gets generated
     * and an email with an activation link is being send to the email address used for registration.
     *
     * @param user User
     * @return User
     */
    public User register(User user) {
        LOGGER.debug("in Method register()");
        if (userRepository.findByUserName(user.getUsername()) != null) {
            LOGGER.warn("Username is already taken");
            throw new AlreadyExistsException("Username already taken");
        } else if (userRepository.findByEmail(user.getEmail()) != null) {
            LOGGER.warn("Email is already taken");
            throw new AlreadyExistsException("Email already taken");
        } else {
            LOGGER.info("Try to save user");
            user.setPasswordHash(passwordEncoder.encode(user.getPassword()));
            save(user);
            UserCrucialInfosDTO userCrucialInfosDTO;
            userCrucialInfosDTO = mapStructMapper.userCrucialInfosDTO(user);
            messageProducerImpl.sendMessage(userCrucialInfosDTO);
            messageProducerImpl.sendMyMessage("User angelegt (Test Message zur Demonstration)");
            LOGGER.info("Important userInfos sent to other microservices " + user.getUsername());
            LOGGER.info("User save successful. Try generate tokenString for users");
            if (user.getRole() == Role.USER) {
                String token = UUID.randomUUID().toString();
                LOGGER.info("Tokenstring successfully generated");
                tokenService.saveToken(getUserByUsername(user.getUsername()), token);
                emailService.sendMail(user.getEmail(), "Registration for MediaReviewApp", "Please click the following link to verify your email:" + server + "/user/activate?token=" + token);
                LOGGER.info("Token saved successfully");
            }
            return user;
        }
        /*UserCrucialInfosDTO userCrucialInfosDTO;
        userCrucialInfosDTO = mapStructMapper.userCrucialInfosDTO(user);
        messageProducer.sendMessage(userCrucialInfosDTO);*/
    }

    /**
     * Login a user if credentials are valid
     *
     * @param username String
     * @param password String
     * @return User
     */
    public User login(String username, String password) {
        LOGGER.debug("in Method login()");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            LOGGER.info("User authenticated");
            return userRepository.findByUserName(username);
        } catch (Exception e) {
            LOGGER.warn("User Authentication failed");
            throw new ResourceNotFoundException("Invalid username and password");
        }
    }

    public User updateUser(UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(updateUserDTO.getId());
        User user1 = mapStructMapper.updateUser(updateUserDTO);
        user.setEmail(user1.getEmail());
        user.setFirstName(user1.getFirstName());
        user.setLastName(user1.getLastName());
        user.setCountry(user1.getCountry());
        user.setStreet(user1.getStreet());
        user.setHouseNo(user1.getHouseNo());
        user.setCity(user1.getCity());
        return user;
    }


    /**
     * Confirms a Token. Checks in token table for a Token with the given String. If the token is valid and has not been confirmed or expired,
     * the confirmation Date gets set and the user activated
     *
     * @param token String
     * @return String "confirmed"
     */

    public String confirmToken(String token) {
        LOGGER.debug("in Method confirm Token()");
        LOGGER.info(token);
        Token confirmationToken = tokenService
                .getToken(token)
                .orElseThrow(() ->
                        new ResourceNotFoundException("token not found"));
        LOGGER.warn("token not found");

        if (confirmationToken.getConfirmedAt() != null) {
            LOGGER.warn("Link for confirmation was already clicked");
            throw new AlreadyExistsException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            LOGGER.warn("token expired");
            throw new ExpiredException("Activation Link expired");
        }

        tokenService.setConfirmedAt(token);
        userRepository.activateUser(
                tokenService.getToken(token).get().getUser().getEmail());
        LOGGER.info("Activate user");
        return "confirmed";
    }

    /**
     * Method to change the password.
     *
     * @param username String
     * @param password String
     * @return User
     */
    public User changePassword(String username, String password, String newPassword, String confirmPassword) {
        LOGGER.debug("Method changePassword() is called");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (!password.equals(newPassword)) {
            if (newPassword.equals(confirmPassword)) {
                LOGGER.info("entered passwords are equal");
                User user = userRepository.findByUserName(username);
                user.setPasswordHash(passwordEncoder.encode(newPassword));
                userRepository.save(user);
                LOGGER.info("password has been changed successfully");
            } else {
                LOGGER.warn("Entered passwords are not equal");
                throw new PasswordNotValidException("The new Password and the confirmation Password are not identical");
            }
        } else {
            LOGGER.warn("Old and new password are identical");
            throw new PasswordNotValidException("New Password cannot be equal to the old one");
        }
        LOGGER.info("User " + username + " has been authenticated");
        return userRepository.findByUserName(username);
    }

    public void changeAuthority(long id) {
        LOGGER.debug("Method changeAuthority() is called");
        User user = userRepository.findById(id);
        if (user == null) {
            LOGGER.error("User does not exist " + id);
            throw new ResourceNotFoundException("User doesn't exist");
        }
        if (getUserRating() >= userRatingforMod) {
            userRepository.changeAuthorityToModerator(id);
            LOGGER.info("Changed Role to Moderator");
        } else {
            LOGGER.warn("User rating to low for Role Moderator");
        }
    }

    @Retryable(value = RetryableException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 150, maxDelay = 500))
    public int getUserRating() throws RetryableException {
        LOGGER.debug("In Method getUserRating() from FeignClient");
        int[] ratings = this.userRatingClient.getRating();
        int rating = ratings[0];
        return rating;
    }

    @Recover
    public int fallBackRating(RetryableException e) {
        LOGGER.info("Feign Client not accessible");
        return 0;
    }

    public void changeUserMedia(UserMediaDTO userMediaDTO) {
        User user = userRepository.findById(userMediaDTO.getUserId());
        user.setMedia(userMediaDTO.getNumberOfPosts());
        userRepository.save(user);
    }
}
