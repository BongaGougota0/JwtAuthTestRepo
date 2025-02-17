package za.co.bonga.jwt_practice;


import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;

public class CreateSecretKeyTest {

    @Test
    public void createSecretKey() {
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.printf("The generated secret key is [%s]",encodedKey);
    }
}
