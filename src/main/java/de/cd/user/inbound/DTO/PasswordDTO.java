package de.cd.user.inbound.DTO;

import de.cd.user.model.util.ValidUserPassword;

import javax.persistence.Id;

/**
 * PasswordDTO class
 */
public class PasswordDTO {

    @Id
    private Long id;

    private String password;

    private String userName;

    @ValidUserPassword
    private String newPassword;

    @ValidUserPassword
    private String confirmPassword;

    /**
     * Empty default constructor
     */
    public PasswordDTO() {
    }

    /**
     * Get-Method to receive the current Password
     *
     * @return String password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set-Method to set the current Password
     *
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get-Method to receive the new Password
     *
     * @return String newPassword
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Set-Method to set the new Password
     *
     * @param newPassword String
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Get-Method to receive the confirmation Password
     *
     * @return
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * Set Method to set the confirmation Password
     *
     * @param confirmPassword
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * Get-Method to receive the Id
     *
     * @return Long id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set-Method to set the Id
     *
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get-Method to receive the username
     *
     * @return username String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set-Method to set the userName
     *
     * @param userName String
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
