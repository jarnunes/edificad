package com.puc.edificad.services.edsuser;

import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.BaseRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByUsernameEqualsOrEmailEquals(@Param("username") String username, @Param("email") String email);
}
