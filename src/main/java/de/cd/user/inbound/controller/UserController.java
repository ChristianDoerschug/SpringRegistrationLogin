package de.cd.user.inbound.controller;

import de.cd.user.inbound.DTO.*;
import de.cd.user.inbound.security.JwtProvider;
import de.cd.user.model.UserRepository;
import de.cd.user.model.entities.Role;
import de.cd.user.model.entities.User;
import de.cd.user.model.exceptions.AuthenticationException;
import de.cd.user.model.services.EmailService;
import de.cd.user.model.services.TokenService;
import de.cd.user.model.services.UserService;
import de.cd.user.model.util.MapStructMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Restcontroller UserManagement microservice
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final MapStructMapper mapStructMapper;
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    @Autowired
    public UserController(UserRepository userRepository, MapStructMapper mapStructMapper, UserService userService,
                          JwtProvider jwtProvider, PasswordEncoder passwordEncoder, TokenService tokenService,
                          EmailService emailService) {
        this.userRepository = userRepository;
        this.mapStructMapper = mapStructMapper;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }


    /**
     * Method to load a User with given id according ti the UserGetDTO
     *
     * @param Authorization Input element to specify the user
     * @return Returns a ResponseEntity
     */
    @GetMapping("/userinfo")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<UserGetDto> getByUserName(@RequestHeader String Authorization) {
        LOGGER.debug("Received Get Request on user/{id} to get UserInfo according to UserGetDto");
        String username;
        username = jwtProvider.getUsername(Authorization.substring(7));
        return new ResponseEntity<>(
                mapStructMapper.userGetDto(userService.getUserByUsername(username)),
                HttpStatus.OK
        );
    }

    @PostMapping("/forgotPassword")
    public void forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPasswordDTO) {
        LOGGER.debug("Received Request to reset password");
        User user = userService.getUserByEmail(forgotPasswordDTO.getEmail());
        String password = UUID.randomUUID().toString();
        user.setPasswordHash(passwordEncoder.encode(password));
        userRepository.save(user);
        emailService.sendMail(forgotPasswordDTO.getEmail(), "Password reset", "Please change the password the next time you log in " + password);
    }

    /**
     * Method to register a new User
     *
     * @param userRegistrationDTO Input Element of Type user according to the UserRegistrationDTO
     * @return Returns a JWT
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public String create(
            @Valid @RequestBody UserRegistrationDTO userRegistrationDTO
    ) {
        LOGGER.debug("Received Post Request for User registration");
        userService.register(
                mapStructMapper.userRegistration(userRegistrationDTO)
        );
        User user = userService.getUserByUsername(userRegistrationDTO.getUsername());
        Role role = user.getRole();
        return jwtProvider.createJwt(user.getUsername(), role.getAuthority());
    }

    /**
     * Login Method to login a User with either the username or email
     *
     * @param loginDTO Input Parameter Object of type loginDTO
     * @return Returns a JWT
     */
    @PostMapping("/login")
    public String login(
            @Valid @RequestBody LoginDTO loginDTO
    ) {
        LOGGER.debug("Received Post Request on user/login");
        User user;
        if (loginDTO.getUsername() != null) {
            LOGGER.info("Try Login with username " + loginDTO.getUsername());
            user = userService.login(loginDTO.getUsername(), loginDTO.getPassword());
        } else {
            LOGGER.info("Try Log in with email " + loginDTO.getEmail());
            user = userService.login(userService.getUserByEmail(loginDTO.getEmail()).getUsername(), loginDTO.getPassword());
        }
        LOGGER.info("Log in successful");
        return jwtProvider.createJwt(user.getUsername(), user.getRole().getAuthority());
    }

    /**
     * Method to update all possible fields according to the UpdateUserDTO
     *
     * @param updateUserDTO Input Parameter according to the UpdateUserDTO
     */
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER')")
    public void updateUser(@RequestHeader String Authorization,
                           @Valid @RequestBody UpdateUserDTO updateUserDTO) {
        LOGGER.debug("Received PUT-Request on user to update user");
        String userName = jwtProvider.getUsername(Authorization.substring(7));
        if (!userName.isEmpty()) {
            User user = userService.updateUser(updateUserDTO);
            userService.save(user);
            LOGGER.info("Update User successful " + userName);
        } else {
            LOGGER.warn("Could not update user");
            throw new AuthenticationException("unauthorized");
        }
    }

    /**
     * Method to change a users password. Checks if the newPassword is equal to the old Password and whether the newPassword and the confirmPassword are equal.
     *
     * @param passwordDTO specifies the accepted and obligatory fields
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/changePassword")
    @PreAuthorize("hasAuthority('USER')")
    public void changePassword(@RequestHeader String Authorization,
                               @Valid @RequestBody PasswordDTO passwordDTO
    ) {
        LOGGER.info("Received Put Request on Method changePassword()");
        String username = jwtProvider.getUsername(Authorization.substring(7));
        User user = userService.changePassword(username, passwordDTO.getPassword(), passwordDTO.getNewPassword(), passwordDTO.getConfirmPassword());
        userRepository.save(user);
    }


    /**
     * Method to activate a user
     *
     * @param token Request Parameter to identify the Token
     * @return Returns a String with either a success message or a failure message
     */
    @GetMapping("/activate")
    public String activateUser(@RequestParam String token) {
        LOGGER.info("in activateUser() for User " + tokenService.getToken(token).get().getUser().getId());
        userService.confirmToken(token);
        LOGGER.info("User activated ");
        return "Your email has been confirmed";
    }


    /**
     * Method to delete a singe user
     */
    @DeleteMapping
    @PreAuthorize("hasAuthority('USER')")
    public void deleteUser(@RequestHeader String Authorization) {
        String username = jwtProvider.getUsername(Authorization.substring(7));
        Long id = userRepository.findByUserName(username).getId();
        userService.deleteUserById(id);
        LOGGER.info("User erfolgreich gelöscht");
    }
}
