package com.smartvoucher.webEcommercesmartvoucher.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomCodeHandler {
    public String generateRandomChars(int length) {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(
                    candidateChars.charAt(
                        random.nextInt(candidateChars.length())));
        }
        return sb.toString();
    }
}
