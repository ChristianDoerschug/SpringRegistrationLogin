package de.cd.user.inbound.controller;

import de.cd.user.inbound.DTO.AdminRegistrationDTO;
import de.cd.user.inbound.DTO.UserCrucialInfosDTO;
import de.cd.user.inbound.security.JwtProvider;
import de.cd.user.model.entities.Token;
import de.cd.user.model.entities.User;
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
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final MapStructMapper mapStructMapper;
    private final UserService userService;
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;


    @Autowired
    public AdminController(MapStructMapper mapStructMapper, UserService userService, JwtProvider jwtProvider, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.mapStructMapper = mapStructMapper;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }


    /**
     * Method to get all registred Users
     *
     * @return Returns a List of Users.
     */
    @GetMapping("/allUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers() {
        LOGGER.debug("Received Request to get all Users");
        return userService.getAllUsers();
    }

    /**
     * Method to get all infos about a user
     *
     * @param username String
     * @return User
     */
    @GetMapping("/{username}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User getUserById(@PathVariable String username) {
        LOGGER.debug("Received Request to getUserById ");
        return userService.getUserByUsername(username);
    }

    /**
     * Method to load a User given the id according to the UserCrucialInfosDTO
     *
     * @param username Input element to specify the user
     * @return Returns a ResponseEntity
     */
    @GetMapping("/{username}/crucial")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserCrucialInfosDTO> getCrucialById(
            @PathVariable String username) {
        LOGGER.debug("Received Get Request on user/{id}/crucial to get UserInfo according to UserCrucialInfosDTO");
        return new ResponseEntity<>(
                mapStructMapper.userCrucialInfosDTO(userService.getUserByUsername(username)),
                HttpStatus.OK
        );
    }

    /**
     * Method to load a token given the UserId
     *
     * @param id UserId to identify which user the token belongs to
     * @return Returns a Token Object
     */
    @GetMapping("/{id}/token/UserId")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Token> getTokenByUserId(@PathVariable long id) {
        LOGGER.debug("Received Request to get all Token for a user");
        return tokenService.getTokenByUserId(id);
    }

    /**
     * Method to register a new User of Type Admin
     *
     * @param adminRegistrationDTO Input Element of Type AdminRegistrationDTO
     * @return Returns a JWT
     */

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/registration")
    public String create(
            @Valid @RequestBody AdminRegistrationDTO adminRegistrationDTO
    ) {
        LOGGER.debug("Received reguest to create a bew Admin");
        userService.register(
                mapStructMapper.adminRegistration(adminRegistrationDTO)
        );
        User user = userService.getUserByUsername(adminRegistrationDTO.getUsername());
        //Role role = user.getRole();
        return jwtProvider.createJwt(user.getUsername(), user.getRole().getAuthority());
    }

    /**
     * Method to delete all Users
     */
    @DeleteMapping("/deleteAllUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteAllUsers(@RequestHeader String Authorization) {
        userService.deleteAllUsers();
    }

    /**
     * Method to change authority
     *
     * @param id Long to identify the user
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/{id}/changeAuthority")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'MODERATOR')")
    public void changeAuthority(@PathVariable long id) {
        userService.changeAuthority(id);
    }

}
