package br.com.gori.scb.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Leonardo
 */
public class Criptografia {

    private static MessageDigest md = null;

    public static String encriptar(String password) {
        try {
            if (password == null || password.equals("")) {
                return password;
            }
            if (md == null) {
                md = MessageDigest.getInstance("SHA-256");
            }
            byte messageDigest[] = md.digest(password.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02X", 0xff & b));
            }
            password = sb.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            throw new SCBException(ex.getMessage(), ex);
        }
        return password;
    }

}
