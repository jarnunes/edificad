package com.puc.edificad.model.edsuser;

import com.puc.edificad.model.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(name = "seq_user", sequenceName = "seq_user", allocationSize = 1)
@Table(name = "eds_password_reset_token")
public class PasswordResetToken extends BaseEntity {

    @Serial
    private static final long serialVersionUID = -4884341597353398215L;
    private static final long EXPIRATION = 60L * 24L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator")
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(5);
}