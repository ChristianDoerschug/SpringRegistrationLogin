package de.cd.user.inbound.security;

import de.cd.user.model.UserRepository;
import de.cd.user.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user.getUsername() != null) {
            return org.springframework.security.core.userdetails.User.withUsername
                    (user.getUsername()).password(user.getPasswordHash()).authorities(user.getRole()).build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
