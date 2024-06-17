package de.cd.user.inbound.DTO;

import de.cd.user.model.entities.Role;

import java.io.Serializable;

/**
 * UserCrucialInfosDTO class
 */
public class UserCrucialInfosDTO implements Serializable {

    //private long id;

    private String email;

    private String username;

    private boolean activated;

    private Role role;

    /**
     * Empty default constructor
     */
    public UserCrucialInfosDTO() {
    }

    public UserCrucialInfosDTO(String email, String username, boolean activated, Role role) {
        this.email = email;
        this.username = username;
        this.activated = activated;
        this.role = role;
    }

    /**
     * Get-Method to receive the email
     *
     * @return String with email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set-Method to set the email
     *
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get-Method to receive the userName
     *
     * @return String with username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set-Method to set the username
     *
     * @param username String
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get-Method to receive activation status
     *
     * @return boolean activated
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Set-Method to set activation status
     *
     * @param activated boolean
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    /**
     * Get-Method to receive the role
     *
     * @return Role role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Set-Method to set the role
     *
     * @param role Role
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
