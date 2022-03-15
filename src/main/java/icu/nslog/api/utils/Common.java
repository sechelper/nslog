package icu.nslog.api.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

public class Common {
    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    public static String base64Encode(String s){
        return Base64.getEncoder().encodeToString(s.getBytes());
    }

    public static String base64Decode(String s){
        byte[] decodedBytes = Base64.getDecoder().decode(s);
        return new String(decodedBytes);
    }

    public static void main(String[] args) throws Exception {
        int length = 32;
        boolean useLetters = true;
        boolean useNumbers = false;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        System.out.println(generatedString);

        System.out.println(generateUUID());

    }

}
