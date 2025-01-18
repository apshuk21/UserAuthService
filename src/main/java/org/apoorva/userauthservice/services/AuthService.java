package org.apoorva.userauthservice.services;

import org.apoorva.userauthservice.exceptions.PasswordMismatchException;
import org.apoorva.userauthservice.exceptions.UserAlreadyExistException;
import org.apoorva.userauthservice.exceptions.UserNotRegisteredException;
import org.apoorva.userauthservice.models.Role;
import org.apoorva.userauthservice.models.User;
import org.apoorva.userauthservice.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    public User logIn(String username, String password) {
        Optional<User> userOptional = userRepo.findByEmail(username);

        if (userOptional.isEmpty()) {
            throw new UserNotRegisteredException("User not found");
        }

        User user = userOptional.get();
        String hashedPassword = user.getPassword();

        if (!bcryptPasswordEncoder.matches(password, hashedPassword)) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        return user;
    }
}
