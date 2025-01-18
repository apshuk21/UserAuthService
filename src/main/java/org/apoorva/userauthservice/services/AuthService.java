package org.apoorva.userauthservice.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import org.antlr.v4.runtime.misc.Pair;
import org.apoorva.userauthservice.exceptions.PasswordMismatchException;
import org.apoorva.userauthservice.exceptions.UserAlreadyExistException;
import org.apoorva.userauthservice.exceptions.UserNotRegisteredException;
import org.apoorva.userauthservice.models.Role;
import org.apoorva.userauthservice.models.User;
import org.apoorva.userauthservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService implements IAuthService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    public AuthService(UserRepo userRepo, BCryptPasswordEncoder bcryptPasswordEncoder) {
        this.userRepo = userRepo;
        this.bcryptPasswordEncoder = bcryptPasswordEncoder;
    }

    @Override
    public User signUp(String email, String password) {
        Optional<User> userOptional = userRepo.findByEmail(email);

        if (userOptional.isPresent()) {
            throw new UserAlreadyExistException("User already exists");
        }

        User user = new User();

        Role role = new Role();
        role.setRoleName("ROLE_USER");

        user.setEmail(email);
        user.setPassword(bcryptPasswordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        List<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);

        return userRepo.save(user);
    }

    @Override
    public Pair<User, String> logIn(String username, String password) {
        Optional<User> userOptional = userRepo.findByEmail(username);

        if (userOptional.isEmpty()) {
            throw new UserNotRegisteredException("User not found");
        }

        User user = userOptional.get();
        String hashedPassword = user.getPassword();

        if (!bcryptPasswordEncoder.matches(password, hashedPassword)) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        // JWT Payload
//        String message = "{\n" +
//                "   \"email\": \"apshuk21@gmail.com\",\n" +
//                "   \"roles\": [\n" +
//                "      \"instructor\",\n" +
//                "      \"buddy\"\n" +
//                "   ],\n" +
//                "   \"expirationDate\": \"2ndApril2025\"\n" +
//                "}";

        // Payload as bytes array
//        byte[] content = message.getBytes(StandardCharsets.UTF_8);

        // Claim
        Map<String,Object> payload = new HashMap<>();
        long nowInMillis = System.currentTimeMillis();
        payload.put("iat",nowInMillis);
        payload.put("exp",nowInMillis+100000);
        payload.put("userId",user.getId());
        payload.put("iss","xyz.com");
        payload.put("scope",user.getRoles().stream().map(Role::getRoleName).collect(Collectors.toSet()));

        // Use the HS256 algorithm for signing and verifying tokens
        MacAlgorithm algorithm = Jwts.SIG.HS256;

        // Get the secret key for signing and verifying tokens
        SecretKey secretKey = algorithm.key().build();

        // Create a JWT token
//        String token = Jwts.builder().content(content).signWith(secretKey).compact();
        String token = Jwts.builder().claims(payload).signWith(secretKey).compact();

        return new Pair<>(user, token);
    }
}
