package org.apoorva.userauthservice.controllers;

import org.antlr.v4.runtime.misc.Pair;
import org.apoorva.userauthservice.dtos.LoginRequestDTO;
import org.apoorva.userauthservice.dtos.SignupRequestDTO;
import org.apoorva.userauthservice.dtos.UserDTO;
import org.apoorva.userauthservice.models.User;
import org.apoorva.userauthservice.services.IAuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signup(@RequestBody SignupRequestDTO signupRequest) {
        User user = authService.signUp(signupRequest.getEmail(), signupRequest.getPassword());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(from(user));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        Pair<User, String> response = authService.logIn(loginRequest.getEmail(), loginRequest.getPassword());

        User user = response.a;
        String token = response.b;

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, token);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(from(user));
    }

    public UserDTO from(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setRoles(user.getRoles());

        return userDTO;
    }
}
