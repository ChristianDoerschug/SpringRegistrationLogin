package de.cd.user.inbound.DTO;

import java.util.Date;

/**
 * UserGetDTO class
 */
public class UserGetDto {

    //private long id;

    private String email;

    private String username;

    private boolean activated;

    private String firstName;

    private String lastName;

    private Date birthday;

    private String country;

    private String city;

    private String street;

    private Integer houseNo;

    /**
     * Empty default constructor
     */
    public UserGetDto() {
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
     * Get-Method to receive activated
     *
     * @return activated boolean
     */
    public boolean isActivated() {
        return activated;
    }

    /**
     * Set Method to set activated
     *
     * @param activated boolean
     */
    public void setActivated(boolean activated) {
        this.activated = activated;
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
