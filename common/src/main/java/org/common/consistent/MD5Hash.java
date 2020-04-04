package org.common.consistent;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md 5 hash
 *
 * @author nurhier
 * @date 2020/4/4
 **/
public class MD5Hash implements HashFunction {
    MessageDigest instance;

    public MD5Hash() {
        try {
            instance = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Long hash(String key) {
        instance.reset();
        instance.update(key.getBytes());
        byte[] digest = instance.digest();

        long h = 0;
        for (int i = 0; i < 4; i++) {
            h <<= 8;
            h |= ((int) digest[i]) & 0xFF;
        }
        return h;
    }
}
