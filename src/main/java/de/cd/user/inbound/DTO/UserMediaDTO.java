package de.cd.user.inbound.DTO;

/**
 * DTO to get numberOfMedaPosts from MediaManagement Microservice
 */
public class UserMediaDTO {
    private long userId;


    private Integer numberOfPosts;


    public UserMediaDTO() {
    }

    public UserMediaDTO(long userId, Integer numberOfPosts) {
        this.userId = userId;
        this.numberOfPosts = numberOfPosts;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Integer getNumberOfPosts() {
        return numberOfPosts;
    }

    public void setNumberOfPosts(Integer numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

}
