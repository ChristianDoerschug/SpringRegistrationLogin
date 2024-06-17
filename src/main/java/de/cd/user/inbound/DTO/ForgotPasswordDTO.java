package de.cd.user.inbound.DTO;

import javax.validation.constraints.Email;

public class ForgotPasswordDTO {

    @Email
    private String email;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
