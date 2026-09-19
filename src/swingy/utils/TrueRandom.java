package swingy.utils;

import java.security.SecureRandom;

import java.security.NoSuchAlgorithmException;

public final class TrueRandom {
    public static int getrandom(int max) {
        if (max <= 0)
            return (0);

        try {
            SecureRandom secureRandom = SecureRandom.getInstanceStrong();
            int randomNum = secureRandom.nextInt(max);

            return (randomNum);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No strong SecureRandom algorithm available.aa");
        }
        return (0);
    }
}