package com.smartvoucher.webEcommercesmartvoucher.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class GoogleDriveConfig {
    private final GoogleCredential googleCredential;

    @Autowired
    @Lazy
    public GoogleDriveConfig(final GoogleCredential googleCredential) {
        this.googleCredential = googleCredential;
    }

    @Bean
    public Drive getService() throws GeneralSecurityException, IOException{
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        return new Drive.Builder(HTTP_TRANSPORT,
                JacksonFactory.getDefaultInstance(), googleCredential).build();
    }

    @Bean
    public GoogleCredential googleCredentia() throws GeneralSecurityException, IOException{
        Collection<String> stringCollection = new ArrayList<>();
        stringCollection.add("https://www.googleapis.com/auth/drive");
        HttpTransport httpTransport = new  NetHttpTransport();
        JacksonFactory jacksonFactory = new JacksonFactory();
        return new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(jacksonFactory)
                .setServiceAccountId("com-smartvoucher@smartvoucher-403009.iam.gserviceaccount.com")
                .setServiceAccountScopes(stringCollection)
                .setServiceAccountPrivateKeyFromP12File(new File("p12keys/smartvoucher-403009-ea2ab27e93c2.p12"))
                .build();
    }
}
