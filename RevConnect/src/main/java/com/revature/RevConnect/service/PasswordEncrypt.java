package com.revature.RevConnect.service;

import java.util.Random;

public class PasswordEncrypt {

    //This encryption algorithm will have a lot of collision, but for the project this will be fine
    public static String encrypt(String password) {

        int seed = (int)password.charAt(0);
        Random generator = new Random(seed);
        int RNG = generator.nextInt();

        StringBuilder encryptPassword = new StringBuilder();

        for (int i = 0; i < password.length(); i++) {
            encryptPassword.append(((password.charAt(i)) % RNG));
        }

        return encryptPassword.toString();
    }

    public static boolean passwordMatch(String password, String encodedPassword) {

        return encrypt(password).equals(encodedPassword);
    }

}
