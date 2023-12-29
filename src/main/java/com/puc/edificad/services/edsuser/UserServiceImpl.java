package com.puc.edificad.services.edsuser;

import com.puc.edificad.commons.utils.AuthUtils;
import com.jnunes.core.commons.utils.ValidationUtils;
import com.puc.edificad.mapper.UserMapper;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.BaseServiceImpl;
import com.puc.edificad.services.edsuser.dto.ResetPasswordToken;
import com.puc.edificad.services.edsuser.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.puc.edificad.commons.utils.AuthUtils.encode;


@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    private final PasswordResetTokenService resetTokenService;

    @Autowired
    public UserServiceImpl(UserRepository repositoryIn, UserMapper userMapperIn, PasswordResetTokenService resetTokenServiceIn){
        this.repository = repositoryIn;
        this.mapper = userMapperIn;
        this.resetTokenService = resetTokenServiceIn;
    }

    @Override
    public User save(User entity) {
        String username = repository.findUserByUsername(entity.getUsername()).map(User::getUsername).orElse(null);
        String email = repository.findUserByEmail(entity.getEmail()).map(User::getEmail).orElse(null);
//
//        ValidationUtils.validate(entity.getId() == null || username == null, "eds.err.user.already.exists",
//                "username", username);
//        ValidationUtils.validate(ObjectUtils.allNull(entity.getId(), email), "eds.err.user.already.exists",
//                "email", email);

        entity.setPassword(encode(entity.getPassword()));
        entity.getUserRoles().forEach(it -> it.setUser(entity));
        return super.save(entity);
    }

    @Override
    public User update(User entity) {
        entity.getUserRoles().forEach(it -> it.setUser(entity));
        return super.update(entity);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findUserByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    @Override
    public void resetPassword(String username, String password, String passwordConfirmation) {
        ValidationUtils.validateEquals(password, passwordConfirmation, "eds.err.password.does.not.matches");
        User user = repository.findUserByUsername(username).orElseThrow();
        user.setPassword(encode(password));
        update(user);
    }

    @Override
    public UserDto save(UserDto dto) {
        final User entity = mapper.toEntity(dto);
        final String newPassword = AuthUtils.gerarSenha();
        entity.setPassword(newPassword);
        this.save(entity);
        UserDto newUserDto = mapper.toDto(entity);
        newUserDto.setPassword(newPassword);
        return newUserDto;
    }

    @Override
    public Optional<UserDto> findByIdDto(Long id) {
        return this.findById(id).map(mapper::toDto);
    }

    @Override
    public Optional<User> getEntityWithSearchAttrs(String searchValue) {
        User user = new User();
        user.setUsername(searchValue);
        user.setFullName(searchValue);
        user.setEmail(searchValue);
        user.setEnabled(null);
        user.setLocked(null);
        return Optional.of(user);
    }

    @Override
    public UserDto update(UserDto dto) {
        final User entity = mapper.toEntity(dto);
        this.update(entity);
        return mapper.toDto(entity);
    }

    @Override
    public void resetPasswordToken(ResetPasswordToken resetPassword) {
        User user = resetTokenService.findByToken(resetPassword.getToken()).orElseThrow().getUser();
        this.resetPassword(user.getUsername(), resetPassword.getNewPassword(), resetPassword.getNewPasswordConfirmation());
    }
}
