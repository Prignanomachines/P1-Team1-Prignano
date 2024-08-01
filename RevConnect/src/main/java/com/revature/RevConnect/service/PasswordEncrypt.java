package com.revature.RevConnect.service;

public class PasswordEncrypt {

    //This encrypt algorithm will have a lot of collision, but for the project this will be fine
    public static String encrypt(String password) {

        StringBuilder encryptPassword = new StringBuilder();

        for (int i = 0; i < password.length(); i++) {
            encryptPassword.append(((password.charAt(i)) % 26) + (int)'A');
        }

        return encryptPassword.toString();
    }

    public static boolean passwordMatch(String password, String encodedPassword) {

        return encrypt(password).equals(encodedPassword);
    }

}
