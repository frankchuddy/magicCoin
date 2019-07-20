package de.magicbrothers.coin.node;

import de.magicbrothers.coin.main.MagicCoin;
import de.magicbrothers.coin.main.Utils;

import java.util.ArrayList;
import java.util.Date;

public class Block {

    private String previousHash;
    private String merkleRoot;
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private long timeStamp;
    private int nonce;
    private String hash;

    public Block(String previousHash) {

        this.previousHash = previousHash;
        timeStamp = new Date().getTime();

        hash = calcHash();

    }

    public String calcHash() {
        return Utils.sha256(previousHash + merkleRoot + timeStamp + nonce);
    }

    public void mine() {

        merkleRoot = Utils.getMerkleRoot(transactions);

        int difficulty = MagicCoin.difficulty;
        String target = Utils.getTarget(MagicCoin.difficulty);

        System.out.println("Block wird gemined...");

        while(!hash.substring(0, difficulty).equals(target)) {
            nonce++;
            hash = calcHash();
        }

        System.out.println("Blockhash gefunden: " + hash);

    }

    public boolean addTransaction(Transaction transaction) throws Exception {
        if(transaction == null) return false;
        if(!previousHash.equals("0")) {
            if(!transaction.processTransaction()) {
                System.out.println("Transaktion konnte nicht durchgeführt werden.");
            }
        }

        transactions.add(transaction);
        System.out.println("Transaktion erfolgreich zum Block hinzugefügt.");
        return true;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
