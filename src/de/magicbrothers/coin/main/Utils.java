package de.magicbrothers.coin.main;

import de.magicbrothers.coin.node.Transaction;

import javax.xml.bind.DatatypeConverter;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
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

    public static String getTarget(int difficulty) {
        StringBuilder target = new StringBuilder();
        for(int i = 0; i < difficulty; i++) {
            target.append("0");
        }

        return target.toString();
    }

    public static KeyPair genKeyPair() throws Exception {

        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = new SecureRandom();
        generator.initialize(2048, random);

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
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    public static String getMerkleRoot(ArrayList<Transaction> transactions) {
        int count = transactions.size();

        ArrayList<String> previousTreeLayer = new ArrayList<>();

        for(Transaction transaction : transactions) {
            previousTreeLayer.add(transaction.transactionId);
        }

        ArrayList<String> treeLayer = previousTreeLayer;
        while(count > 1) {
            treeLayer = new ArrayList<>();
            for(int i = 1; i < previousTreeLayer.size(); i++) {
                treeLayer.add(sha256(previousTreeLayer.get(i-1) + previousTreeLayer.get(i)));
            }
            count = treeLayer.size();
            previousTreeLayer = treeLayer;
        }

        return (treeLayer.size() == 1) ? treeLayer.get(0) : "";
    }

    public static PublicKey getPublicKeyFromString(String address) throws Exception {
        byte[] publicBytes = Base64.getDecoder().decode(address);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static PrivateKey getPrivateKeyFromString(String address) throws Exception {
        byte[] privateBytes = Base64.getDecoder().decode(address);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

}
