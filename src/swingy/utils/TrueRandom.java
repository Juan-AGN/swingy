package swingy.utils;

import java.security.SecureRandom;

import java.security.NoSuchAlgorithmException;

public final class TrueRandom {
    public static void getrandom(String[] args) {
        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            
            int randomNum = secureRandom.nextInt(100);
            System.out.println("Random number: " + randomNum);
            
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Strong algorithm not available.");
        }
    }
}