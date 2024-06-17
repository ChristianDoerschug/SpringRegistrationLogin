package de.cd.user.inbound.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityAndAuthenticationConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtProvider jwtProvider;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .requestMatchers(EndpointRequest.to(HealthEndpoint.class)).permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/user/login").permitAll()
                .antMatchers("/user/registration").permitAll()
                .antMatchers(("/user/forgotPassword")).permitAll()
                .antMatchers("/admin/registration").permitAll()
                .antMatchers("/user/forgotPassword").permitAll()
                .antMatchers("/user/activate").permitAll()
                .antMatchers("/admin/health").permitAll()
                .antMatchers("/fallback").permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
        http.headers().frameOptions().disable();
        http.apply(new JwtFilterConfigurer(jwtProvider));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
