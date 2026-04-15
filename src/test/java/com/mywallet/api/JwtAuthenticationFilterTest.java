package com.mywallet.api.configuration;

import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import javax.crypto.SecretKey;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "jwt.secret=mySuperSecretKeyThatIsLongEnoughForHS512Algorithm",
    "jwt.expiration=3600000"
})
public class JwtAuthenticationFilterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private String validToken;

    @BeforeEach
    public void setUp() {
        validToken = jwtTokenProvider.generateToken("test@example.com");
    }

    /**
     * Testa que rotas públicas são acessíveis sem JWT
     */
    @Test
    public void testPublicRouteWithoutToken() throws Exception {
        mockMvc.perform(post("/api/v1/user/login")
                .contentType("application/json")
                .content("{\"email\":\"test@example.com\",\"password\":\"password\"}"))
                .andExpect(status().isNotFound()); // Falha por credenciais inválidas, não por JWT
    }

    /**
     * Testa que rotas protegidas rejeitam requisições sem JWT
     */
    @Test
    public void testProtectedRouteWithoutToken() throws Exception {
        mockMvc.perform(get("/api/v1/wallet"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Testa que rotas protegidas aceitam requisições com JWT válido
     */
    @Test
    public void testProtectedRouteWithValidToken() throws Exception {
        UUID walletId = UUID.randomUUID();
        mockMvc.perform(get("/api/v1/wallettransactions/lastTransactions/" + walletId)
                .header("Authorization", "Bearer " + validToken))
                .andExpect(status().isOk());
    }

    /**
     * Testa que JWT inválido é rejeitado
     */
    @Test
    public void testProtectedRouteWithInvalidToken() throws Exception {
        mockMvc.perform(get("/api/v1/wallet")
                .header("Authorization", "Bearer invalid_token_xyz"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Testa que token com formato incorreto é rejeitado
     */
    @Test
    public void testProtectedRouteWithWrongBearerFormat() throws Exception {
        mockMvc.perform(get("/api/v1/wallet")
                .header("Authorization", "Token " + validToken))
                .andExpect(status().isUnauthorized());
    }
}

