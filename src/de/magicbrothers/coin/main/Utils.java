package de.magicbrothers.coin.main;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;

public class Utils {

    public static String sha256(String input) {
        String result = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            return DatatypeConverter.printHexBinary(hash); // make it printable
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static String getTarget() {
        StringBuilder target = new StringBuilder();
        for(int i = 0; i < MagicCoin.difficulty; i++) {
            target.append("0");
        }

        return target.toString();
    }

}
