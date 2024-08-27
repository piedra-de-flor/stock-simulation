package com.example.stocksimulation.configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;

@Configuration
public class FirebaseConfig {
    @Value("${firebase-realtime-database.database-url}")
    private String url;

    @Value("${firebase-realtime-database.database-name}")
    private String databaseName;

    @Bean
    public FirebaseApp init() {
        try {
            String credentialPath;
            if (new File("src/main/resources/stock-simul-aeb30-firebase-adminsdk-m68lu-6838ec34fa.json").exists()) {
                credentialPath = "src/main/resources/stock-simul-aeb30-firebase-adminsdk-m68lu-6838ec34fa.json";
            } else {
                credentialPath = "/app/src/main/resources/stock-simul-aeb30-firebase-adminsdk-m68lu-6838ec34fa.json";
            }

            FileInputStream serviceAccount = new FileInputStream(credentialPath);
            FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(url)
                    .build();
            return FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            throw new IllegalArgumentException("Firebase 초기화 중 오류 발생: " + e.getMessage(), e);
        }
    }

    @Bean
    public FirebaseDatabase firebaseDatabase(FirebaseApp firebaseApp) {
        return FirebaseDatabase.getInstance(firebaseApp);
    }

    @Bean
    public DatabaseReference databaseReference(FirebaseDatabase firebaseDatabase) {
        return firebaseDatabase.getReference(databaseName);
    }
}
