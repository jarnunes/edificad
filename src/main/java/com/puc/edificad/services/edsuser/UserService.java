package com.puc.edificad.services.edsuser;

import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.BaseService;
import com.puc.edificad.services.edsuser.dto.Login;
import com.puc.edificad.services.edsuser.dto.ResetPasswordToken;
import com.puc.edificad.services.edsuser.dto.UserDto;

import java.util.Optional;

public interface UserService extends BaseService<User> {

    void resetPassword(String username, String password, String passwordConfirmation);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    UserDto save(UserDto dto);

    UserDto update(UserDto dto);

    Optional<UserDto> findByIdDto(Long id);

    void resetPasswordToken(ResetPasswordToken resetPassword);
}
