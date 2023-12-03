package com.puc.edificad.services.edsuser;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.utils.UserUtils;
import com.puc.edificad.mapper.UserMapper;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.BaseServiceImpl;
import com.puc.edificad.services.edsuser.dto.Login;
import com.puc.edificad.services.edsuser.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.puc.edificad.commons.utils.UserUtils.encode;


@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private UserRepository repository;
    private UserMapper mapper;

    @Autowired
    public void setRepository(UserRepository repositoryIn) {
        this.repository = repositoryIn;
    }

    @Autowired
    public void setMapper(UserMapper mapperIn) {
        this.mapper = mapperIn;
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
        repository.findById(entity.getId()).map(User::getPassword).ifPresent(entity::setPassword);
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
    public void resetPassword(Login login) {
        Optional.of(login).map(Login::username).flatMap(repository::findUserByUsername)
                .ifPresent(user -> setNewPasswordUpdateUser(login, user));
    }

    private void setNewPasswordUpdateUser(Login login, User user) {
        user.setPassword(encode(login.password()));
        super.update(user);
    }

    @Override
    public String resetPassword(Long id) {
        User user =  this.findById(id).orElseThrow(EntityNotFoundException::notFoundForId);
        String password = UserUtils.gerarSenha();
        resetPassword(new Login(user.getUsername(), password));
        return password;
    }

    @Override
    public UserDto save(UserDto dto) {
        final String senha = UserUtils.gerarSenha();
        dto.setPassword(senha);
        User entity = mapper.toEntity(dto);
        this.save(entity);
        UserDto newDto =  mapper.toDto(entity);
        newDto.setPassword(senha);
        return newDto;
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
        return Optional.of(user);
    }
}
