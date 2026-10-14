package mindivo.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

// Configures JWT authentication for the application.
@Configuration
public class JwtConfig {

    // Creates the secret key used to sign and verify JWT tokens
    @Bean
    public SecretKey jwtSecretKey(
            @Value("${jwt.secret}") String secret // currently came from environment variables
    ) {
        // Decode the Base64 encoded secret into bytes.
        byte[] decodedKey = Base64.getDecoder().decode(secret);

        // Create the secret key using HMAC-SHA256
        return new SecretKeySpec(decodedKey, "HmacSHA256");
    }

    // Creates the encoder used to generate JWT tokens
    @Bean
    public JwtEncoder jwtEncoder(SecretKey jwtSecretKey) {
        return new NimbusJwtEncoder(
                new ImmutableSecret<>(jwtSecretKey)
        );
    }

    // Creates the decoder used to verify JWT tokens.
    @Bean
    public JwtDecoder jwtDecoder(SecretKey jwtSecretKey) {
        return NimbusJwtDecoder
                .withSecretKey(jwtSecretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }
}