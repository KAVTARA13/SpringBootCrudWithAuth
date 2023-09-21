package com.example.crud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    protected InMemoryUserDetailsManager userDetailsService(){
        UserDetails userDetails = User
                .withUsername("luka")
                .password("{noop}password")
                .roles("USER")
                .build();
        UserDetails userDetails2 = User
                .withUsername("admin")
                .password("{noop}password")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(userDetails,userDetails2);
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http)throws Exception{
        http
                .authorizeHttpRequests(form->form
                        .requestMatchers(new AntPathRequestMatcher("/"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/edit/*")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/delete/*")).hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .logout(form -> form
                .logoutUrl("/logout")
                .permitAll());
        return http.build();
    }
}
