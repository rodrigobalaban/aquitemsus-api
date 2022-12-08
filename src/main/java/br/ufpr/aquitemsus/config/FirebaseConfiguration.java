package br.ufpr.aquitemsus.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfiguration {

    private final String firebaseCredentials;

    public FirebaseConfiguration(@Value("${GOOGLE_CREDENTIALS}") String firebaseCredentials) {
        this.firebaseCredentials = firebaseCredentials;
    }

    @Bean
    FirebaseMessaging firebaseMessaging() throws IOException {
        InputStream credentialsStream = new ByteArrayInputStream(firebaseCredentials.getBytes());
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(credentialsStream);

        FirebaseOptions firebaseOptions = FirebaseOptions
                .builder()
                .setCredentials(googleCredentials)
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(firebaseOptions, "aqui-tem-sus");

        return FirebaseMessaging.getInstance(app);
    }
}
