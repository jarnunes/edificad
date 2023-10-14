package com.puc.edificad.services.edsuser;

import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.BaseService;
import com.puc.edificad.services.edsuser.dto.Login;

import java.util.Optional;

public interface UserService extends BaseService<User> {

    void resetPassword(Login login);

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
