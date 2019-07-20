package de.magicbrothers.coin.node;

import de.magicbrothers.coin.main.Utils;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public Wallet() throws Exception {
        init();
    }

    public Wallet(PublicKey publicKey, PrivateKey privateKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    private void init() throws Exception {
        KeyPair pair = Utils.genKeyPair();
        privateKey = pair.getPrivate();
        publicKey = pair.getPublic();
        System.out.println("Keypair erstellt!");
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public float getBalance() {
        float total = 0;

        for(Map.Entry<String, TransactionOutput> i : Transaction.UTXOs.entrySet()) {
            TransactionOutput UTXO = i.getValue();
            if(UTXO.isMine(publicKey)) {
                Transaction.UTXOs.put(UTXO.id, UTXO);
                total += UTXO.amount;
            }
        }

        return total;
    }

    public Transaction send(PublicKey to, float amount) throws Exception {
        if(getBalance() < amount) {
            System.out.println("Du hast nur " + getBalance() + " Coins.");
            return null;
        }

        ArrayList<TransactionInput> inputs = new ArrayList<>();

        float total = 0;
        for(Map.Entry<String, TransactionOutput> i : Transaction.UTXOs.entrySet()) {
            TransactionOutput UTXO = i.getValue();
            total += UTXO.amount;
            inputs.add(new TransactionInput(UTXO.id));
            if(total > amount) break;
        }

        Transaction transaction = new Transaction(publicKey, to, amount, inputs);
        transaction.sign(privateKey);
        transaction.processTransaction();

        for(TransactionInput i : inputs) {
            Transaction.UTXOs.remove(i.transactionOutputId);
        }

        return transaction;
    }

}
