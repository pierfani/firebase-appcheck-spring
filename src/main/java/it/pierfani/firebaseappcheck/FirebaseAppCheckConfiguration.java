package it.pierfani.firebaseappcheck;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class FirebaseAppCheckConfiguration {
    @Bean
    public FirebaseAppCheckAspect firebaseAppCheckAspect() {
        return new FirebaseAppCheckAspect();
    }
}