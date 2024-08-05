package com.example.stocksimulation.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {
    @Value("${firebase-realtime-database.database-url}")
    private String url;

    @Bean
    public FirebaseApp init() {
        try {
            FileInputStream serviceAccount = new FileInputStream("src/main/resources/stock-simul-aeb30-firebase-adminsdk-m68lu-21eeb6e2b2.json");
            FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(url)
                    .build();
            return FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to initialize FirebaseApp", e);
        }
    }

    @Bean
    public FirebaseDatabase firebaseDatabase(FirebaseApp firebaseApp) {
        return FirebaseDatabase.getInstance(firebaseApp);
    }

    @Bean
    public DatabaseReference databaseReference(FirebaseDatabase firebaseDatabase) {
        return firebaseDatabase.getReference("stocks");
    }
}
