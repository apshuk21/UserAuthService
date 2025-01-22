package org.apoorva.userauthservice.services;

import org.antlr.v4.runtime.misc.Pair;
import org.apoorva.userauthservice.models.User;

public interface IAuthService {

    User signUp(String username, String password);

    Pair<User, String> logIn(String username, String password);

    Boolean validateToken(String token, Long userId );
}
