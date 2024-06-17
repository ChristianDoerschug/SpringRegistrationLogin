package de.cd.user.model.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Token Entity class
 */
@Entity
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @Version
    @Column(columnDefinition = "integer default 0")
    private int version;


    /**
     * Default Empty Constructor
     */
    public Token() {
    }

    /**
     * Constructor with TokenString and user
     *
     * @param token String of the Token
     * @param user  identifies the User which the token belongs to
     */
    public Token(String token, User user) {
        this.token = token;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = LocalDateTime.now().plusMinutes(60);
        this.user = user;
    }

    public Token(String token, LocalDateTime createdAt, LocalDateTime expiresAt, LocalDateTime confirmedAt) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.confirmedAt = confirmedAt;
    }

    /**
     * Method to get the User
     *
     * @return User user
     */
    public User getUser() {
        return user;
    }

    /**
     * Method to set the User
     *
     * @param user User
     */
    public void setUser(User user) {
        this.user = user;
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
     * Get-Method to receive the TokenString
     *
     * @return String token
     */
    public String getToken() {
        return token;
    }

    /**
     * Set-Method to set the TokenString
     *
     * @param token String
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Get-Method to receive createdAt
     *
     * @return LocalDateTime created at
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Set-Method to set createdAt
     *
     * @param createdAt LocalDateTime
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Get-Method to get expiresAt
     *
     * @return LocalDateTime expires at
     */
    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    /**
     * Set-Method to set expiresAt
     *
     * @param expiresAt LocalDateTime
     */
    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    /**
     * Get-Method to receive confirmation Date
     *
     * @return LocalDateTime confirmedAt
     */
    public LocalDateTime getConfirmedAt() {
        return confirmedAt;
    }

    /**
     * Set-Method to set confirmedAt Date
     *
     * @param confirmedAt LocalDateTime
     */
    public void setConfirmedAt(LocalDateTime confirmedAt) {
        this.confirmedAt = confirmedAt;
    }
}
