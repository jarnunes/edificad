package com.puc.edificad.services.edsuser;

import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    Optional<User> findUserByUsername(String username);
}
