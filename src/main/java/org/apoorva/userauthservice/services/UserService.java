package org.apoorva.userauthservice.services;

import lombok.RequiredArgsConstructor;
import org.apoorva.userauthservice.models.User;
import org.apoorva.userauthservice.repos.UserRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public User getUserDetails(long id) {
       return userRepo.findById(id).orElse(null);
    }

}
