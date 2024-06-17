package de.cd.user.inbound.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.cd.user.model.entities.Role;
import de.cd.user.model.util.ValidAdminPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * AdminRegistration class
 */
public class AdminRegistrationDTO {

    @JsonIgnore
    private long id;

    @Email
    @NotNull
    private String email;

    @NotNull
    @Size(max = 20)
    private String username;

    @NotNull
    @ValidAdminPassword
    private String password;

    @JsonIgnore
    private final Role role = Role.ADMIN;

    @JsonIgnore
    private final boolean activated = true;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private Date birthday;

    @NotNull
    private String country;


    private String city;


    private String street;


    private Integer houseNo;


    @JsonIgnore
    private final LocalDateTime created = LocalDateTime.now();

    @JsonIgnore
    private final LocalDateTime edited = LocalDateTime.now();

    /**
     * Empty default constructor
     */
    public AdminRegistrationDTO() {
    }

    /**
     * Full args Constructor for testing
     *
     * @param id
     * @param email
     * @param username
     * @param password
     * @param firstName
     * @param lastName
     * @param birthday
     * @param country
     * @param city
     * @param street
     * @param houseNo
     */
    public AdminRegistrationDTO(long id, String email, String username, String password, String firstName, String lastName, Date birthday, String country, String city, String street, Integer houseNo) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.country = country;
        this.city = city;
        this.street = street;
        this.houseNo = houseNo;
    }


    /**
     * Get-Method to receive the Id
     *
     * @return long with the id
     */
    public long getId() {
        return id;
    }

    /**
     * Set-Method to set the Id
     *
     * @param id long
     */
    public void setId(long id) {
        this.id = id;
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
     * Get-Method to receive the password
     *
     * @return String with password
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

    /**
     * Get-Method to receive the role
     *
     * @return Role with Admin
     */
    public Role getRole() {
        return role;
    }

    /**
     * Get-Method to receive activated
     *
     * @return true
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Get-Method to receive created Date
     *
     * @return LocalDateTime created now
     */
    public LocalDateTime getCreated() {
        return created;
    }

    /**
     * Get-Method to receive edited Date
     *
     * @return LocalDateTIme edited now
     */
    public LocalDateTime getEdited() {
        return edited;
    }

    /**
     * Get-Method to receive the firstname
     *
     * @return String firstname
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set-Method to set the firstname
     *
     * @param firstName String
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get-Method to receive the lastname
     *
     * @return lastname String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set-Method to set the lastname
     *
     * @param lastName String
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get-Method to receive the birthday
     *
     * @return
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Set-Method to set a birthday
     *
     * @param birthday Date
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Get-Method to receive the country
     *
     * @return String country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set-Method to set a country
     *
     * @param country String
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get-Method to receive the city
     *
     * @return String city
     */
    public String getCity() {
        return city;
    }

    /**
     * Set-Method to set the city
     *
     * @param city String
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get-Method to receive the street
     *
     * @return String street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Set-method to set the street
     *
     * @param street String
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Get-Method to receive the house number
     *
     * @return int houseNo
     */
    public Integer getHouseNo() {
        return houseNo;
    }

    /**
     * Set-Method to set the house number
     *
     * @param houseNo int
     */
    public void setHouseNo(Integer houseNo) {
        this.houseNo = houseNo;
    }

}
