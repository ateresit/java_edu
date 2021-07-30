package ru.geekbrains.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public void authConfig(AuthenticationManagerBuilder auth,
                           PasswordEncoder passwordEncoder ) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("mem_user")
                .password(passwordEncoder.encode("qwe"))
                .roles("ADMIN")
                .and()
                .withUser(passwordEncoder.encode("mem_guest"))
                .password("asd")
                .roles("GUEST");
    }
}
