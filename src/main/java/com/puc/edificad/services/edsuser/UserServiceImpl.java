package com.puc.edificad.services.edsuser;

import com.puc.edificad.commons.exceptions.ValidationException;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.BaseServiceImpl;
import com.puc.edificad.services.edsuser.dto.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.puc.edificad.commons.utils.UserUtils.encode;


@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repositoryIn) {
        this.repository = repositoryIn;
    }

    @Override
    public User save(User entity) {
        repository.findUserByUsername(entity.getUsername()).map(User::getUsername)
            .ifPresent(username -> lancarExcecaoUsuarioJaExistente("username", username));
        repository.findUserByEmail(entity.getEmail()).map(User::getEmail)
            .ifPresent(email -> lancarExcecaoUsuarioJaExistente("email", email));

        entity.setPassword(encode(entity.getPassword()));
        entity.getUserRoles().forEach(it -> it.setUser(entity));
        return super.save(entity);
    }

    private void lancarExcecaoUsuarioJaExistente(String attrName, String attrValue) {
        throw new ValidationException("eds.err.user.already.exists", attrName, attrValue);
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

    private void setNewPasswordUpdateUser(Login login, User user){
        user.setPassword(encode(login.password()));
        super.update(user);
    }

}
