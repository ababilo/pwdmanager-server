package ababilo.pwd.server.util;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ababilo on 08.11.16.
 */
public class KeystoreUtil {

    private static final String FILE = "keystore";
    private static class Holder {
        private static final byte[] key = loadKey();
    }

    private KeystoreUtil() {
    }

    private static byte[] loadKey() {
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(FILE)) {
            if (stream != null) {
                return Base64Utils.decodeFromString(IOUtils.readLines(stream, Charsets.UTF_8).get(0));
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't load key");
        }

        throw new RuntimeException("Can't load key");
    }

    public static byte[] getKey() {
        return Holder.key;
    }
}
