package com.puc.edificad.services.edsuser;

import com.puc.edificad.model.edsuser.PasswordResetToken;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.BaseService;

import java.util.Optional;


public interface PasswordResetTokenService extends BaseService<PasswordResetToken> {

    void createPasswordResetTokenForUser(User user, String token);

    void validateResetPasswordToken(String token);

    Optional<PasswordResetToken> findByToken(String token);

}
