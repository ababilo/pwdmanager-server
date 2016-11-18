package ababilo.pwd.server.util;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;

/**
 * Created by ababilo on 18.11.16.
 */
public class HashingUtil {

    public static byte[] PBKDF2(char[] input, byte[] salt) {
        final int iterations = 10000;

        // Generate a 256-bit key
        final int outputKeyLength = 256;

        try {
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec keySpec = new PBEKeySpec(input, salt, iterations, outputKeyLength);
            SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
            return secretKey.getEncoded();
        } catch (Exception e) {
            return null;
        }
    }
}
