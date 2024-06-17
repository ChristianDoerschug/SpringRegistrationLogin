package de.cd.user.inbound.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * LoginDTO class
 */
public class LoginDTO {

    @Email
    private String email;

    private String username;

    @NotNull
    private String password;

    /**
     * Empty default constructor
     */
    public LoginDTO() {
    }

    /**
     * Get-Method to receive the email
     *
     * @return email String
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
     * Get-Method to receive the username
     *
     * @return username String
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
     * Get-Method to get the password
     *
     * @return String password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set-Method to set the password
     *
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
