package org.apoorva.userauthservice.controllers;

import lombok.RequiredArgsConstructor;
import org.apoorva.userauthservice.dtos.UserDTO;
import org.apoorva.userauthservice.models.User;
import org.apoorva.userauthservice.services.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDTO getUserById(@PathVariable("id") long id) {
        User user = userService.getUserDetails(id);

        return from(user);
    }

    public UserDTO from(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
//        userDTO.setRoles(user.getRoles());

        return userDTO;
    }
}
