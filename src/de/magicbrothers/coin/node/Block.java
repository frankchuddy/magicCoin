package de.magicbrothers.coin.node;

import de.magicbrothers.coin.main.MagicCoin;
import de.magicbrothers.coin.main.Utils;

import java.util.Date;

public class Block {

    private String previousHash;
    private String data;
    private long timeStamp;
    private int nonce;
    private String hash;

    public Block(String previousHash, String data) {

        this.previousHash = previousHash;
        this.data = data;
        timeStamp = new Date().getTime();
        nonce = 0;
        hash = calcHash();

    }

    public String calcHash() {
        return Utils.sha256(previousHash + data + timeStamp + nonce);
    }

    public void mine() {

        int difficulty = MagicCoin.difficulty;
        String target = Utils.getTarget();

        System.out.println("Block wird gemined...");

        while(!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calcHash();
        }

        System.out.println("Blockhash gefunden: " + hash);

        System.out.println(hash);

    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public String getData() {
        return data;
    }

}
