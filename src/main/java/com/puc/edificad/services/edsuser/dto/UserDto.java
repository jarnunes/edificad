package com.puc.edificad.services.edsuser.dto;

import com.puc.edificad.model.edsuser.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private boolean enabled;
    private Role role;

    private String password;
}
