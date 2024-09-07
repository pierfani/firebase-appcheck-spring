package it.pierfani.firebaseappcheck;

import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class FirebaseAppCheckAspect {

    private static final String FIREBASE_APP_CHECK_HEADER = "X-Firebase-AppCheck";

    @Value("${it.pierfani.firebaseappcheck.project-number}")
    private String firebaseProjectNumber;

    @Value("${it.pierfani.firebaseappcheck.jwks-url}")
    private String firebaseJwksUrl;

    private JwkProvider provider;

    private void initializeProvider() {
        if (provider == null) {
            provider = new JwkProviderBuilder(firebaseJwksUrl)
                    .cached(10, 4, TimeUnit.HOURS)
                    .rateLimited(20, 1, TimeUnit.MINUTES)
                    .build();
        }
    }

    @Around("@annotation(it.pierfani.firebaseappcheck.FirebaseAppCheck)")
    public Object checkFirebaseAppCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String appCheckToken = request.getHeader(FIREBASE_APP_CHECK_HEADER);

        if (appCheckToken == null || appCheckToken.isEmpty()) {
            throw new FirebaseAppCheckException("Firebase App Check token is missing");
        }

        try {
            verifyToken(appCheckToken);
        } catch (FirebaseAppCheckException e) {
            throw e;
        } catch (Exception e) {
            throw new FirebaseAppCheckException("Error verifying Firebase App Check token", e);
        }

        return joinPoint.proceed();
    }

    private void verifyToken(String token) {
        if (token == null) {
            throw new FirebaseAppCheckException("Token is null");
        }

        initializeProvider();

        try {
            DecodedJWT jwt = JWT.decode(token);
            RSAPublicKey publicKey = (RSAPublicKey) provider.get(jwt.getKeyId()).getPublicKey();

            Algorithm algorithm = Algorithm.RSA256(publicKey, null);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("https://firebaseappcheck.googleapis.com/" + firebaseProjectNumber)
                    .build();

            jwt = verifier.verify(token);

            if (jwt.getExpiresAt().before(java.util.Date.from(Instant.now()))) {
                throw new FirebaseAppCheckException("Token is expired");
            }

            if (!jwt.getAudience().contains("projects/" + firebaseProjectNumber)) {
                throw new FirebaseAppCheckException("Token audience does not match the project");
            }

        } catch (JWTVerificationException e) {
            throw new FirebaseAppCheckException("JWT verification failed", e);
        } catch (Exception e) {
            throw new FirebaseAppCheckException("Error during token verification", e);
        }
    }
}