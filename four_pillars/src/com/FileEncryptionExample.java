package com;

import java.io.*;
import java.nio.file.Files;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class FileEncryptionExample {

    public static void main(String[] args) {
        try {
            String inputFilePath = "C:/Users/maha9/OneDrive/Desktop/outputFile.txt"; // replace with the path of your input file
            String outputFilePath = "C:/Users/maha9/OneDrive/Desktop/encrypted.txt"; // replace with the path where you want to save the encrypted file
            String decryptedFilePath = "C:/Users/maha9/OneDrive/Desktop/decrypted.txt"; // replace with the path where you want to save the decrypted file
            String password = "mySecretPassword"; // replace with your own secret password

            byte[] input = Files.readAllBytes(new File(inputFilePath).toPath());

            byte[] encrypted = encrypt(input, password);

            saveToFile(encrypted, outputFilePath);

            byte[] decrypted = decrypt(encrypted, password);

            saveToFile(decrypted, decryptedFilePath);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static byte[] encrypt(byte[] input, String password) throws Exception {
        SecretKeySpec keySpec = getKeySpec(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }

    private static byte[] decrypt(byte[] input, String password) throws Exception {
        SecretKeySpec keySpec = getKeySpec(password);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        return cipher.doFinal(input);
    }

    private static SecretKeySpec getKeySpec(String password) throws Exception {
        byte[] keyBytes = password.getBytes();
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        keyBytes = sha.digest(keyBytes);
        keyBytes = copyOf(keyBytes, 16);
        return new SecretKeySpec(keyBytes, "AES");
    }

    private static byte[] copyOf(byte[] original, int newLength) {
        byte[] copy = new byte[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    private static void saveToFile(byte[] content, String filePath) throws Exception {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        outputStream.write(content);
        outputStream.close();
    }
}

