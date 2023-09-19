package com.puc.edificad.model.edsuser;

import com.puc.edificad.model.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@EqualsAndHashCode(callSuper = true, exclude = {"user"})
@SequenceGenerator(name = "seq_role_user", sequenceName = "seq_role_user", allocationSize = 1)
@Table(name = "eds_role_user")
public class RoleUser extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 7969397870584868440L;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
