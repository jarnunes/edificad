package com.puc.edificad.services.edsuser;

import com.puc.edificad.model.edsuser.PasswordResetToken;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.BaseRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends BaseRepository<PasswordResetToken> {

    Optional<PasswordResetToken> findDistinctByToken(@Param("token") String token);

    Optional<PasswordResetToken> findDistinctByUser(@Param("user") User user);
}
