package com.suensky.example;

import org.apache.commons.codec.digest.DigestUtils;

public class App {
    public static void main(String[] args) {
       FutureExercise.checkAndSavePrice();
    }

    public static String sha256hex(String input) {
        return DigestUtils.sha256Hex(input);
    }
}
