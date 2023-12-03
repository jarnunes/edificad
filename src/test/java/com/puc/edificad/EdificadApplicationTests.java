package com.puc.edificad;

import com.puc.edificad.services.edsuser.AuthenticationService;
import com.puc.edificad.web.api.auth.AuthController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class EdificadApplicationTests {

    @Autowired
    private AuthController authController;

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    void contextLoads() {
        assertNotNull(authController);
        assertNotNull(authenticationService);
    }
}
