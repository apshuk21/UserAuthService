package org.apoorva.userauthservice.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;

@Configuration
public class SecurityConfig {

//    @Bean
//    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
//        http.cors().disable();
//        http.csrf().disable();
//        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.anyRequest().permitAll());
//        return http.build();
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecretKey secretKey() {
        // Use the HS256 algorithm for signing and verifying tokens
        MacAlgorithm algorithm = Jwts.SIG.HS256;

        // Get the secret key for signing and verifying tokens
        return algorithm.key().build();
    }
}
