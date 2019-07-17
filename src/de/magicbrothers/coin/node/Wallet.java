package de.magicbrothers.coin.node;

import de.magicbrothers.coin.main.Utils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Wallet() throws Exception {
        init();
    }

    private void init() throws Exception {
        KeyPair pair = Utils.genKeyPair();
        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();
        System.out.println("Keys erstellt!");
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
