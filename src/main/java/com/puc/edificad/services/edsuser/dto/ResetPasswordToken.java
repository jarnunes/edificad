package com.puc.edificad.services.edsuser.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordToken {
    String newPassword;
    String newPasswordConfirmation;
    String token;
}
