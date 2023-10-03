package com.puc.edificad.services.edsuser;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.puc.edificad.commons.config.Message;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.edsuser.dto.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@Service
public class TokenService {

    private Message msg;

    @Autowired
    public void setMsg(Message message) {
        this.msg = message;
    }

    public String buildToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("id", user.getId())
                .withExpiresAt(LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00")))
                .sign(getAlgorithm());
    }

    public AccessToken buildAccessToken(User user){
        final String token = buildToken(user);
        return new AccessToken(token);
    }

    public String getSubject(String token) {
        return JWT.require(getAlgorithm())
                .build()
                .verify(token)
                .getSubject();
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(msg.get("eds.secret.token"));
    }
}
