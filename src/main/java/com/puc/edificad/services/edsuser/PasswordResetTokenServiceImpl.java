package com.puc.edificad.services.edsuser;

import com.puc.edificad.commons.exceptions.EntityNotFoundException;
import com.puc.edificad.commons.utils.ValidationUtils;
import com.puc.edificad.model.edsuser.PasswordResetToken;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PasswordResetTokenServiceImpl extends BaseServiceImpl<PasswordResetToken> implements PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public PasswordResetTokenServiceImpl(PasswordResetTokenRepository tokenRepositoryIn) {
        this.passwordResetTokenRepository = tokenRepositoryIn;
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);

        passwordResetTokenRepository.findDistinctByUser(user).ifPresent(passwordResetToken -> {
            passwordResetTokenRepository.delete(passwordResetToken);
            passwordResetTokenRepository.flush();
        });

        passwordResetTokenRepository.save(resetToken);
    }

    @Override
    public void validateResetPasswordToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findDistinctByToken(token)
                .orElseThrow(() -> new EntityNotFoundException("eds.err.token.nao.encontrado"));

        ValidationUtils.validateIsAfterNow(passwordResetToken.getExpiryDate(), "eds.expired.restore.password.token");
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return passwordResetTokenRepository.findDistinctByToken(token);
    }

}
