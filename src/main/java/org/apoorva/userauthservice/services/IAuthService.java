package org.apoorva.userauthservice.services;

import org.apoorva.userauthservice.models.User;

public interface IAuthService {

    User signUp(String username, String password);

    User logIn(String username, String password);
}
