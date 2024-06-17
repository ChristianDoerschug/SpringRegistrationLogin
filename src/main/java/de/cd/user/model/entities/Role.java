package de.cd.user.model.entities;

import org.springframework.security.core.GrantedAuthority;

/**
 * Role enum
 */
public enum Role implements GrantedAuthority {
    ADMIN, USER, MODERATOR;

    @Override
    public String getAuthority() {
        return name();
    }
}
