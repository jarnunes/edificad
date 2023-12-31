package com.puc.edificad.services.edsuser;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jnunes.core.commons.utils.DateTimeUtils;
import com.puc.edificad.model.edsuser.User;
import com.puc.edificad.services.ConfiguracaoService;
import com.puc.edificad.services.edsuser.dto.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TokenService {
    private ConfiguracaoService configuracaoService;

    @Autowired
    public void setConfiguracaoService(ConfiguracaoService serviceIn) {
        this.configuracaoService = serviceIn;
    }

    public String buildToken(User user) {
        return JWT.create()
            .withSubject(user.getUsername())
            .withClaim("id", user.getId())
            .withExpiresAt(DateTimeUtils.instantOf(configuracaoService.obterConfiguracao().getTokenExpiresAt()))
            .sign(getAlgorithm());
    }

    public AccessToken buildAccessToken(User user) {
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
        return Algorithm.HMAC256(configuracaoService.obterConfiguracao().getTokenSecretKey());
    }
}
