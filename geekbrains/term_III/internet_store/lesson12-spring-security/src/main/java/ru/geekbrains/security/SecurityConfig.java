package ru.geekbrains.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public void authConfig(AuthenticationManagerBuilder auth,
                           PasswordEncoder passwordEncoder,
                           UserAuthService userAuthService) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("mem_user")
                .password(passwordEncoder.encode("qwe"))
                .roles("ADMIN")
                .and()
                .withUser("mem_guest")
                .password(passwordEncoder.encode("asd"))
                .roles("GUEST")
                .and()
                .withUser("mem_super_user")
                .password(passwordEncoder.encode("qwe"))
                .roles("SUPER_ADMIN");

        auth.userDetailsService(userAuthService);
    }

    @Configuration
    public static class UiWebSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests()
                    .antMatchers("/**/*.css", "/**/*.js").permitAll()
                    .antMatchers("/product/**").permitAll()
//                    .antMatchers("/user/**").hasRole("ADMIN")
                    .antMatchers("/user/**").hasAnyRole("ADMIN, SUPER_ADMIN")
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login_processing")
                    .defaultSuccessUrl("/user")
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/access_denied");
        }
    }
}
