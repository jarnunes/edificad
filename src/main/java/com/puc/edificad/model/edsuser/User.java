package com.puc.edificad.model.edsuser;


import com.puc.edificad.model.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.io.Serial;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.UnaryOperator;

@Entity
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"userRoles"})
@SequenceGenerator(name = "seq_user", sequenceName = "seq_user", allocationSize = 1)
@Table(name = "eds_user")
public class User extends BaseEntity implements UserDetails {

    @Serial
    private static final long serialVersionUID = 2053508908322293256L;
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled = Boolean.TRUE;

    @Column(nullable = false)
    private Boolean locked = Boolean.TRUE;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<RoleUser> userRoles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UnaryOperator<String> mapperRole = role -> "ROLE_" + role;

        List<SimpleGrantedAuthority> sga = userRoles.stream()
                .map(RoleUser::getRole)
                .map(Enum::name)
                .map(mapperRole)
                .map(SimpleGrantedAuthority::new).toList();

        return new HashSet<>(sga);
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
