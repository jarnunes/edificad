package com.puc.edificad.services.edsuser;

import com.puc.edificad.commons.exceptions.ValidationException;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repositoryIn){
        this.repository = repositoryIn;
    }

    @Override
    public User save(User entity) {
        repository.findUserByUsername(entity.getUsername()).map(User::getUsername)
            .ifPresent(username -> lancarExcecaoUsuarioJaExistente("username", username));
        repository.findUserByEmail(entity.getEmail()).map(User::getEmail)
            .ifPresent(email -> lancarExcecaoUsuarioJaExistente("email", email));

        entity.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(entity.getPassword()));
        entity.getUserRoles().forEach(it -> it.setUser(entity));
        return super.save(entity);
    }

    private void lancarExcecaoUsuarioJaExistente(String attrName, String attrValue){
        throw new ValidationException("eds.err.user.already.exists", attrName, attrValue);
    }

    @Override
    public User update(User entity) {
        entity.getUserRoles().forEach(it -> it.setUser(entity));
        return super.update(entity);
    }


}
