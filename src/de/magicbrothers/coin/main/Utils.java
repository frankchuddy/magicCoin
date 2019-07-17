package de.magicbrothers.coin.main;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

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

    public static KeyPair genKeyPair() throws Exception {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048, new SecureRandom());

        return generator.generateKeyPair();

    }

    public static String sign(String data, PrivateKey privateKey) throws Exception {

        Signature privSig = Signature.getInstance("SHA256withRSA");
        privSig.initSign(privateKey);
        privSig.update(data.getBytes(UTF_8));

        byte[] signature = privSig.sign();

        return Base64.getEncoder().encodeToString(signature);

    }

    public static boolean verify(String data, String sig, PublicKey publicKey) throws Exception {

        Signature pubSig = Signature.getInstance("SHA256withRSA");
        pubSig.initVerify(publicKey);
        pubSig.update(data.getBytes(UTF_8));

        byte[] signature = Base64.getDecoder().decode(sig);

        return pubSig.verify(signature);

    }

    public static String keyToString(Key key) {
        return key.toString();
    }

}
