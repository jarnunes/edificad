package com.puc.edificad.model.edsuser;

import com.jnunes.core.model.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_password_reset_token", allocationSize = 1)
@Table(name = "eds_password_reset_token")
public class PasswordResetToken extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -4884341597353398215L;
    private static final long EXPIRATION = 60L * 24L;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5);
}
