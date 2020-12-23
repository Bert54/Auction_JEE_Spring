package com.ul.gla.auctionbackspring.services;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Component
public class HashingServiceImpl implements HashingService {

    private static final String SALT = "12345";
    private static final int ITERATIONS = 20000;
    private static final int KEYLENGTH = 512;

    @Override
    public String hashPassword(final String pass) {

        char[] password = pass.toCharArray();
        byte[] salt = SALT.getBytes();

        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEYLENGTH);
            SecretKey key = skf.generateSecret(spec);
            byte[] res = key.getEncoded();
            return Hex.encodeHexString(res);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean validatePassword(String userPass, String storedPass) {
        String hashedUserPass = this.hashPassword(userPass);
        try {
            return this.slowEquals(Hex.decodeHex(hashedUserPass.toCharArray()), Hex.decodeHex(storedPass.toCharArray()));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean slowEquals(byte[] a, byte[] b)
    {
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

}
