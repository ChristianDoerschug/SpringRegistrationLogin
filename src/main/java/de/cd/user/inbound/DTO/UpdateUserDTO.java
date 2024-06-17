package de.cd.user.inbound.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;

/**
 * UpdateUserDTO class
 */
public class UpdateUserDTO {


    @Id
    private Long id;

    @Email
    private String email;

    private String firstName;

    private String lastName;

    private String country;

    private String city;

    private String street;

    private Integer houseNo;

    @JsonIgnore
    private final LocalDateTime edited = LocalDateTime.now();

    /**
     * Empty default Constructor
     */
    public UpdateUserDTO() {
    }

    /**
     * Get Method to receive the Id
     *
     * @return Long Id
     */
    public Long getId() {
        return id;
    }

    /**
     * Set Method to set the Id
     *
     * @param id Long
     */
    public void setId(Long id) {
        this.id = id;
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
